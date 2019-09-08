package com.example.omocha.Fragments.CreateNewSpeech;

import android.content.Context;
import android.util.Log;

import com.example.omocha.MainActivity;
import com.example.omocha.Models.SavedSpeech;
import com.example.omocha.Models.SavedSpeechDAO;
import com.example.omocha.Models.VoiceProfile;
import com.example.omocha.Util.RetrofitClient;
import com.example.omocha.Util.SpeechUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CreateNewSpeechPresenter implements CreateNewSpeechContract.Presenter {

    private static final String TAG = "CreateNewSpeechPresente";
    Context context;
    private CreateNewSpeechContract.View view;
    SpeechUtil speechUtil;
    SavedSpeechDAO savedSpeechDAO;

    public CreateNewSpeechPresenter(Context context, CreateNewSpeechContract.View view, SpeechUtil speechUtil, SavedSpeechDAO savedSpeechDAO) {
        this.context = context;
        this.view = view;
        this.speechUtil = speechUtil;
        this.savedSpeechDAO = savedSpeechDAO;
    }

    @Override
    public void onGetSpeech(String speech, VoiceProfile voiceProfileConfigurations, boolean isSave, String title) {
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
                            if (isSave) {
                                long tsLong = System.currentTimeMillis()/1000;
                                String ts = Long.toString(tsLong);
                                String savePath = MainActivity.SPEECH_DIRECTORY + ts + ".wav";
                                if (speechUtil.saveSpeech(responseBody.bytes(), savePath)) {
                                    savedSpeechDAO.addSavedSpeech(new SavedSpeech(title, savePath));
                                    Log.d(TAG, "onGetSpeech: added to database");
                                } else {
                                    Log.d(TAG, "error saving file, not adding to database");
                                }
                            } else {
                                speechUtil.playTempSpeech(responseBody.bytes());
                            }
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
                            if (isSave) {
                                long tsLong = System.currentTimeMillis()/1000;
                                String ts = Long.toString(tsLong);
                                String savePath = MainActivity.SPEECH_DIRECTORY + ts + ".wav";
                                if (speechUtil.saveSpeech(responseBody.bytes(), savePath)) {
                                    savedSpeechDAO.addSavedSpeech(new SavedSpeech(title, savePath));
                                    Log.d(TAG, "onGetSpeech: added to database");
                                } else {
                                    Log.d(TAG, "error saving file, not adding to database");
                                }
                            } else {
                                speechUtil.playTempSpeech(responseBody.bytes());
                            }
                            view.onHideProgressIndicator();
                        }
                    });
        }
    }
}
