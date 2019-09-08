package com.example.omocha.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VoiceTextAPI {

    @FormUrlEncoded
    @POST("v1/tts")
    Observable<ResponseBody> getTTS(
            @Header("Authorization") String auth,
            @Field("text") String text,
            @Field("speaker") String speaker,
            @Field("pitch") int pitch,
            @Field("speed") int speed,
            @Field("volume") int volume
    );

    @FormUrlEncoded
    @POST("v1/tts")
    Observable<ResponseBody> getTTSWithEmotion(
            @Header("Authorization") String auth,
            @Field("text") String text,
            @Field("speaker") String speaker,
            @Field("emotion") String emotion,
            @Field("emotion_level") int emotion_level,
            @Field("pitch") int pitch,
            @Field("speed") int speed,
            @Field("volume") int volume
    );

}
