package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.db.DbUsers;

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
            DbUsers dbUsers = new DbUsers(context);
            Userinfo lastUser = dbUsers.getLastUser();
            int lastUserId = lastUser.getId();
            //Cursor wordlast = db.rawQuery("SELECT * FROM " + TABLE_WORDS,null);
            Cursor wordlast = db.rawQuery("SELECT * FROM " + TABLE_WORDS + " WHERE UsuarioId = " + lastUserId , null);
            ArrayList<Wordinfo> Wordinfo = new ArrayList<>();
            if (wordlast.moveToFirst()) {
                do {
                    Wordinfo.add(new Wordinfo(wordlast.getInt(0),wordlast.getString(1),wordlast.getString(2), wordlast.getInt(4), wordlast.getString(3)));
                } while (wordlast.moveToNext());
            }
            wordlast.close();
            db.close();
            return Wordinfo.get(Wordinfo.size()-1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Wordinfo(0,"","", 1, "");
    }
}
