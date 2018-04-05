package mycompany.com.nienluancoso.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


public class KindObject {
    @SerializedName("ID_KIND")
    @Expose
    private String iDKIND;
    @SerializedName("NAME_KIND")
    @Expose
    private String nAMEKIND;

    public KindObject() {
    }

    public KindObject(String iDKIND, String nAMEKIND) {
        this.iDKIND = iDKIND;
        this.nAMEKIND = nAMEKIND;
    }

    public String getIDKIND() {
        return iDKIND;
    }

    public void setIDKIND(String iDKIND) {
        this.iDKIND = iDKIND;
    }

    public String getNAMEKIND() {
        return nAMEKIND;
    }

    public void setNAMEKIND(String nAMEKIND) {
        this.nAMEKIND = nAMEKIND;
    }
}
