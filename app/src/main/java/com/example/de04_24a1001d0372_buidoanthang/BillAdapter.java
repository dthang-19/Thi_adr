package com.example.de04_24a1001d0372_buidoanthang;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BillAdapter extends ArrayAdapter<Bill> {

    public BillAdapter(Context context, List<Bill> danhSach) {
        super(context, 0, danhSach);
    }

    @NonNull
    @Override
    public View getView(int viTri, @Nullable View dongCu, @NonNull ViewGroup cha) {
        if (dongCu == null) {
            dongCu = LayoutInflater.from(getContext()).inflate(R.layout.item_bill, cha, false);
        }
        Bill hoaDon = getItem(viTri);

        ((TextView) dongCu.findViewById(R.id.tvTenItem)).setText(hoaDon.getTenChuHo());
        ((TextView) dongCu.findViewById(R.id.tvMoTaItem)).setText(
                getContext().getString(R.string.item_sub, hoaDon.getMaKH(), hoaDon.getSoKwh(), hoaDon.getNgayLap()));
        ((TextView) dongCu.findViewById(R.id.tvTongItem)).setText(
                getContext().getString(R.string.money_fmt, ElectricCalculator.dinhDangTien(hoaDon.getTongTien())));
        return dongCu;
    }
}
