package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "app.db";
    public static final String TABLE_USERS = "t_users";
    public static final String TABLE_WORDS = "t_words";
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String initialUsersTableString = "CREATE TABLE " + TABLE_USERS + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT," +
                "nivel INTEGER," +
                "experiencia REAL," +
                "rango TEXT, " +
                "native TEXT, " +
                "target TEXT, " +
                "active INTEGER)";
        sqLiteDatabase.execSQL(initialUsersTableString);

        String initialWordsTableString = "CREATE TABLE " + TABLE_WORDS + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imageurl TEXT," +
                "texto TEXT," +
                "translation TEXT," +
                "UsuarioId INTEGER REFERENCES " + TABLE_USERS + "(id))";
        sqLiteDatabase.execSQL(initialWordsTableString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }
}
