package com.example.de04_24a1001d0372_buidoanthang;

import java.text.NumberFormat;
import java.util.Locale;

public class ElectricCalculator {
    public static final int LOAI_HO_GIA_DINH = 0;
    public static final int LOAI_HO_NGHEO = 1;
    public static final int LOAI_KINH_DOANH = 2;

    public static final int[] GIOI_HAN_BAC = {50, 50, 100, 100, 100, Integer.MAX_VALUE};
    public static final int[] DON_GIA = {1800, 1900, 2200, 2700, 3000, 3200};
    public static final int DON_GIA_KINH_DOANH = 3500;

    public static int[] chiaBac(int soKwh) {
        int[] ketQua = new int[6];
        int conLai = soKwh;
        for (int i = 0; i < GIOI_HAN_BAC.length && conLai > 0; i++) {
            int daDung = Math.min(conLai, GIOI_HAN_BAC[i]);
            ketQua[i] = daDung;
            conLai -= daDung;
        }
        return ketQua;
    }

    public static long tinhTienTruocGiam(int soKwh, int loai) {
        if (loai == LOAI_KINH_DOANH) return (long) soKwh * DON_GIA_KINH_DOANH;

        int[] cacBac = chiaBac(soKwh);
        long tien = 0;
        for (int i = 0; i < cacBac.length; i++) {
            tien += (long) cacBac[i] * DON_GIA[i];
        }
        return tien;
    }

    public static long tinhTienDien(int soKwh, int loai) {
        long tienGoc = tinhTienTruocGiam(soKwh, loai);
        return loai == LOAI_HO_NGHEO ? Math.round(tienGoc * 0.8) : tienGoc;
    }

    public static long tinhThue(long tienDien) {
        return Math.round(tienDien * 0.1);
    }

    public static String dinhDangTien(long soTien) {
        return NumberFormat.getInstance(new Locale("vi", "VN")).format(soTien);
    }
}