package mycompany.com.nienluancoso.Data.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Order_List";
    private static final String TAG = "DatabaseHelper" ;

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

    public long insertOrder(String idOrder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBOrderObject.COLUMN_ID_ORDER, idOrder);
        // insert row
        long row = db.insert(DBOrderObject.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return row;
    }

    public long insertAgriOnOrder(String idAgri,String numOfAgri) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DBAgricOrderObject.COLUMN_ID_AGRI, idAgri);
        values.put(DBAgricOrderObject.COLUMN_NUM_OF_AGRI, numOfAgri);
        // insert row
        long row = db.insert(DBAgricOrderObject.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return row;
    }

    public DBOrderObject getOrder() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBOrderObject.TABLE_NAME + " LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

//        Cursor cursor = db.query(DBOrderObject.TABLE_NAME, null,"*",null
//                ,null,null,null,"1" );


        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            // prepare note object
            DBOrderObject orderObject = new DBOrderObject(
                    cursor.getString(cursor.getColumnIndex(DBOrderObject.COLUMN_ID_ORDER)),
                    cursor.getString(cursor.getColumnIndex(DBOrderObject.COLUMN_DATE_ORDER)));
            // close the db connection
            cursor.close();
            return orderObject;

        } else return null;


    }

    public List<DBAgricOrderObject> getAgricOnOrder() {
        List<DBAgricOrderObject> agricOrderObjectList = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBAgricOrderObject.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            for (int i = 1; i <= cursor.getCount() ; i++) {
                // prepare note object
                DBAgricOrderObject agricOrderObject = new DBAgricOrderObject(
                        cursor.getString(cursor.getColumnIndex(DBAgricOrderObject.COLUMN_ID_AGRI)),
                        cursor.getString(cursor.getColumnIndex(DBAgricOrderObject.COLUMN_NUM_OF_AGRI)));
                // close the db connection


                Log.e(TAG, "getAgricOnOrder: "+ cursor.getString(cursor.getColumnIndex(DBAgricOrderObject.COLUMN_ID_AGRI)) );
                agricOrderObjectList.add(agricOrderObject);
                cursor.moveToNext();
            }

            cursor.close();
            return agricOrderObjectList;

        } else return null;


    }

    public void deleteAllOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Drop older table if existed
//        String sql = "DROP TABLE IF EXISTS ";
//        db.execSQL(sql + DBOrderObject.TABLE_NAME);
//        db.execSQL(sql + DBAgricOrderObject.CREATE_TABLE);
//        db.close();
    }

    public void deleteAgricOnOrder(String idAgri) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBAgricOrderObject.TABLE_NAME, DBAgricOrderObject.COLUMN_ID_AGRI + " = ?",
                new String[]{String.valueOf(idAgri)});
        db.close();
    }

    public int updateNumOfAgric(String idAgric, String numOfAgric) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBAgricOrderObject.COLUMN_NUM_OF_AGRI, numOfAgric);

        // updating row
        return db.update(DBAgricOrderObject.TABLE_NAME, values, DBAgricOrderObject.COLUMN_ID_AGRI + " = ?",
                new String[]{String.valueOf(idAgric)});
    }



}
