package com.example.omocha;

public class MainContract {
    interface View {
        void showSplashFragment();
        void showDashboardFragment();
        void showCreateVoiceProfileFragment();
        void showCreateNewSpeechFragment();
        void showPlaybackFragment();
        void showLiveChatFragment();
        void showUseYourVoiceFragment();
        void showSettingsFragment();
    }

    interface Presenter {

    }

}
