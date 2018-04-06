package mycompany.com.nienluancoso.Data.Local;

public class DBAgricOrderObject {


    public static final String TABLE_NAME = "AGRI_ORDER";

    public static final String COLUMN_ID_AGRI = "ID_AGRI";
    public static final String COLUMN_CURRENT_PRICE = "CURRENT_PRICE";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID_AGRI + " INTEGER PRIMARY KEY,"
                    + COLUMN_CURRENT_PRICE + " float(8,2)"
                    + ")";


    private String ID_AGRI;
    private String CURRENT_PRICE;

    public String getID_AGRI() {
        return ID_AGRI;
    }

    public void setID_AGRI(String ID_AGRI) {
        this.ID_AGRI = ID_AGRI;
    }

    public String getCURRENT_PRICE() {
        return CURRENT_PRICE;
    }

    public void setCURRENT_PRICE(String CURRENT_PRICE) {
        this.CURRENT_PRICE = CURRENT_PRICE;
    }
}
