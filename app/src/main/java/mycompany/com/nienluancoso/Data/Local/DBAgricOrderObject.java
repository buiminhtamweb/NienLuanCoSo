package mycompany.com.nienluancoso.Data.Local;

public class DBAgricOrderObject {


    public static final String TABLE_NAME = "AGRI_ORDER";

    public static final String COLUMN_ID_AGRI = "ID_AGRI";
    public static final String COLUMN_NUM_OF_AGRI = "NUM_OF_AGRI";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID_AGRI + " INTEGER PRIMARY KEY,"
                    + COLUMN_NUM_OF_AGRI + " INTEGER "
                    + ")";


    private String ID_AGRI;
    private String NUM_OF_AGRI;

    public DBAgricOrderObject(String ID_AGRI, String NUM_OF_AGRI) {
        this.ID_AGRI = ID_AGRI;
        this.NUM_OF_AGRI = NUM_OF_AGRI;
    }

    public String getNUM_OF_AGRI() {
        return NUM_OF_AGRI;
    }

    public void setNUM_OF_AGRI(String NUM_OF_AGRI) {
        this.NUM_OF_AGRI = NUM_OF_AGRI;
    }

    public String getID_AGRI() {
        return ID_AGRI;
    }

    public void setID_AGRI(String ID_AGRI) {
        this.ID_AGRI = ID_AGRI;
    }

}
