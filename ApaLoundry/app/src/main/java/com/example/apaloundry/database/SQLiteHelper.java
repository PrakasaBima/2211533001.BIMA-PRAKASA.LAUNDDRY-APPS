package com.example.apaloundry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.apaloundry.model.ModelLayanan;
import com.example.apaloundry.model.ModelPelanggan;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_laundry.db";
    public static final int DATABASE_VERSION = 2;

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
            + KEY_LAYANAN_ID + " TEXT PRIMARY KEY, "
            + KEY_LAYANAN_NAMA + " TEXT, "
            + KEY_LAYANAN_HARGA + " TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQLiteHelper", "onCreate called");
        db.execSQL(CREATE_TABLE_PELANGGAN);
        db.execSQL(CREATE_TABLE_LAYANAN); // Pastikan ini dipanggil
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELANGGAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAYANAN);
        onCreate(db); // Buat tabel kembali setelah dihapus
    }

    // Method untuk menambahkan pelanggan
    public boolean insertPelanggan(ModelPelanggan mp) {
        SQLiteDatabase database = this.getWritableDatabase();
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
    public boolean insertLayanan(ModelLayanan layanan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LAYANAN_ID, layanan.getId()); // Pastikan ada id layanan
        contentValues.put(KEY_LAYANAN_NAMA, layanan.getNamaLayanan());
        contentValues.put(KEY_LAYANAN_HARGA, layanan.getHarga());
        Log.d("InsertLayanan", "Mencoba untuk memasukkan data layanan: " + layanan.getNamaLayanan());
        long id = database.insert(TABLE_LAYANAN, null, contentValues);
        database.close();
        return id != -1;
    }

    //method untuk menghapus pelanggan
    public boolean deletePelanggan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PELANGGAN, KEY_PELANGGAN_ID + "=?", new String[]{id}) > 0;
    }

    //method untuk menghapus layanan
    public boolean deleteLayanan(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LAYANAN, KEY_LAYANAN_ID + " = ?", new String[]{id}) > 0;
    }

    public ModelPelanggan getPelangganById(String pelangganId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return null;
    }
    public ModelLayanan getLayananById(String LayananId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return null;
    }

    public boolean updatePelanggan(ModelPelanggan mp) {
        SQLiteDatabase db = this.getWritableDatabase();
        return false;
    }

    public boolean updateLayanan(ModelLayanan ml) {
        SQLiteDatabase db = this.getWritableDatabase();
        return false;
    }

    // Method untuk mengambil data pelanggan
    public List<ModelPelanggan> getPelanggan() {
        List<ModelPelanggan> pel = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PELANGGAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ModelPelanggan k = new ModelPelanggan();
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
    public List<ModelLayanan> getLayanan() {
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