
package mycompany.com.nienluancoso.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgriOrderObject {

    @SerializedName("ID_AGRI")
    @Expose
    private String iDAGRI;
    @SerializedName("NUM_OF_AGRI")
    @Expose
    private String nUMOFAGRI;
    @SerializedName("CURRENT_PRICE")
    @Expose
    private String cURRENTPRICE;

    public String getIDAGRI() {
        return iDAGRI;
    }

    public void setIDAGRI(String iDAGRI) {
        this.iDAGRI = iDAGRI;
    }

    public String getNUMOFAGRI() {
        return nUMOFAGRI;
    }

    public void setNUMOFAGRI(String nUMOFAGRI) {
        this.nUMOFAGRI = nUMOFAGRI;
    }

    public String getCURRENTPRICE() {
        return cURRENTPRICE;
    }

    public void setCURRENTPRICE(String cURRENTPRICE) {
        this.cURRENTPRICE = cURRENTPRICE;
    }

}
