package mycompany.com.nienluancoso.Data;

import java.util.List;

/**
 * Created by Admin on 3/28/2018.
 */

public class HoaDonObject {
    private String ID;
    private String NGAYMUA;
    private String NGUOIBAN;
    private List<OrderOject> NONGSANMUA;

    public HoaDonObject(String ID, String NGAYMUA, String NGUOIBAN, List<OrderOject> NONGSANMUA) {
        this.ID = ID;
        this.NGAYMUA = NGAYMUA;
        this.NGUOIBAN = NGUOIBAN;
        this.NONGSANMUA = NONGSANMUA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNGAYMUA() {
        return NGAYMUA;
    }

    public void setNGAYMUA(String NGAYMUA) {
        this.NGAYMUA = NGAYMUA;
    }

    public String getNGUOIBAN() {
        return NGUOIBAN;
    }

    public void setNGUOIBAN(String NGUOIBAN) {
        this.NGUOIBAN = NGUOIBAN;
    }

    public List<OrderOject> getNONGSANMUA() {
        return NONGSANMUA;
    }

    public void setNONGSANMUA(List<OrderOject> NONGSANMUA) {
        this.NONGSANMUA = NONGSANMUA;
    }
}
