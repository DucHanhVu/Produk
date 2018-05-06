package vn.viviu.produk.models;

/**
 * Created by ViviU on 10/3/2017.
 */

public class Industry {
    private String industryId, industryName;
    private boolean status;

    public Industry(String industryId, String industryName, boolean status) {
        this.industryId = industryId;
        this.industryName = industryName;
        this.status = status;
    }

    public Industry() {
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
