package vn.viviu.produk.models;

import java.io.Serializable;

/**
 * Created by ViviU on 11/9/2017.
 */

public class Area implements Serializable {
    private String MaKV;
    private String TenKV;

    public Area() {
    }

    public String getMaKV() {
        return MaKV;
    }


    public String getTenKV() {
        return TenKV;
    }
}
