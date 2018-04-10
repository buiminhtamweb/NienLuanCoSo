
package mycompany.com.nienluancoso.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgriOrderObject {

    @SerializedName("ID_AGRI")
    @Expose
    private String idAgri;

    @SerializedName("NUM_OF_AGRI")
    @Expose
    private String numOfAgri;

    @SerializedName("CURRENT_PRICE")
    @Expose
    private String currentPrice;

    public AgriOrderObject(String idAgri, String numOfAgri, String currentPrice) {
        this.idAgri = idAgri;
        this.numOfAgri = numOfAgri;
        this.currentPrice = currentPrice;
    }

    public String getIDAGRI() {
        return idAgri;
    }

    public void setIDAGRI(String iDAGRI) {
        this.idAgri = iDAGRI;
    }

    public String getNUMOFAGRI() {
        return numOfAgri;
    }

    public void setNUMOFAGRI(String nUMOFAGRI) {
        this.numOfAgri = nUMOFAGRI;
    }

    public String getCURRENTPRICE() {
        return currentPrice;
    }

    public void setCURRENTPRICE(String cURRENTPRICE) {
        this.currentPrice = cURRENTPRICE;
    }

}
