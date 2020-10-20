package com.example.omocha.Fragments.CreateVoiceProfile.AddVoiceProfile;

import android.content.Context;

import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Models.VoiceProfileDAO;
import com.example.omocha.Util.SpeechUtil;
import com.example.omocha.Util.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class AddVoiceProfilePresenter implements AddVoiceProfileContract.Presenter {

    Context context;
    private AddVoiceProfileContract.View view;
    SpeechUtil speechUtil;
    VoiceProfileDAO voiceProfileDAO;

    public AddVoiceProfilePresenter(Context context, AddVoiceProfileContract.View view, SpeechUtil SpeechUtil, VoiceProfileDAO voiceProfileDAO) {
        this.context = context;
        this.view = view;
        this.speechUtil = SpeechUtil;
        this.voiceProfileDAO = voiceProfileDAO;
    }

    @Override
    public void handleSpeakerToggles(String speaker) {
        if (speaker.equalsIgnoreCase("GIRL")) {
            view.handleSpeakerToggles(true);
        } else {
            view.handleSpeakerToggles(false);
        }
    }

    @Override
    public void handleEmotionToggles(String emotion) {
        if (emotion.equalsIgnoreCase("HAPPY")) {
            view.handleEmotionToggles("HAPPY");
        } else if (emotion.equalsIgnoreCase("SAD")) {
            view.handleEmotionToggles("SAD");
        } else if (emotion.equalsIgnoreCase("ANGRY")) {
            view.handleEmotionToggles("ANGRY");
        } else {
            view.handleEmotionToggles("NONE");
        }
    }

    // range 1 - 4
    @Override
    public int seekbarToEmotionLevel(int progress) {
        return progress + 1;
    }

    // range 50 - 200
    @Override
    public int seekbarToPitchValue(int progress) {
        return progress * 10 + 50;
    }

    // range 50 - 400
    @Override
    public int seekbarToSpeedValue(int progress) {
        return progress * 10 + 50;
    }

    // range 50 - 200
    @Override
    public int seekbarToVolumeValue(int progress) {
        return progress * 10 + 50;
    }

    @Override
    public void onTestVoiceProfile(String voiceSample, VoiceProfile voiceProfileConfigurations) {
        view.onShowProgressIndicator();
        if (voiceProfileConfigurations.getEmotion() != null) {
            Observable<ResponseBody> responseBodyObservable = RetrofitClient
                    .getInstance().getAPI().getTTSWithEmotion("API_KEY",
                            voiceSample,
                            voiceProfileConfigurations.getSpeaker(),
                            voiceProfileConfigurations.getEmotion(),
                            voiceProfileConfigurations.getEmotion_level(),
                            voiceProfileConfigurations.getPitch(),
                            voiceProfileConfigurations.getSpeed(),
                            voiceProfileConfigurations.getVolume());

            Disposable subscribe = responseBodyObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> {
                        if (responseBody != null) {
                            speechUtil.playTempSpeech(responseBody.bytes());
                            view.onHideProgressIndicator();
                        }
                    });
        } else {
            Observable<ResponseBody> responseBodyObservable = RetrofitClient
                    .getInstance().getAPI().getTTS("API_KEY",
                            voiceSample,
                            voiceProfileConfigurations.getSpeaker(),
                            voiceProfileConfigurations.getPitch(),
                            voiceProfileConfigurations.getSpeed(),
                            voiceProfileConfigurations.getVolume());

            Disposable subscribe = responseBodyObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> {
                        if (responseBody != null) {
                            speechUtil.playTempSpeech(responseBody.bytes());
                            view.onHideProgressIndicator();
                        }
                    });
        }
    }


    @Override
    public void onSaveVoiceProfile(VoiceProfile voiceProfileConfigurations) {
        voiceProfileDAO.addVoiceProfile(voiceProfileConfigurations);
    }
}
