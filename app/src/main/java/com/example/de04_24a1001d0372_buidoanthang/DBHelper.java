package com.example.de04_24a1001d0372_buidoanthang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TEN_DB = "evn.db";
    private static final int PHIEN_BAN_DB = 2;

    public DBHelper(Context context) {
        super(context, TEN_DB, null, PHIEN_BAN_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ma_kh TEXT, ten_chu_ho TEXT, loai_kh TEXT, " +
                "chi_so_cu INTEGER, chi_so_moi INTEGER, so_kwh INTEGER, " +
                "tien_dien INTEGER, thue_gtgt INTEGER, tong_tien INTEGER, ngay_lap TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int phienBanCu, int phienBanMoi) {
        db.execSQL("DROP TABLE IF EXISTS bills");
        onCreate(db);
    }

    public long themHoaDon(Bill hoaDon) {
        ContentValues giaTri = new ContentValues();
        giaTri.put("ma_kh", hoaDon.getMaKH());
        giaTri.put("ten_chu_ho", hoaDon.getTenChuHo());
        giaTri.put("loai_kh", hoaDon.getLoaiKH());
        giaTri.put("chi_so_cu", hoaDon.getChiSoCu());
        giaTri.put("chi_so_moi", hoaDon.getChiSoMoi());
        giaTri.put("so_kwh", hoaDon.getSoKwh());
        giaTri.put("tien_dien", hoaDon.getTienDien());
        giaTri.put("thue_gtgt", hoaDon.getThueGTGT());
        giaTri.put("tong_tien", hoaDon.getTongTien());
        giaTri.put("ngay_lap", hoaDon.getNgayLap());
        return getWritableDatabase().insert("bills", null, giaTri);
    }

    public List<Bill> layTatCaHoaDon() {
        List<Bill> danhSach = new ArrayList<>();
        Cursor con = getReadableDatabase().rawQuery("SELECT * FROM bills ORDER BY id DESC", null);
        while (con.moveToNext()) {
            Bill hoaDon = new Bill();
            hoaDon.setId(con.getInt(con.getColumnIndexOrThrow("id")));
            hoaDon.setMaKH(con.getString(con.getColumnIndexOrThrow("ma_kh")));
            hoaDon.setTenChuHo(con.getString(con.getColumnIndexOrThrow("ten_chu_ho")));
            hoaDon.setLoaiKH(con.getString(con.getColumnIndexOrThrow("loai_kh")));
            hoaDon.setChiSoCu(con.getInt(con.getColumnIndexOrThrow("chi_so_cu")));
            hoaDon.setChiSoMoi(con.getInt(con.getColumnIndexOrThrow("chi_so_moi")));
            hoaDon.setSoKwh(con.getInt(con.getColumnIndexOrThrow("so_kwh")));
            hoaDon.setTienDien(con.getLong(con.getColumnIndexOrThrow("tien_dien")));
            hoaDon.setThueGTGT(con.getLong(con.getColumnIndexOrThrow("thue_gtgt")));
            hoaDon.setTongTien(con.getLong(con.getColumnIndexOrThrow("tong_tien")));
            hoaDon.setNgayLap(con.getString(con.getColumnIndexOrThrow("ngay_lap")));
            danhSach.add(hoaDon);
        }
        con.close();
        return danhSach;
    }

    public void xoaHoaDon(int id) {
        getWritableDatabase().delete("bills", "id=?", new String[]{String.valueOf(id)});
    }

    public long[] layThongKe() {
        Cursor con = getReadableDatabase().rawQuery(
                "SELECT COUNT(*), IFNULL(SUM(tong_tien), 0) FROM bills", null);
        con.moveToFirst();
        long[] ketQua = {con.getLong(0), con.getLong(1)};
        con.close();
        return ketQua;
    }
}