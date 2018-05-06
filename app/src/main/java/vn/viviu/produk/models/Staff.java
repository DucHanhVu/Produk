package vn.viviu.produk.models;

import java.io.Serializable;

/**
 * Created by ViviU on 11/9/2017.
 */

public class Staff implements Serializable {

    private String MaKho;

    private String MaND;

    private String MoTa;

    private String TenKho;

    private Boolean TrangThai;

    public String getMaKho() {
        return MaKho;
    }

    public void setMaKho(String maKho) {
        this.MaKho = maKho;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        this.MaND = maND;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        this.MoTa = moTa;
    }

    public String getTenKho() {
        return TenKho;
    }

    public void setTenKho(String tenKho) {
        this.TenKho = tenKho;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.TrangThai = trangThai;
    }
}
