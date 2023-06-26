package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.db.DbHelper;

import java.util.ArrayList;

public class DbWords extends  DbHelper{
    Context context;

    public DbWords(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public Wordinfo getLastWord() {
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor wordlast = db.rawQuery("SELECT * FROM " + TABLE_WORDS,null);
            ArrayList<Wordinfo> Wordinfo = new ArrayList<>();
            if (wordlast.moveToFirst()) {
                do {
                    Wordinfo.add(new Wordinfo(wordlast.getInt(0),wordlast.getString(1),wordlast.getString(2), wordlast.getInt(3)));
                } while (wordlast.moveToNext());
            }
            wordlast.close();
            db.close();
            return Wordinfo.get(Wordinfo.size()-1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Wordinfo(0,"","", 1);
    }
}
