package com.example.omocha.Models;

public class VoiceProfile {
    private String voiceProfileName;
    private String speaker;
    private String emotion;
    private int emotion_level;
    private int pitch;
    private int speed;
    private int volume;

    public VoiceProfile(String voiceProfileName, String speaker, String emotion, int emotion_level, int pitch, int speed, int volume) {
        this.voiceProfileName = voiceProfileName;
        this.speaker = speaker;
        this.emotion = emotion;
        this.emotion_level = emotion_level;
        this.pitch = pitch;
        this.speed = speed;
        this.volume = volume;
    }

    public VoiceProfile() {

    }

    public String getVoiceProfileName() {
        return voiceProfileName;
    }

    public void setVoiceProfileName(String voiceProfileName) {
        this.voiceProfileName = voiceProfileName;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getEmotion_level() {
        return emotion_level;
    }

    public void setEmotion_level(int emotion_level) {
        this.emotion_level = emotion_level;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
