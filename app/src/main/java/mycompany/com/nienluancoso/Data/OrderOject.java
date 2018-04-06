package mycompany.com.nienluancoso.Data;

/**
 * Created by Admin on 3/23/2018.
 */

public class OrderOject extends AgriObjectItem {

    private int soLuongMua;

    public OrderOject(int ID_AGRI, int ID_KIND, String NAME_AGRI, String IMG_URL_AGRI, float PRICE_AGRI, int soLuongMua) {
        super(ID_AGRI, ID_KIND, NAME_AGRI, IMG_URL_AGRI, PRICE_AGRI);
        this.soLuongMua = soLuongMua;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
