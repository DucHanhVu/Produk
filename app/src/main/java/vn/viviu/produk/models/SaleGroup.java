package vn.viviu.produk.models;


import java.io.Serializable;

/**
 * Created by ViviU on 11/9/2017.
 */

public class SaleGroup implements Serializable {

    private String MaKV;

    private Integer MaNhom;

    private String TenNhom;

    private Boolean TrangThai;

    public SaleGroup(String maKV, Integer maNhom, String tenNhom, Boolean trangThai) {
        MaKV = maKV;
        MaNhom = maNhom;
        TenNhom = tenNhom;
        TrangThai = trangThai;
    }

    public SaleGroup() {
    }

    public String getMaKV() {
        return MaKV;
    }

    public void setMaKV(String maKV) {
        this.MaKV = maKV;
    }

    public Integer getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(Integer maNhom) {
        this.MaNhom = maNhom;
    }

    public String getTenNhom() {
        return TenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.TenNhom = tenNhom;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.TrangThai = trangThai;
    }
}
