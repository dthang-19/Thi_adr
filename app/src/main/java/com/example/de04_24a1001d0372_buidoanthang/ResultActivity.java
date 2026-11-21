package com.example.de04_24a1001d0372_buidoanthang;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
    private Bill hoaDon;
    private Button btnLuu;
    private boolean daLuu = false;
    private TableLayout bangChiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        hoaDon = (Bill) getIntent().getSerializableExtra("hoaDon");
        int[] cacBac = getIntent().getIntArrayExtra("cacBac");
        String cheDo = getIntent().getStringExtra("cheDo");
        if (savedInstanceState != null) daLuu = savedInstanceState.getBoolean("daLuu");

        bangChiTiet = findViewById(R.id.bangChiTiet);
        btnLuu = findViewById(R.id.btnLuu);
        Button btnQuayLai = findViewById(R.id.btnQuayLai);

        int loai = Arrays.asList(getResources().getStringArray(R.array.customer_types))
                .indexOf(hoaDon.getLoaiKH());

        hienThiThongTinKH();
        hienThiBangChiTiet(cacBac, loai);
        hienThiTongHop(loai);

        btnQuayLai.setOnClickListener(v -> finish());

        if ("xem".equals(cheDo)) {
            btnLuu.setVisibility(View.GONE);
        } else {
            btnLuu.setEnabled(!daLuu);
            btnLuu.setOnClickListener(v -> luuHoaDon());
        }
    }

    private void hienThiThongTinKH() {
        ((TextView) findViewById(R.id.tvMaKH)).setText(getString(R.string.info_code, hoaDon.getMaKH()));
        ((TextView) findViewById(R.id.tvTenChuHo)).setText(getString(R.string.info_owner, hoaDon.getTenChuHo()));
        ((TextView) findViewById(R.id.tvLoaiKH)).setText(getString(R.string.info_type, hoaDon.getLoaiKH()));
        ((TextView) findViewById(R.id.tvChiSo)).setText(getString(R.string.info_index,
                hoaDon.getChiSoCu(), hoaDon.getChiSoMoi(), hoaDon.getSoKwh()));
        ((TextView) findViewById(R.id.tvNgayLap)).setText(getString(R.string.info_date, hoaDon.getNgayLap()));
    }

    private void hienThiBangChiTiet(int[] cacBac, int loai) {
        if (loai == ElectricCalculator.LOAI_KINH_DOANH) {
            themDong(getString(R.string.flat_rate), String.valueOf(hoaDon.getSoKwh()),
                    ElectricCalculator.dinhDangTien(ElectricCalculator.DON_GIA_KINH_DOANH),
                    ElectricCalculator.dinhDangTien((long) hoaDon.getSoKwh() * ElectricCalculator.DON_GIA_KINH_DOANH));
            return;
        }
        for (int i = 0; i < cacBac.length; i++) {
            if (cacBac[i] > 0) {
                themDong(getString(R.string.tier_n, i + 1), String.valueOf(cacBac[i]),
                        ElectricCalculator.dinhDangTien(ElectricCalculator.DON_GIA[i]),
                        ElectricCalculator.dinhDangTien((long) cacBac[i] * ElectricCalculator.DON_GIA[i]));
            }
        }
    }

    private void themDong(String... cacO) {
        TableRow dong = new TableRow(this);
        for (String noiDung : cacO) {
            TextView o = new TextView(this);
            o.setText(noiDung);
            o.setTextColor(getColor(R.color.text_dark));
            o.setPadding(8, 8, 8, 8);
            o.setGravity(Gravity.CENTER);
            dong.addView(o);
        }
        bangChiTiet.addView(dong);
    }

    private void hienThiTongHop(int loai) {
        long tienGoc = ElectricCalculator.tinhTienTruocGiam(hoaDon.getSoKwh(), loai);
        long soTienGiam = tienGoc - hoaDon.getTienDien();
        TextView tvGiamGia = findViewById(R.id.tvGiamGia);

        ((TextView) findViewById(R.id.tvTienDien)).setText(
                getString(R.string.label_electric) + ": " + dinhDang(hoaDon.getTienDien()));

        if (loai == ElectricCalculator.LOAI_HO_NGHEO) {
            tvGiamGia.setText(getString(R.string.label_discount) + ": -" + dinhDang(soTienGiam));
        } else {
            tvGiamGia.setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.tvThue)).setText(
                getString(R.string.label_vat) + ": " + dinhDang(hoaDon.getThueGTGT()));
        ((TextView) findViewById(R.id.tvTongTien)).setText(
                getString(R.string.label_total) + ": " + dinhDang(hoaDon.getTongTien()));
    }

    private String dinhDang(long soTien) {
        return getString(R.string.money_fmt, ElectricCalculator.dinhDangTien(soTien));
    }

    private void luuHoaDon() {
        new DBHelper(this).themHoaDon(hoaDon);
        daLuu = true;
        btnLuu.setEnabled(false);
        Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("daLuu", daLuu);
    }
}