package com.example.omocha.Fragments.UseYourVoice;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omocha.MainActivity;
import com.example.omocha.Models.SavedSpeech;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UseYourVoicePresenter implements UseYourVoiceContract.Presenter {

    private String tmpOutputFile = MainActivity.SPEECH_DIRECTORY + "temprecording.mp3";

    private Context context;
    private UseYourVoiceContract.View view;
    private SpeechUtil speechUtil;
    private SavedSpeechDAO savedSpeechDAO;
    private MediaRecorder mediaRecorder;

    UseYourVoicePresenter(Context context, UseYourVoiceContract.View view,
                          SpeechUtil speechUtil, MediaRecorder mediaRecorder) {
        this.context = context;
        this.view = view;
        this.speechUtil = speechUtil;
        this.mediaRecorder = mediaRecorder;
        savedSpeechDAO = new SavedSpeechDAO(context);
    }

    @Override
    public void startRecording() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(tmpOutputFile);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            view.onRecording();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopRecording() {
        mediaRecorder.stop();
        view.onStopRecording();
    }

    @Override
    public void playbackRecording() {
        speechUtil.saySpeech(tmpOutputFile);
    }

    @Override
    public void saveRecording() {
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            final EditText voiceTitleEditText = new EditText(context);
            alert.setTitle(context.getResources().getString(R.string.save_voice_title));
            alert.setMessage(context.getResources().getString(R.string.save_voice_message));
            alert.setView(voiceTitleEditText);
            alert.setPositiveButton(context.getResources().getString(R.string.yes), (dialog, whichButton) -> {
                String inputVoiceTitleName = voiceTitleEditText.getText().toString();
                if (isVoiceTitleOkay(inputVoiceTitleName)) {
                    saveRecordingToDB(inputVoiceTitleName);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.save_voice_error), Toast.LENGTH_LONG).show();
                }
            });
            alert.setNegativeButton(context.getResources().getString(R.string.no), (dialog, whichButton) -> {});
            alert.show();
        }
    }

    private void saveRecordingToDB(String title) {
        if (new File(tmpOutputFile).exists()) {
            long tsLong = System.currentTimeMillis()/1000;
            String ts = Long.toString(tsLong);
            String savePath = MainActivity.SPEECH_DIRECTORY + ts + ".3gp";
            new File(tmpOutputFile).renameTo(new File(savePath));
            savedSpeechDAO.addSavedSpeech(new SavedSpeech(title, savePath));
        }
    }

    private boolean isVoiceTitleOkay(String requestedName) {
        ArrayList<String> databaseSavedSpeechNames = savedSpeechDAO.getAllSavedSpeechNames();
        for (String existingName : databaseSavedSpeechNames) {
            if (existingName.equals(requestedName)) {
                return false;
            }
        }
        return true;
    }
}
