package com.example.omocha;

import com.example.omocha.Util.SpeechUtil;

public class MainContract {
    interface View {
        void showSplashFragment();
        void showDashboardFragment();
        void showCreateVoiceProfileFragment(SpeechUtil speechUtil);
        void showCreateNewSpeechFragment();
        void showPlaybackFragment();
        void showLiveChatFragment();
        void showUseYourVoiceFragment();
        void showSettingsFragment();
    }

    interface Presenter {

    }

}
