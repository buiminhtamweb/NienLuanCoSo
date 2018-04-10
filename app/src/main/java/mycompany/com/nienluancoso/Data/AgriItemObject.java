package mycompany.com.nienluancoso.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by buimi on 3/13/2018.
 */

public class AgriItemObject {

    @SerializedName("ID_AGRI")
    @Expose
    private int ID_AGRI;

    @SerializedName("ID_KIND")
    @Expose
    private int ID_KIND;

    @SerializedName("NAME_AGRI")
    @Expose
    private String NAME_AGRI;

    @SerializedName("IMG_URL_AGRI")
    @Expose
    private String IMG_URL_AGRI;

    @SerializedName("PRICE_AGRI")
    @Expose
    private float PRICE_AGRI;

    @SerializedName("OLD_PRICE")
    @Expose
    private String oLDPRICE;

    public AgriItemObject(int ID_AGRI, int ID_KIND, String NAME_AGRI, String IMG_URL_AGRI, float PRICE_AGRI) {
        this.ID_AGRI = ID_AGRI;
        this.ID_KIND = ID_KIND;
        this.NAME_AGRI = NAME_AGRI;
        this.IMG_URL_AGRI = IMG_URL_AGRI;
        this.PRICE_AGRI = PRICE_AGRI;
    }

    public AgriItemObject() {
    }

    public int getID_AGRI() {
        return ID_AGRI;
    }

    public void setID_AGRI(int ID_AGRI) {
        this.ID_AGRI = ID_AGRI;
    }

    public int getID_KIND() {
        return ID_KIND;
    }

    public void setID_KIND(int ID_KIND) {
        this.ID_KIND = ID_KIND;
    }

    public String getNAME_AGRI() {
        return NAME_AGRI;
    }

    public void setNAME_AGRI(String NAME_AGRI) {
        this.NAME_AGRI = NAME_AGRI;
    }


    public String getIMG_URL_AGRI() {
        return IMG_URL_AGRI;
    }

    public void setIMG_URL_AGRI(String IMG_URL_AGRI) {
        this.IMG_URL_AGRI = IMG_URL_AGRI;
    }

    public float getPRICE_AGRI() {
        return PRICE_AGRI;
    }

    public void setPRICE_AGRI(float PRICE_AGRI) {
        this.PRICE_AGRI = PRICE_AGRI;
    }

    public String getoLDPRICE() {
        return oLDPRICE;
    }

    public void setoLDPRICE(String oLDPRICE) {
        this.oLDPRICE = oLDPRICE;
    }
}
