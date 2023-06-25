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
        int nivel = 1;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            values.put("nivel", nivel);
            values.put("experiencia", 0);
            values.put("rango", "Ippanjin");
            id = db.insert(TABLE_USERS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public Userinfo getLastUser() {
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor userLast = db.rawQuery("SELECT * FROM " + TABLE_USERS,null);
            ArrayList<Userinfo> usersInfo = new ArrayList<>();
            if (userLast.moveToFirst()) {
                do {
                  usersInfo.add(new Userinfo(userLast.getInt(0),userLast.getString(1),userLast.getString(2), userLast.getInt(3), userLast.getDouble(4), userLast.getString(5)));
                } while (userLast.moveToNext());
            }
            userLast.close();
            db.close();
            return usersInfo.get(usersInfo.size()-1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Userinfo(0,"","", 1, 0 , "Ippanjin");
    }

    public void updateUser(Userinfo user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("experiencia", user.getExperiencia());
        values.put("nivel", user.getNivel());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(user.getId())};
        db.update(TABLE_USERS, values, whereClause, whereArgs);
    }

}
