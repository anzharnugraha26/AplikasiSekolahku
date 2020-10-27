package com.example.sekolahkuapplication.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sekolahkuapplication.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class SiswaDataSource {
    private final DatabaseHelper databaseHelper;


    public SiswaDataSource(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    private ContentValues convertToContentValues (Siswa siswa){
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", siswa.getId());
            contentValues.put("namaDepan", siswa.getNamaDepan());
            contentValues.put("namaBelakang", siswa.getNamaBelakang());
            contentValues.put("phoneNumber", siswa.getPhoneNumber());
            contentValues.put("email", siswa.getEmail());
            contentValues.put("tglLahir", siswa.getTglLahir());
            contentValues.put("gender", siswa.getGender());
            contentValues.put("education", siswa.getEducation());
            contentValues.put("hobi", siswa.getHobi());
            contentValues.put("alamat", siswa.getAlamat());
            return contentValues;
    }

    private Siswa convertToSiswa (Cursor cursor){
            Siswa siswa = new Siswa();
                siswa.setId(cursor.getLong(0));
                siswa.setNamaDepan(cursor.getString(1));
                siswa.setNamaBelakang(cursor.getString(2));
                siswa.setPhoneNumber(cursor.getString(3));
                siswa.setEmail(cursor.getString(4));
                siswa.setTglLahir(cursor.getString(5));
                siswa.setGender(cursor.getString(6));
                siswa.setEducation(cursor.getString(7));
                siswa.setHobi(cursor.getString(8));
                siswa.setAlamat(cursor.getString(9));
                return siswa ;
    }


    public void save (Siswa siswa){
        ContentValues contentValues = convertToContentValues(siswa);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insert("siswa", null , contentValues);
        database.close();
    }


    public List<Siswa> getAll(){
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM siswa", null);

        List<Siswa> foundSiswaList = new ArrayList<>();
        while (cursor.moveToNext()){
            Siswa siswa = convertToSiswa(cursor);
            foundSiswaList.add(siswa);
        }

        cursor.close();
        database.close();
        return foundSiswaList;
    }


    public Siswa findById(Long id){
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM siswa WHERE id=?"
            , new String[]{String.valueOf(id)}
        );
        boolean found = cursor.getCount() > 0 ;

        if (found){
            cursor.moveToNext();
            Siswa siswa = convertToSiswa(cursor);
            cursor.close();
            database.close();

            return siswa ;
        }else{
                throw new RuntimeException("data siswa dengan id" +id+ "tidak ditemukan");
        }
    }


    public void update (Siswa siswa){
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
            ContentValues contentValues = convertToContentValues(siswa);
            database.update("siswa", contentValues ,  "id=?" , new String[]{
                String.valueOf(siswa.getId())
            });
            database.close();
    }

    public void remove (Siswa siswa){
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            database.delete("siswa", "id=?", new
                    String[]{Long.toString(siswa.getId())});
            database.close();
    }




}
