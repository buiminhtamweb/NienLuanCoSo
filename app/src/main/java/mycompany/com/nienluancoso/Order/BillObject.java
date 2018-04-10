
package mycompany.com.nienluancoso.Order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillObject {

    @SerializedName("ID_ORDER")
    @Expose
    private String iDORDER;
    @SerializedName("NAME_USER")
    @Expose
    private String nAMEUSER;
    @SerializedName("DATE_BILL")
    @Expose
    private String dATEBILL;
    @SerializedName("TOTAL_ORDER")
    @Expose
    private String tOTALORDER;
    @SerializedName("AGRI_ORDER")
    @Expose
    private List<AgriOrderObject> aGRIORDER = null;

    public String getIDORDER() {
        return iDORDER;
    }

    public void setIDORDER(String iDORDER) {
        this.iDORDER = iDORDER;
    }

    public String getNAMEUSER() {
        return nAMEUSER;
    }

    public void setNAMEUSER(String nAMEUSER) {
        this.nAMEUSER = nAMEUSER;
    }

    public String getDATEBILL() {
        return dATEBILL;
    }

    public void setDATEBILL(String dATEBILL) {
        this.dATEBILL = dATEBILL;
    }

    public String getTOTALORDER() {
        return tOTALORDER;
    }

    public void setTOTALORDER(String tOTALORDER) {
        this.tOTALORDER = tOTALORDER;
    }

    public List<AgriOrderObject> getAGRIORDER() {
        return aGRIORDER;
    }

    public void setAGRIORDER(List<AgriOrderObject> aGRIORDER) {
        this.aGRIORDER = aGRIORDER;
    }

}
