package com.example.omocha.Fragments.LiveChat;

import android.content.Context;
import android.util.Log;

import com.example.omocha.Fragments.CreateNewSpeech.CreateNewSpeechContract;
import com.example.omocha.MainActivity;
import com.example.omocha.Models.SavedSpeech;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Util.RetrofitClient;
import com.example.omocha.Util.SpeechUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LiveChatPresenter implements LiveChatContract.Presenter {

    private static final String TAG = "LiveChatPresenter";
    Context context;
    private LiveChatContract.View view;
    SpeechUtil speechUtil;

    public LiveChatPresenter(Context context, LiveChatContract.View view, SpeechUtil speechUtil) {
        this.context = context;
        this.view = view;
        this.speechUtil = speechUtil;
    }

    @Override
    public void onGetSpeech(String speech, VoiceProfile voiceProfileConfigurations) {
        view.onShowProgressIndicator();
        if (voiceProfileConfigurations.getEmotion() != null) {
            Observable<ResponseBody> responseBodyObservable = RetrofitClient
                    .getInstance().getAPI().getTTSWithEmotion("Basic bmE4eGVrM3RjMTJiMDNkZTo=",
                            speech,
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
                    .getInstance().getAPI().getTTS("Basic bmE4eGVrM3RjMTJiMDNkZTo=",
                            speech,
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
}
