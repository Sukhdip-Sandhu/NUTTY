package com.example.omocha.Fragments.CreateNewSpeech;

import com.example.omocha.Models.VoiceProfile;

public interface CreateNewSpeechContract {
    interface View {
        void onShowProgressIndicator();
        void onHideProgressIndicator();
    }

    interface Presenter {
        void onGetSpeech(String speech, VoiceProfile voiceProfileConfigurations, boolean isSave, String title);
    }
}
