
package mycompany.com.nienluancoso.Order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderObject {

    @SerializedName("ID_ORDER")
    @Expose
    private String iDORDER;
    @SerializedName("DATE_ORDER")
    @Expose
    private String dATEORDER;
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

    public String getDATEORDER() {
        return dATEORDER;
    }

    public void setDATEORDER(String dATEORDER) {
        this.dATEORDER = dATEORDER;
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
