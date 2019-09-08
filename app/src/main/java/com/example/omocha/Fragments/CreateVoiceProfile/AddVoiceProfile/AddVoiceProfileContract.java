package com.example.omocha.Fragments.CreateVoiceProfile.AddVoiceProfile;

import com.example.omocha.Models.VoiceProfile;

public interface AddVoiceProfileContract {
    interface View {
        void initToggles();
        void handleSpeakerToggles(boolean toggleGirl);
        void handleEmotionToggles(String emotionToToggle);
        void onShowProgressIndicator();
        void onHideProgressIndicator();
    }

    interface Presenter {
        void handleSpeakerToggles(String speaker);
        void handleEmotionToggles(String emotion);
        int seekbarToEmotionLevel(int progress);
        int seekbarToPitchValue(int progress);
        int seekbarToSpeedValue(int progress);
        int seekbarToVolumeValue(int progress);
        void onTestVoiceProfile(String voiceSample, VoiceProfile voiceProfileConfigurations);
        void onSaveVoiceProfile(VoiceProfile voiceProfileConfigurations);
    }
}
