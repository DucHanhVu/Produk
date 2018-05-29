package vn.viviu.produk.models;



import java.io.Serializable;
/**
 * Created by ViviU on 11/9/2017.
 */

public class Provider implements Serializable {

    private String DiaChi;

    private String MaNCC;

    private long MaSoThue;

    private long SDT;

    private String TenNCC;

    private String TrangThai;

    public Provider() {
    }

    public Provider(String diaChi, String maNCC, long maSoThue, long SDT, String tenNCC, String trangThai) {
        DiaChi = diaChi;
        MaNCC = maNCC;
        MaSoThue = maSoThue;
        this.SDT = SDT;
        TenNCC = tenNCC;
        TrangThai = trangThai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        this.MaNCC = maNCC;
    }

    public long getMaSoThue() {
        return MaSoThue;
    }

    public void setMaSoThue(long maSoThue) {
        this.MaSoThue = maSoThue;
    }

    public long getSDT() {
        return SDT;
    }

    public void setSDT(long sDT) {
        this.SDT = sDT;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.TenNCC = tenNCC;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        this.TrangThai = trangThai;
    }
}
