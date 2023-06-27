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
    public long insertUser(String name, String email, String nativeLanguage, String targetLanguage, int active) {
        long id = 0;
        int nivel = 1;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = this.getWritableDatabase();
            // Establecer todos los campos "active" a 0
            ContentValues updateValues = new ContentValues();
            updateValues.put("active", 0);
            db.update(TABLE_USERS, updateValues, null, null);
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            values.put("nivel", nivel);
            values.put("experiencia", 0);
            values.put("rango", "一般人");
            values.put("native", nativeLanguage);
            values.put("target", targetLanguage);
            values.put("active", active);
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
            String selection = "active = ?";
            String[] selectionArgs = {"1"};
            Cursor userLast = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
            ArrayList<Userinfo> usersInfo = new ArrayList<>();
            if (userLast.moveToFirst()) {
                do {
                    usersInfo.add(new Userinfo(userLast.getInt(0), userLast.getString(1), userLast.getString(2), userLast.getInt(3),
                            userLast.getDouble(4), userLast.getString(5), userLast.getString(6), userLast.getString(7), userLast.getInt(8)));
                } while (userLast.moveToNext());
            }
            userLast.close();
            db.close();
            return usersInfo.get(usersInfo.size() - 1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Userinfo(0, "", "", 1, 0, "一般人", "spanish", "japanese", 1);
    }
    /*public Userinfo getLastUser() {
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor userLast = db.rawQuery("SELECT * FROM " + TABLE_USERS,null);
            ArrayList<Userinfo> usersInfo = new ArrayList<>();
            if (userLast.moveToFirst()) {
                do {
                    usersInfo.add(new Userinfo(userLast.getInt(0),userLast.getString(1),userLast.getString(2), userLast.getInt(3),
                            userLast.getDouble(4), userLast.getString(5), userLast.getString(6), userLast.getString(7), userLast.getInt(8)));
                } while (userLast.moveToNext());
            }
            userLast.close();
            db.close();
            return usersInfo.get(usersInfo.size()-1);
        } catch (Exception ex) {
            ex.toString();
        }
        return new Userinfo(0,"","", 1, 0 , "一般人", "spanish","japanese", 0);
    }*/

    public void updateUser(Userinfo user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("experiencia", user.getExperiencia());
        values.put("nivel", user.getNivel());
        values.put("rango", user.getRango());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(user.getId())};
        db.update(TABLE_USERS, values, whereClause, whereArgs);
    }
    public Userinfo getUserById(String name, String email) {
        Userinfo userInfo = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "name = ? AND email = ?";
        String[] selectionArgs = {name, email};
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nivelIndex = cursor.getColumnIndex("nivel");
            int experienciaIndex = cursor.getColumnIndex("experiencia");
            int rangoIndex = cursor.getColumnIndex("rango");
            int nativeLanguageIndex = cursor.getColumnIndex("native");
            int targetLanguageIndex = cursor.getColumnIndex("target");
            int activeIndex = cursor.getColumnIndex("active");

            int userId = cursor.getInt(idIndex);
            int nivel = cursor.getInt(nivelIndex);
            double experiencia = cursor.getDouble(experienciaIndex);
            String rango = cursor.getString(rangoIndex);
            String nativeLanguage = cursor.getString(nativeLanguageIndex);
            String targetLanguage = cursor.getString(targetLanguageIndex);
            int active = cursor.getInt(activeIndex);

            userInfo = new Userinfo(userId, name, email, nivel, experiencia, rango, nativeLanguage, targetLanguage, active);
        }
        cursor.close();
        db.close();
        return userInfo;
    }

}
