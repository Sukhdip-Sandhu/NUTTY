package com.example.omocha.DashboardUI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.omocha.Fragments.Settings.SettingsContract;
import com.example.omocha.MainActivity;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment implements SettingsContract.View {

    private static final String TAG = "DashboardFragmentTAG";

    MainActivity activity;
    SpeechUtil speechUtil;

    @BindView(R.id.create_voice_profile)
    Button createVoiceProfileButton;

    @BindView(R.id.create_new_speech)
    Button createNewSpeechButton;

    @BindView(R.id.playback)
    Button playBackButton;

    @BindView(R.id.live_chat)
    Button liveChatButton;

    @BindView(R.id.use_your_voice)
    Button useYourVoiceButton;

    @BindView(R.id.settings)
    Button settingsButton;

    public DashboardFragment(MainActivity mainActivity) {
        // Required empty public constructor
        activity = mainActivity;
        speechUtil = new SpeechUtil(mainActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        createVoiceProfileButton.setOnClickListener(v -> activity.showCreateVoiceProfileFragment(speechUtil));
        createNewSpeechButton.setOnClickListener(v -> activity.showCreateNewSpeechFragment());
        playBackButton.setOnClickListener(v -> activity.showPlaybackFragment());
        liveChatButton.setOnClickListener(v -> activity.showLiveChatFragment());
        useYourVoiceButton.setOnClickListener(v -> activity.showUseYourVoiceFragment());
        settingsButton.setOnClickListener(v -> activity.showSettingsFragment());

        return view;
    }

}
