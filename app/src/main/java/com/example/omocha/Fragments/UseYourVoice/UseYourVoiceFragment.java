package com.example.omocha.Fragments.UseYourVoice;


import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.omocha.MainActivity;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UseYourVoiceFragment extends Fragment implements UseYourVoiceContract.View {

    private static final String TAG = "UseYourVoiceFragment";
    private String tmpOutputFile = MainActivity.SPEECH_DIRECTORY + "temprecording.mp3";
    UseYourVoicePresenter presenter;
    private MediaRecorder mediaRecorder;
    SpeechUtil speechUtil;

    @BindView(R.id.voice_start_recording)
    Button startRecordingButton;

    @BindView(R.id.voice_stop_recording)
    Button stopRecordingButton;

    @BindView(R.id.voice_playback_recording)
    Button playbackRecordingButton;

    @BindView(R.id.voice_save_recording)
    Button saveRecordingButton;

    @BindView(R.id.indeterminateBar)
    ProgressBar indeterminateProgressBar;



    public UseYourVoiceFragment() {
        // Required empty public constructor
    }

    public UseYourVoiceFragment(SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_your_voice, container, false);
        ButterKnife.bind(this, view);

        mediaRecorder = new MediaRecorder();

        presenter = new UseYourVoicePresenter(getContext(), this, speechUtil, mediaRecorder);

        startRecordingButton.setOnClickListener(v -> presenter.startRecording());

        stopRecordingButton.setOnClickListener(v -> presenter.stopRecording());

        playbackRecordingButton.setOnClickListener(v -> presenter.playbackRecording());

        saveRecordingButton.setOnClickListener(v -> presenter.saveRecording());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaRecorder != null) {
            mediaRecorder.release();
        }
    }

    @Override
    public void onRecording() {
        indeterminateProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopRecording() {
        indeterminateProgressBar.setVisibility(View.INVISIBLE);
    }
}
