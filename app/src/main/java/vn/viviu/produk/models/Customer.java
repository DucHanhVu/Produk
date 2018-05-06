package vn.viviu.produk.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by ViviU on 11/5/2017.
 */
@IgnoreExtraProperties
public class Customer implements Serializable {
    private String MaKH;
    private String TenKH;
    private String MaLoaiKH;
    private String MaKV;
    private String MaTuyen;
    private String DiaChi;
    private String NguoiLienHe;
    private String ChucVu;
    private Integer SDT;
    private String Email;
    private String Website;
    private String HinhAnh;
    private Integer HanMucCN;
    private String GhiChu;
    private boolean TrangThai;

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("MaKH", MaKH);
        result.put("TenKH", TenKH);
        result.put("MaLoaiKH", MaLoaiKH);
        result.put("MaTuyen", MaTuyen);
        result.put("DiaChi", DiaChi);
        result.put("NguoiLienHe", NguoiLienHe);
        result.put("ChucVu", ChucVu);
        result.put("SDT", SDT);
        result.put("Email", Email);
        result.put("Website", Website);
        result.put("HinhAnh", HinhAnh);
        result.put("HanMucCN", HanMucCN);
        result.put("GhiChu", GhiChu);
        result.put("TrangThai", TrangThai);
        return result;
    }

    public Customer() {
    }

    public Customer(String maKH, String tenKH, String maLoaiKH, String maKV, String maTuyen, String diaChi, String diaChiGiaoHang, String nguoiLienHe, String chucVu, Integer SDT, String email, String website, String hinhAnh, Integer hanMucCN, String ghiChu, boolean trangThai) {
        MaKH = maKH;
        TenKH = tenKH;
        MaLoaiKH = maLoaiKH;
        MaKV = maKV;
        MaTuyen = maTuyen;
        DiaChi = diaChi;
        NguoiLienHe = nguoiLienHe;
        ChucVu = chucVu;
        this.SDT = SDT;
        Email = email;
        Website = website;
        HinhAnh = hinhAnh;
        HanMucCN = hanMucCN;
        GhiChu = ghiChu;
        TrangThai = trangThai;
    }

    public String getMaKH() {
        return MaKH;
    }


    public String getTenKH() {
        return TenKH;
    }


    public String getMaLoaiKH() {
        return MaLoaiKH;
    }


    public String getMaKV() {
        return MaKV;
    }


    public String getMaTuyen() {
        return MaTuyen;
    }


    public String getDiaChi() {
        return DiaChi;
    }


    public String getNguoiLienHe() {
        return NguoiLienHe;
    }


    public String getChucVu() {
        return ChucVu;
    }


    public Integer getSDT() {
        return SDT;
    }


    public String getEmail() {
        return Email;
    }


    public String getWebsite() {
        return Website;
    }


    public String getHinhAnh() {
        return HinhAnh;
    }


    public Integer getHanMucCN() {
        return HanMucCN;
    }


    public String getGhiChu() {
        return GhiChu;
    }


    public boolean isTrangThai() {
        return TrangThai;
    }

}
