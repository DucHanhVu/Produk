package vn.viviu.produk.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ViviU on 11/9/2017.
 */

public class ChiTietBan implements Serializable {
    private String MaCTB;
    private String MaPhieuBan;
    private String MaSP;
    private String MaKho;
    private String DVT;
    private Integer SoLuong;
    private Integer DonGia;
    private Integer ThanhTien;
    private Integer ChietKhau;

    public ChiTietBan() {
    }

    public ChiTietBan(String maCTB, String maPhieuBan, String maSP, String maKho, String DVT, Integer soLuong, Integer donGia, Integer thanhTien, Integer chietKhau) {
        MaCTB = maCTB;
        MaPhieuBan = maPhieuBan;
        MaSP = maSP;
        MaKho = maKho;
        this.DVT = DVT;
        SoLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
        ChietKhau = chietKhau;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> post = new HashMap<>();
        post.put("MaCTB", MaCTB);
        post.put("MaPhieuBan", MaPhieuBan);
        post.put("MaSP", MaSP);
        post.put("MaKho", MaKho);
        post.put("DVT", DVT);
        post.put("SoLuong", SoLuong);
        post.put("DonGia", DonGia);
        post.put("ThanhTien", ThanhTien);
        post.put("ChietKhau", ChietKhau);

        Map<String, Object> result = new HashMap<>();
        result.put(MaCTB, post);
        return result;
    }

    public String getMaCTB() {
        return MaCTB;
    }


    public String getMaPhieuBan() {
        return MaPhieuBan;
    }


    public String getMaSP() {
        return MaSP;
    }


    public String getMaKho() {
        return MaKho;
    }


    public String getDVT() {
        return DVT;
    }


    public Integer getSoLuong() {
        return SoLuong;
    }


    public Integer getDonGia() {
        return DonGia;
    }


    public Integer getThanhTien() {
        return ThanhTien;
    }


    public Integer getChietKhau() {
        return ChietKhau;
    }

}
