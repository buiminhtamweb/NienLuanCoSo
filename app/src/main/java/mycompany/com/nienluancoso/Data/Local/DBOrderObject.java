package mycompany.com.nienluancoso.Data.Local;

public class DBOrderObject {

    public static final String TABLE_NAME = "ORDER";

    public static final String COLUMN_ID_ORDER = "ID_ORDER";
    public static final String COLUMN_DATE_ORDER = "DATE_ORDER";
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID_ORDER + " INTEGER PRIMARY KEY,"
                    + COLUMN_DATE_ORDER + " DATETIME DEFAULT CURRENT_TIMESTAMP "
                    + ")";


    private String ID_ORDER;
    private String DATE_ORDER;

    public DBOrderObject(String ID_ORDER, String DATE_ORDER) {
        this.ID_ORDER = ID_ORDER;
        this.DATE_ORDER = DATE_ORDER;
    }

    public String getID_ORDER() {
        return ID_ORDER;
    }

    public void setID_ORDER(String ID_ORDER) {
        this.ID_ORDER = ID_ORDER;
    }

    public String getDATE_ORDER() {
        return DATE_ORDER;
    }

    public void setDATE_ORDER(String DATE_ORDER) {
        this.DATE_ORDER = DATE_ORDER;
    }

}
