package com.biee.apaloundry.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.biee.apaloundry.com.model.ModelLayanan;
import com.biee.apaloundry.com.model.ModelPelanggan;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_laundry.db";
    public static final int DATABASE_VERSION = 1;

    // Tabel Pelanggan
    public static final String TABLE_PELANGGAN = "pelanggan";
    public static final String KEY_PELANGGAN_ID = "pelanggan_id";
    public static final String KEY_PELANGGAN_NAMA = "nama";
    public static final String KEY_PELANGGAN_EMAIL = "email";
    public static final String KEY_PELANGGAN_HP = "hp";

    // Tabel Layanan
    public static final String TABLE_LAYANAN = "layanan";
    public static final String KEY_LAYANAN_ID = "layanan_id"; // Tambahkan ID layanan
    public static final String KEY_LAYANAN_NAMA = "nama_layanan";
    public static final String KEY_LAYANAN_HARGA = "harga";

    // Query untuk membuat tabel pelanggan
    private static final String CREATE_TABLE_PELANGGAN = "CREATE TABLE " +
            TABLE_PELANGGAN + "("
            + KEY_PELANGGAN_ID + " TEXT PRIMARY KEY, "
            + KEY_PELANGGAN_NAMA + " TEXT, "
            + KEY_PELANGGAN_EMAIL + " TEXT, "
            + KEY_PELANGGAN_HP + " TEXT )";

    // Query untuk membuat tabel layanan
    private static final String CREATE_TABLE_LAYANAN = "CREATE TABLE IF NOT EXISTS "
            + TABLE_LAYANAN + "("
            + "layanan_id TEXT PRIMARY KEY, "
            + KEY_LAYANAN_NAMA + " TEXT, "
            + KEY_LAYANAN_HARGA + " TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PELANGGAN);
        db.execSQL(CREATE_TABLE_LAYANAN); // Buat tabel layanan
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELANGGAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAYANAN); // Hapus tabel layanan jika ada pembaruan
        onCreate(db);
    }

    // Method untuk menambahkan pelanggan
    public boolean insertPelanggan(ModelPelanggan mp) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_ID, mp.getId());
        contentValues.put(KEY_PELANGGAN_NAMA, mp.getNama());
        contentValues.put(KEY_PELANGGAN_EMAIL, mp.getEmail());
        contentValues.put(KEY_PELANGGAN_HP, mp.getHp());
        long id = database.insert(TABLE_PELANGGAN, null, contentValues);
        database.close();
        return id != -1;
    }

    // Method untuk menambahkan layanan
    public boolean insertLayanan(com.biee.apaloundry.com.model.ModelLayanan layanan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_ID, layanan.getId()); // Pastikan ada id layanan
        contentValues.put(KEY_LAYANAN_NAMA, layanan.getNamaLayanan());
        contentValues.put(KEY_LAYANAN_HARGA, layanan.getHarga());
        long id = database.insert(TABLE_LAYANAN, null, contentValues);
        database.close();
        return id != -1;
    }

    // Method untuk mengambil data pelanggan
    public List<com.biee.apaloundry.com.model.ModelPelanggan> getPelanggan() {
        List<com.biee.apaloundry.com.model.ModelPelanggan> pel = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PELANGGAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                com.biee.apaloundry.com.model.ModelPelanggan k = new com.biee.apaloundry.com.model.ModelPelanggan();
                k.setId(cursor.getString(0));
                k.setNama(cursor.getString(1));
                k.setEmail(cursor.getString(2));
                k.setHp(cursor.getString(3));
                pel.add(k);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pel;
    }

    // Method untuk mengambil data layanan
    public List<com.biee.apaloundry.com.model.ModelLayanan> getLayanan() {
        List<ModelLayanan> layananList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LAYANAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ModelLayanan l = new ModelLayanan();
                l.setId(cursor.getString(0));
                l.setNamaLayanan(cursor.getString(1));
                l.setHarga(cursor.getString(2));
                layananList.add(l);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return layananList;
    }
}