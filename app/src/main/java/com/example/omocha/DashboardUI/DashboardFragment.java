package com.example.omocha.DashboardUI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.omocha.Fragments.Settings.SettingsContract;
import com.example.omocha.MainActivity;
import com.example.omocha.R;
import com.example.omocha.Util.SpeechUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment implements SettingsContract.View {

    private static final String TAG = "DashboardFragmentTAG";

    private MainActivity activity;
    private SpeechUtil speechUtil;

    @BindView(R.id.create_voice_profile)
    CardView createVoiceProfileButton;

    @BindView(R.id.create_new_speech)
    CardView createNewSpeechButton;

    @BindView(R.id.saved_speeches)
    CardView savedSpeechesButton;

    @BindView(R.id.live_chat)
    CardView liveChatButton;

    @BindView(R.id.use_your_voice)
    CardView useYourVoiceButton;

    @BindView(R.id.settings)
    CardView settingsButton;

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
        createNewSpeechButton.setOnClickListener(v -> activity.showCreateNewSpeechFragment(speechUtil));
        savedSpeechesButton.setOnClickListener(v -> activity.showSavedSpeechesFragment(speechUtil));
        liveChatButton.setOnClickListener(v -> activity.showLiveChatFragment(speechUtil));
        useYourVoiceButton.setOnClickListener(v -> activity.showUseYourVoiceFragment(speechUtil));
        settingsButton.setOnClickListener(v -> activity.showSettingsFragment());

        return view;
    }

}
