package vn.viviu.produk.models;



import java.io.Serializable;

/**
 * Created by ViviU on 11/9/2017.
 */

public class Stream implements Serializable {

    private String MaKV;

    private String MaND;

    private Integer MaNhom;

    private String MaTuyen;

    private String MoTa;

    private String TenTuyen;

    private String TrangThai;

    public String getMaKV() {
        return MaKV;
    }

    public void setMaKV(String maKV) {
        this.MaKV = maKV;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        this.MaND = maND;
    }

    public Integer getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(Integer maNhom) {
        this.MaNhom = maNhom;
    }

    public String getMaTuyen() {
        return MaTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.MaTuyen = maTuyen;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        this.MoTa = moTa;
    }

    public String getTenTuyen() {
        return TenTuyen;
    }

    public void setTenTuyen(String tenTuyen) {
        this.TenTuyen = tenTuyen;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        this.TrangThai = trangThai;
    }
}
