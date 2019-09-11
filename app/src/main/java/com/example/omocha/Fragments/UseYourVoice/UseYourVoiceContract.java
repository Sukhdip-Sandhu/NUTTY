package com.example.omocha.Fragments.UseYourVoice;

public interface UseYourVoiceContract {
    interface View {
        void onRecording();
        void onStopRecording();
    }

    interface Presenter {
        void startRecording();
        void stopRecording();
        void playbackRecording();
        void saveRecording();
    }
}
