package mycompany.com.nienluancoso.Data;

/**
 * Created by Admin on 3/23/2018.
 */

public class OrderItemObject extends AgriItemObject {

    private int soLuongMua;
    private int soLuongConLai_AGRI;

    public int getSoLuongConLai_AGRI() {
        return soLuongConLai_AGRI;
    }

    public void setSoLuongConLai_AGRI(int soLuongConLai_AGRI) {
        this.soLuongConLai_AGRI = soLuongConLai_AGRI;
    }

    public OrderItemObject(int ID_AGRI, int ID_KIND, String NAME_AGRI, String IMG_URL_AGRI, float PRICE_AGRI, int soLuongMua) {
        super(ID_AGRI, ID_KIND, NAME_AGRI, IMG_URL_AGRI, PRICE_AGRI);
        this.soLuongMua = soLuongMua;
    }

    public OrderItemObject() {
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public float getTongGiaMua() {
        return soLuongMua*getPRICE_AGRI();
    }



}
