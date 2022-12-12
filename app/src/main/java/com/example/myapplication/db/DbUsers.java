package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.myapplication.Userinfo;

import java.util.ArrayList;

public class DbUsers extends  DbHelper {

    Context context;

    public DbUsers(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertUser(String name, String email) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            id = db.insert(TABLE_USERS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public Userinfo getLastUser() {
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor userLast = db.rawQuery("SELECT * FROM " + TABLE_USERS,null);
            ArrayList<Userinfo> usersInfo = new ArrayList<>();
            if (userLast.moveToFirst()) {
                do {
                  usersInfo.add(new Userinfo(userLast.getString(1),userLast.getString(2)));
                } while (userLast.moveToNext());
            }
            userLast.close();
            return usersInfo.get(usersInfo.size()-1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Userinfo("","");
    }
}
