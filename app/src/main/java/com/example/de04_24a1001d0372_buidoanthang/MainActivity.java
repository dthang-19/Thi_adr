package com.example.de04_24a1001d0372_buidoanthang;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText edtMaKH, edtTenChuHo, edtChiSoCu, edtChiSoMoi;
    private Spinner spnLoaiKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMaKH = findViewById(R.id.edtMaKH);
        edtTenChuHo = findViewById(R.id.edtTenChuHo);
        edtChiSoCu = findViewById(R.id.edtChiSoCu);
        edtChiSoMoi = findViewById(R.id.edtChiSoMoi);
        spnLoaiKH = findViewById(R.id.spnLoaiKH);
        Button btnXoaTrang = findViewById(R.id.btnXoaTrang);
        Button btnTinhTien = findViewById(R.id.btnTinhTien);
        Button btnLichSu = findViewById(R.id.btnLichSu);

        edtMaKH.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10)});

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.customer_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiKH.setAdapter(adapter);

        btnXoaTrang.setOnClickListener(v -> xoaTrang());
        btnTinhTien.setOnClickListener(v -> tinhTien());
        btnLichSu.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
    }

    private void xoaTrang() {
        edtMaKH.setText("");
        edtTenChuHo.setText("");
        edtChiSoCu.setText("");
        edtChiSoMoi.setText("");
        edtMaKH.setError(null);
        edtTenChuHo.setError(null);
        edtChiSoCu.setError(null);
        edtChiSoMoi.setError(null);
        spnLoaiKH.setSelection(0);
        edtMaKH.requestFocus();
    }

    private Integer docChiSo(EditText oNhap) {
        try {
            int giaTri = Integer.parseInt(oNhap.getText().toString().trim());
            if (giaTri < 0) throw new NumberFormatException();
            return giaTri;
        } catch (NumberFormatException loi) {
            oNhap.setError(getString(R.string.err_index));
            return null;
        }
    }

    private boolean kiemTraDuLieu() {
        EditText oLoiDauTien = null;

        String maKH = edtMaKH.getText().toString().trim();
        if (!maKH.matches("^PD\\d{8}$")) {
            edtMaKH.setError(getString(R.string.err_code));
            oLoiDauTien = edtMaKH;
        }

        if (edtTenChuHo.getText().toString().trim().isEmpty()) {
            edtTenChuHo.setError(getString(R.string.err_name));
            if (oLoiDauTien == null) oLoiDauTien = edtTenChuHo;
        }

        Integer chiSoCu = docChiSo(edtChiSoCu);
        Integer chiSoMoi = docChiSo(edtChiSoMoi);
        if (chiSoCu == null && oLoiDauTien == null) oLoiDauTien = edtChiSoCu;
        if (chiSoMoi == null && oLoiDauTien == null) oLoiDauTien = edtChiSoMoi;

        if (chiSoCu != null && chiSoMoi != null && chiSoMoi < chiSoCu) {
            edtChiSoMoi.setError(getString(R.string.err_new_less));
            if (oLoiDauTien == null) oLoiDauTien = edtChiSoMoi;
        }

        if (oLoiDauTien != null) {
            oLoiDauTien.requestFocus();
            return false;
        }
        return true;
    }

    private void tinhTien() {
        if (!kiemTraDuLieu()) return;

        int chiSoCu = Integer.parseInt(edtChiSoCu.getText().toString().trim());
        int chiSoMoi = Integer.parseInt(edtChiSoMoi.getText().toString().trim());
        int soKwh = chiSoMoi - chiSoCu;
        int loai = spnLoaiKH.getSelectedItemPosition();

        long tienDien = ElectricCalculator.tinhTienDien(soKwh, loai);
        long thue = ElectricCalculator.tinhThue(tienDien);

        Bill hoaDon = new Bill();
        hoaDon.setMaKH(edtMaKH.getText().toString().trim());
        hoaDon.setTenChuHo(edtTenChuHo.getText().toString().trim());
        hoaDon.setLoaiKH(String.valueOf(spnLoaiKH.getSelectedItem()));
        hoaDon.setChiSoCu(chiSoCu);
        hoaDon.setChiSoMoi(chiSoMoi);
        hoaDon.setSoKwh(soKwh);
        hoaDon.setTienDien(tienDien);
        hoaDon.setThueGTGT(thue);
        hoaDon.setTongTien(tienDien + thue);
        hoaDon.setNgayLap(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));

        Intent manHinhKetQua = new Intent(this, ResultActivity.class);
        manHinhKetQua.putExtra("hoaDon", hoaDon);
        manHinhKetQua.putExtra("cacBac", ElectricCalculator.chiaBac(soKwh));
        manHinhKetQua.putExtra("cheDo", "moi");
        startActivity(manHinhKetQua);
    }
}
