package com.example.omocha.Util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.omocha.MainActivity.SPEECH_DIRECTORY;
import static com.example.omocha.MainActivity.TEMP_SPEECH_PATH;

public class SpeechUtil {

    private Context context;
    private MediaPlayer mediaPlayer;

    public SpeechUtil(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }

    public void playTempSpeech(byte[] rawData) {
        if (createTmpFile(rawData)) {
            Uri audioURI = Uri.parse(TEMP_SPEECH_PATH);
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(context, audioURI);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else {
            Log.d("ERRORR", "playTempSpeech:");
        }
    }


    private boolean createTmpFile(byte[] rawData) {
        File outputFile = new File(TEMP_SPEECH_PATH);
        FileOutputStream fileoutputstream;
        try {
            fileoutputstream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fileoutputstream.write(rawData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fileoutputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}


