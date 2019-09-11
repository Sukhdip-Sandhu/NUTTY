package com.example.omocha;

import com.example.omocha.Util.SpeechUtil;

public class MainContract {
    interface View {
        void showSplashFragment();
        void showDashboardFragment();
        void showCreateVoiceProfileFragment(SpeechUtil speechUtil);
        void showCreateNewSpeechFragment(SpeechUtil speechUtil);
        void showSavedSpeechesFragment(SpeechUtil speechUtil);
        void showLiveChatFragment(SpeechUtil speechUtil);
        void showUseYourVoiceFragment(SpeechUtil speechUtil);
        void showSettingsFragment();
    }

    interface Presenter {

    }

}
