package com.example.omocha.Models;

public class SavedSpeech {
    private int id;
    private String speechTitle;
    private String speechPath;

    public SavedSpeech() {
    }

    public SavedSpeech(String speechTitle, String speechPath) {
        this.speechTitle = speechTitle;
        this.speechPath = speechPath;
    }

    public SavedSpeech(int id, String speechTitle, String speechPath) {
        this.id = id;
        this.speechTitle = speechTitle;
        this.speechPath = speechPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeechTitle() {
        return speechTitle;
    }

    public void setSpeechTitle(String speechTitle) {
        this.speechTitle = speechTitle;
    }

    public String getSpeechPath() {
        return speechPath;
    }

    public void setSpeechPath(String speechPath) {
        this.speechPath = speechPath;
    }
}
