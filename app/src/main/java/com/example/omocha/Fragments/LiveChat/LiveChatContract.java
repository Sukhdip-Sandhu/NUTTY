package com.example.omocha.Fragments.LiveChat;

import com.example.omocha.Models.VoiceProfile;

public interface LiveChatContract {
    interface View {
        void onShowProgressIndicator();
        void onHideProgressIndicator();
    }

    interface Presenter {
        void onGetSpeech(String speech, VoiceProfile voiceProfileConfigurations);
    }
}
