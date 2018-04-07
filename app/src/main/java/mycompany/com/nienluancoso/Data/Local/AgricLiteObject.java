package mycompany.com.nienluancoso.Data.Local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgricLiteObject {

    @SerializedName("NAME_AGRI")
    @Expose
    private String nAMEAGRI;
    @SerializedName("IMG_URL_AGRI")
    @Expose
    private String iMGURLAGRI;
    @SerializedName("PRICE_AGRI")
    @Expose
    private String pRICEAGRI;
    @SerializedName("AMOUNT_AGRI")
    @Expose
    private String aMOUNTAGRI;

    public String getNAMEAGRI() {
        return nAMEAGRI;
    }

    public void setNAMEAGRI(String nAMEAGRI) {
        this.nAMEAGRI = nAMEAGRI;
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
