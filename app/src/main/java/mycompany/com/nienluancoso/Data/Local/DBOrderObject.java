package mycompany.com.nienluancoso.Data.Local;

public class DBOrderObject {


    public static final String TABLE_NAME = "Order";

    public static final String COLUMN_ID_ORDER = "ID_ORDER";
    public static final String COLUMN_USERNAME_CUS = "USERNAME_CUS";
    public static final String COLUMN_DATE_ORDER = "DATE_ORDER";
    public static final String COLUMN_TOTAL_ORDER = "TOTAL_ORDER";
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID_ORDER + " INTEGER PRIMARY KEY,"
                    + COLUMN_USERNAME_CUS + " TEXT,"
                    + COLUMN_DATE_ORDER + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                    + COLUMN_TOTAL_ORDER + " TEXT"
                    + ")";


    private String ID_ORDER;
    private String USERNAME_CUS;
    private String DATE_ORDER;
    private String TOTAL_ORDER;

    public String getID_ORDER() {
        return ID_ORDER;
    }

    public void setID_ORDER(String ID_ORDER) {
        this.ID_ORDER = ID_ORDER;
    }

    public String getUSERNAME_CUS() {
        return USERNAME_CUS;
    }

    public void setUSERNAME_CUS(String USERNAME_CUS) {
        this.USERNAME_CUS = USERNAME_CUS;
    }

    public String getDATE_ORDER() {
        return DATE_ORDER;
    }

    public void setDATE_ORDER(String DATE_ORDER) {
        this.DATE_ORDER = DATE_ORDER;
    }

    public String getTOTAL_ORDER() {
        return TOTAL_ORDER;
    }

    public void setTOTAL_ORDER(String TOTAL_ORDER) {
        this.TOTAL_ORDER = TOTAL_ORDER;
    }
}
