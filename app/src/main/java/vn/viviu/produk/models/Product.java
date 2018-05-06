package vn.viviu.produk.models;

import java.io.Serializable;

/**
 * Created by ViviU on 9/25/2017.
 */

public class Product implements Serializable{
    private int hesoQD, price, giaLe;
    private String productId, productName, description, producerId, maDVTC, maDVTL, image, industryId;
    private double vat;
    private boolean status;

    public Product(
            String productId,
            String productName,
            String industryId,
            String producerId,
            String maDVTC,
            String maDVTL,
            int hesoQD,
            int price,
            int giaLe,
            double vat,
            String image,
            String description,
            boolean status
    ) {
        this.productId = productId;
        this.hesoQD = hesoQD;
        this.price = price;
        this.giaLe = giaLe;
        this.productName = productName;
        this.description = description;
        this.producerId = producerId;
        this.maDVTC = maDVTC;
        this.maDVTL = maDVTL;
        this.image = image;
        this.vat = vat;
        this.status = status;
        this.industryId = industryId;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getHesoQD() {
        return hesoQD;
    }

    public void setHesoQD(int hesoQD) {
        this.hesoQD = hesoQD;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGiaLe() {
        return giaLe;
    }

    public void setGiaLe(int giaLe) {
        this.giaLe = giaLe;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getMaDVTC() {
        return maDVTC;
    }

    public void setMaDVTC(String maDVTC) {
        this.maDVTC = maDVTC;
    }

    public String getMaDVTL() {
        return maDVTL;
    }

    public void setMaDVTL(String maDVTL) {
        this.maDVTL = maDVTL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
