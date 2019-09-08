package com.example.omocha.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SavedSpeechDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "omochadb";

    public static final String SavedSpeech_KEY = "id";
    public static final String SavedSpeech_TITLE = "title";
    public static final String SavedSpeech_PATH = "path";

    public static final String SavedSpeech_TABLE_NAME = "SavedSpeech";

    public static final String SavedSpeech_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + SavedSpeech_TABLE_NAME + " (" +
                    SavedSpeech_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SavedSpeech_TITLE + " TEXT, " +
                    SavedSpeech_PATH + " TEXT);";

    public static final String SavedSpeech_TABLE_DROP = "DROP TABLE IF EXISTS " + SavedSpeech_TABLE_NAME + ";";

    public SavedSpeechDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.getWritableDatabase().execSQL(SavedSpeech_TABLE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SavedSpeech_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SavedSpeech_TABLE_DROP);
        onCreate(db);
    }

    public SavedSpeechDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase().execSQL(SavedSpeech_TABLE_CREATE);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Contact
    public void addSavedSpeech(SavedSpeech SavedSpeech) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SavedSpeech_TITLE, SavedSpeech.getSpeechTitle());
        values.put(SavedSpeech_PATH, SavedSpeech.getSpeechPath());

        // Inserting Row
        db.insert(SavedSpeech_TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
}
