package com.example.sekolahkuapplication.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "school.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE siswa(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namaDepan TEXT," +
                "namaBelakang TEXT," +
                "phoneNumber TEXT," +
                "email TEXT, " +
                "tglLahir TEXT," +
                "gender TEXT," +
                "education TEXT," +
                "hobi TEXT," +
                "alamat TEXT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
