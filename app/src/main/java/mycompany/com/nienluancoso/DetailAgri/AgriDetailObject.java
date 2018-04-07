
package mycompany.com.nienluancoso.DetailAgri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgriDetailObject {

    @SerializedName("ID_AGRI")
    @Expose
    private String iDAGRI;
    @SerializedName("NAME_AGRI")
    @Expose
    private String nAMEAGRI;
    @SerializedName("NAME_KIND")
    @Expose
    private String nAMEKIND;
    @SerializedName("DETAIL_AGRI")
    @Expose
    private String dETAILAGRI;
    @SerializedName("IMG_URL_AGRI")
    @Expose
    private String iMGURLAGRI;
    @SerializedName("PRICE_AGRI")
    @Expose
    private String pRICEAGRI;
    @SerializedName("AMOUNT_AGRI")
    @Expose
    private String aMOUNTAGRI;

    public String getIDAGRI() {
        return iDAGRI;
    }

    public void setIDAGRI(String iDAGRI) {
        this.iDAGRI = iDAGRI;
    }

    public String getNAMEAGRI() {
        return nAMEAGRI;
    }

    public void setNAMEAGRI(String nAMEAGRI) {
        this.nAMEAGRI = nAMEAGRI;
    }

    public String getNAMEKIND() {
        return nAMEKIND;
    }

    public void setNAMEKIND(String nAMEKIND) {
        this.nAMEKIND = nAMEKIND;
    }

    public String getDETAILAGRI() {
        return dETAILAGRI;
    }

    public void setDETAILAGRI(String dETAILAGRI) {
        this.dETAILAGRI = dETAILAGRI;
    }

    public String getIMGURLAGRI() {
        return iMGURLAGRI;
    }

    public void setIMGURLAGRI(String iMGURLAGRI) {
        this.iMGURLAGRI = iMGURLAGRI;
    }

    public String getPRICEAGRI() {
        return pRICEAGRI;
    }

    public void setPRICEAGRI(String pRICEAGRI) {
        this.pRICEAGRI = pRICEAGRI;
    }

    public String getAMOUNTAGRI() {
        return aMOUNTAGRI;
    }

    public void setAMOUNTAGRI(String aMOUNTAGRI) {
        this.aMOUNTAGRI = aMOUNTAGRI;
    }

}
