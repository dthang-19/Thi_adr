package com.example.de04_24a1001d0372_buidoanthang;
import java.io.Serializable;

public class Bill implements Serializable {
    private int id;
    private String maKH;
    private String tenChuHo;
    private String loaiKH;
    private int chiSoCu;
    private int chiSoMoi;
    private int soKwh;
    private long tienDien;
    private long thueGTGT;
    private long tongTien;
    private String ngayLap;

    public Bill() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getTenChuHo() { return tenChuHo; }
    public void setTenChuHo(String tenChuHo) { this.tenChuHo = tenChuHo; }

    public String getLoaiKH() { return loaiKH; }
    public void setLoaiKH(String loaiKH) { this.loaiKH = loaiKH; }

    public int getChiSoCu() { return chiSoCu; }
    public void setChiSoCu(int chiSoCu) { this.chiSoCu = chiSoCu; }

    public int getChiSoMoi() { return chiSoMoi; }
    public void setChiSoMoi(int chiSoMoi) { this.chiSoMoi = chiSoMoi; }

    public int getSoKwh() { return soKwh; }
    public void setSoKwh(int soKwh) { this.soKwh = soKwh; }

    public long getTienDien() { return tienDien; }
    public void setTienDien(long tienDien) { this.tienDien = tienDien; }

    public long getThueGTGT() { return thueGTGT; }
    public void setThueGTGT(long thueGTGT) { this.thueGTGT = thueGTGT; }

    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }

    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }
}
