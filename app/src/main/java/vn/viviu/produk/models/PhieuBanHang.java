package vn.viviu.produk.models;

import java.io.Serializable;

/**
 * Created by ViviU on 11/9/2017.
 */

public class PhieuBanHang implements Serializable {
    private String MaKH;
    private Integer MaNhom;
    private String MaPhieuBan;
    private String MaTuyen;
    private String NgayDat;
    private String NgayGiao;
    private String NguoiBan;
    private String NguoiDat;
    private Integer Status;
    private Integer ThanhToanTruoc;
    private Integer TongTien;

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        this.MaKH = maKH;
    }

    public Integer getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(Integer maNhom) {
        this.MaNhom = maNhom;
    }

    public String getMaPhieuBan() {
        return MaPhieuBan;
    }

    public void setMaPhieuBan(String maPhieuBan) {
        this.MaPhieuBan = maPhieuBan;
    }

    public String getMaTuyen() {
        return MaTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.MaTuyen = maTuyen;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.NgayDat = ngayDat;
    }

    public String getNgayGiao() {
        return NgayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.NgayGiao = ngayGiao;
    }

    public String getNguoiBan() {
        return NguoiBan;
    }

    public void setNguoiBan(String nguoiBan) {
        this.NguoiBan = nguoiBan;
    }

    public String getNguoiDat() {
        return NguoiDat;
    }

    public void setNguoiDat(String nguoiDat) {
        this.NguoiDat = nguoiDat;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        this.Status = status;
    }

    public Integer getThanhToanTruoc() {
        return ThanhToanTruoc;
    }

    public void setThanhToanTruoc(Integer thanhToanTruoc) {
        this.ThanhToanTruoc = thanhToanTruoc;
    }

    public Integer getTongTien() {
        return TongTien;
    }

    public void setTongTien(Integer tongTien) {
        this.TongTien = tongTien;
    }
}
