package vn.viviu.produk.models;



import java.io.Serializable;
/**
 * Created by ViviU on 11/9/2017.
 */

public class Provider implements Serializable {

    private String DiaChi;

    private String MaNCC;

    private Integer MaSoThue;

    private Integer SDT;

    private String TenNCC;

    private String TrangThai;

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

    public Integer getMaSoThue() {
        return MaSoThue;
    }

    public void setMaSoThue(Integer maSoThue) {
        this.MaSoThue = maSoThue;
    }

    public Integer getSDT() {
        return SDT;
    }

    public void setSDT(Integer sDT) {
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
