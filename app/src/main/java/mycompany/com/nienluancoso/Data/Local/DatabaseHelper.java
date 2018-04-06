package mycompany.com.nienluancoso.Data.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Order_List";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(DBOrderObject.CREATE_TABLE);
        db.execSQL(DBAgricOrderObject.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        String sql = "DROP TABLE IF EXISTS ";
        db.execSQL(sql + DBOrderObject.TABLE_NAME);
        db.execSQL(sql + DBAgricOrderObject.CREATE_TABLE);

        // Create tables again
        onCreate(db);
    }

    public long insertOrder(String idOrder, String id_Cus, String totalCost ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DBOrderObject.COLUMN_ID_ORDER, idOrder);
        values.put(DBOrderObject.COLUMN_USERNAME_CUS, id_Cus);
        values.put(DBOrderObject.COLUMN_TOTAL_ORDER, totalCost);

        // insert row
        long id = db.insert(DBOrderObject.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

}
