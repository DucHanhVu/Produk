package vn.viviu.produk.models;

import java.io.Serializable;

/**
 * Created by ViviU on 11/5/2017.
 */

public class CustomerGroup implements Serializable {

    private String MaLoaiKH;

    private String TenLoaiKH;

    public CustomerGroup(String maLoaiKH, String tenLoaiKH) {
        MaLoaiKH = maLoaiKH;
        TenLoaiKH = tenLoaiKH;
    }

    public CustomerGroup() {
    }

    public String getMaLoaiKH() {
        return MaLoaiKH;
    }


    public String getTenLoaiKH() {
        return TenLoaiKH;
    }

}
