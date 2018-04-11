package mycompany.com.nienluancoso.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCusObject {

    @SerializedName("FULLNAME_CUS")
    @Expose
    private String fULLNAMECUS;
    @SerializedName("SEX")
    @Expose
    private String sEX;
    @SerializedName("BIRTHDAY")
    @Expose
    private String bIRTHDAY;
    @SerializedName("IMG_URL_CUS")
    @Expose
    private String iMGURLCUS;
    @SerializedName("TEL_CUS")
    @Expose
    private String tELCUS;
    @SerializedName("ADDRESS_CUS")
    @Expose
    private String aDDRESSCUS;

    public String getFULLNAMECUS() {
        return fULLNAMECUS;
    }

    public void setFULLNAMECUS(String fULLNAMECUS) {
        this.fULLNAMECUS = fULLNAMECUS;
    }

    public String getSEX() {
        return sEX;
    }

    public void setSEX(String sEX) {
        this.sEX = sEX;
    }

    public String getBIRTHDAY() {
        return bIRTHDAY;
    }

    public void setBIRTHDAY(String bIRTHDAY) {
        this.bIRTHDAY = bIRTHDAY;
    }

    public String getIMGURLCUS() {
        return iMGURLCUS;
    }

    public void setIMGURLCUS(String iMGURLCUS) {
        this.iMGURLCUS = iMGURLCUS;
    }

    public String getTELCUS() {
        return tELCUS;
    }

    public void setTELCUS(String tELCUS) {
        this.tELCUS = tELCUS;
    }

    public String getADDRESSCUS() {
        return aDDRESSCUS;
    }

    public void setADDRESSCUS(String aDDRESSCUS) {
        this.aDDRESSCUS = aDDRESSCUS;
    }

}
