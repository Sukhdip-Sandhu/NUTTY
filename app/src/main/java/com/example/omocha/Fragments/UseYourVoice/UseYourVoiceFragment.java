package com.example.omocha.Fragments.UseYourVoice;


import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UseYourVoiceFragment extends Fragment implements UseYourVoiceContract.View {

    private UseYourVoicePresenter presenter;
    private MediaRecorder mediaRecorder;
    private SpeechUtil speechUtil;
    private boolean isRecording = false;

    @BindView(R.id.record_button)
    LottieAnimationView recordButton;

    @BindView(R.id.voice_playback_recording)
    Button playbackRecordingButton;

    @BindView(R.id.voice_save_recording)
    Button saveRecordingButton;


    public UseYourVoiceFragment() {
        // Required empty public constructor
    }

    public UseYourVoiceFragment(SpeechUtil speechUtil) {
        this.speechUtil = speechUtil;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_your_voice, container, false);
        ButterKnife.bind(this, view);

        mediaRecorder = new MediaRecorder();

        presenter = new UseYourVoicePresenter(getContext(), this, speechUtil, mediaRecorder);

        recordButton.setOnClickListener(v -> {
            if (!isRecording) {
                isRecording = true;
                presenter.startRecording();
            } else {
                isRecording = false;
                presenter.stopRecording();
            }
        });

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
        recordButton.playAnimation();
    }

    @Override
    public void onStopRecording() {
        recordButton.playAnimation();
        recordButton.pauseAnimation();
    }
}
