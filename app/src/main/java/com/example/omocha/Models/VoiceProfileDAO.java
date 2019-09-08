package com.example.omocha.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VoiceProfileDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "omochadb";

    public static final String VoiceProfile_KEY = "id";
    public static final String VoiceProfile_PROFILE_NAME = "vp_name";
    public static final String VoiceProfile_SPEAKER = "speaker";
    public static final String VoiceProfile_EMOTION = "emotion";
    public static final String VoiceProfile_EMOTION_LEVEL = "emotion_level";
    public static final String VoiceProfile_PITCH = "pitch";
    public static final String VoiceProfile_SPEED = "speed";
    public static final String VoiceProfile_VOLUME = "volume";

    public static final String VoiceProfile_TABLE_NAME = "VoiceProfile";

    public static final String VoiceProfile_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + VoiceProfile_TABLE_NAME + " (" +
                    VoiceProfile_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VoiceProfile_PROFILE_NAME + " TEXT, " +
                    VoiceProfile_SPEAKER + " TEXT, " +
                    VoiceProfile_EMOTION + " TEXT, " +
                    VoiceProfile_EMOTION_LEVEL + " INTEGER, " +
                    VoiceProfile_PITCH + " INTEGER, " +
                    VoiceProfile_SPEED + " INTEGER, " +
                    VoiceProfile_VOLUME + " INTEGER);";

    public static final String VoiceProfile_TABLE_DROP = "DROP TABLE IF EXISTS " + VoiceProfile_TABLE_NAME + ";";

    public VoiceProfileDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.getWritableDatabase().execSQL(VoiceProfile_TABLE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VoiceProfile_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VoiceProfile_TABLE_DROP);
        onCreate(db);
    }

    public VoiceProfileDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase().execSQL(VoiceProfile_TABLE_CREATE);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Contact
    public void addVoiceProfile(VoiceProfile voiceProfile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VoiceProfile_PROFILE_NAME, voiceProfile.getVoiceProfileName());
        values.put(VoiceProfile_SPEAKER, voiceProfile.getSpeaker());
        values.put(VoiceProfile_EMOTION, voiceProfile.getEmotion());
        values.put(VoiceProfile_EMOTION_LEVEL, voiceProfile.getEmotion_level());
        values.put(VoiceProfile_PITCH, voiceProfile.getPitch());
        values.put(VoiceProfile_SPEED, voiceProfile.getSpeed());
        values.put(VoiceProfile_VOLUME, voiceProfile.getVolume());

        // Inserting Row
        db.insert(VoiceProfile_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting All VoiceProfiles
    public ArrayList<VoiceProfile> getAllVoiceProfiles() {
        ArrayList<VoiceProfile> voiceProfileList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + VoiceProfile_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VoiceProfile voiceProfile = new VoiceProfile();
                voiceProfile.setVoiceProfileName(cursor.getString(1));
                voiceProfile.setSpeaker(cursor.getString(2));
                voiceProfile.setEmotion(cursor.getString(3));
                voiceProfile.setEmotion_level(Integer.parseInt(cursor.getString(4)));
                voiceProfile.setPitch(Integer.parseInt(cursor.getString(5)));
                voiceProfile.setSpeed(Integer.parseInt(cursor.getString(6)));
                voiceProfile.setVolume(Integer.parseInt(cursor.getString(7)));
                // Adding Contact to list
                voiceProfileList.add(voiceProfile);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return voiceProfileList;
    }

    public void deleteVoiceProfile(String voiceProfileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VoiceProfile_TABLE_NAME, VoiceProfile_PROFILE_NAME + " = ?",
                new String[] { String.valueOf(voiceProfileName) });
        db.close();
    }

    public ArrayList<String> getAllVoiceProfileNames() {
        {
            ArrayList<String> voiceProfileNames = new ArrayList<>();
            // Select All Query
            String selectQuery = "SELECT  " + VoiceProfile_PROFILE_NAME + " FROM " + VoiceProfile_TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    voiceProfileNames.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            cursor.close();
            // return Contact list
            return voiceProfileNames;
        }
    }

    public void debug_reset_db() {
        this.getWritableDatabase().execSQL(VoiceProfile_TABLE_DROP);
        this.getWritableDatabase().execSQL(VoiceProfile_TABLE_CREATE);
    }
}
