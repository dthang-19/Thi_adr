package com.example.de04_24a1001d0372_buidoanthang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ListView lvHoaDon;
    private TextView tvThongKe, tvTrong;
    private DBHelper csdl;
    private List<Bill> danhSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHoaDon = findViewById(R.id.lvHoaDon);
        tvThongKe = findViewById(R.id.tvThongKe);
        tvTrong = findViewById(R.id.tvTrong);
        csdl = new DBHelper(this);

        lvHoaDon.setOnItemClickListener((cha, dong, viTri, id) -> {
            Bill hoaDon = danhSach.get(viTri);
            Intent manHinhKetQua = new Intent(this, ResultActivity.class);
            manHinhKetQua.putExtra("hoaDon", hoaDon);
            manHinhKetQua.putExtra("cacBac", ElectricCalculator.chiaBac(hoaDon.getSoKwh()));
            manHinhKetQua.putExtra("cheDo", "xem");
            startActivity(manHinhKetQua);
        });

        lvHoaDon.setOnItemLongClickListener((cha, dong, viTri, id) -> {
            Bill hoaDon = danhSach.get(viTri);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dlg_title)
                    .setMessage(getString(R.string.dlg_msg, hoaDon.getTenChuHo()))
                    .setPositiveButton(R.string.dlg_delete, (hopThoai, nut) -> {
                        csdl.xoaHoaDon(hoaDon.getId());
                        taiDuLieu();
                    })
                    .setNegativeButton(R.string.dlg_cancel, null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taiDuLieu();
    }

    private void taiDuLieu() {
        danhSach = csdl.layTatCaHoaDon();
        lvHoaDon.setAdapter(new BillAdapter(this, danhSach));

        long[] thongKe = csdl.layThongKe();
        tvThongKe.setText(getString(R.string.stat_fmt, (int) thongKe[0], ElectricCalculator.dinhDangTien(thongKe[1])));

        tvTrong.setVisibility(danhSach.isEmpty() ? View.VISIBLE : View.GONE);
    }
}