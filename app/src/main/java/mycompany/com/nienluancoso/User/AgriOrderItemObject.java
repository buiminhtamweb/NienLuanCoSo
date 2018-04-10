
package mycompany.com.nienluancoso.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgriOrderItemObject {

    private String idAGRI;
    private String nAMEAGRI;
    private String iMGURLAGRI;
    private String pRICE_ORDER;
    private String nUM_ORDER;

    public String getIdAGRI() {
        return idAGRI;
    }

    public void setIdAGRI(String idAGRI) {
        this.idAGRI = idAGRI;
    }

    public String getnAMEAGRI() {
        return nAMEAGRI;
    }

    public void setnAMEAGRI(String nAMEAGRI) {
        this.nAMEAGRI = nAMEAGRI;
    }

    public String getiMGURLAGRI() {
        return iMGURLAGRI;
    }

    public void setiMGURLAGRI(String iMGURLAGRI) {
        this.iMGURLAGRI = iMGURLAGRI;
    }

    public String getpRICE_ORDER() {
        return pRICE_ORDER;
    }

    public void setpRICE_ORDER(String pRICE_ORDER) {
        this.pRICE_ORDER = pRICE_ORDER;
    }

    public String getnUM_ORDER() {
        return nUM_ORDER;
    }

    public void setnUM_ORDER(String nUM_ORDER) {
        this.nUM_ORDER = nUM_ORDER;
    }
}
