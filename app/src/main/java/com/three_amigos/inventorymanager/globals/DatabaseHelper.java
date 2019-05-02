package com.three_amigos.inventorymanager.globals;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    final Context CONTEXT;
    static final String DATABASE_NAME="Inventory.db";
    static final String TABLENAME="kitchen_inventory";
    static final int DATABASE_VERSION = 1;


    /**
     * INITIATES:
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.CONTEXT = context;
    }

    /**
     * IMPLEMENTED BY DEFAULT BY THE EXTEND
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    //*********************   HELPERS   **********************



    /**
     *  CREATE COLUMN()
     * @param columnName
     * @param dataType
     */
    public void createColumn(String columnName,String dataType){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sqlQuery = "ALTER TABLE " + TABLENAME + " ADD COLUMN " + columnName + " " + dataType;
            db.execSQL(sqlQuery);
        }catch (Exception e){}
    }


    /**
     * LIST COLUMNS()
     * @return
     */
    public ArrayList<String> listColumns(){
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLENAME+" WHERE 0", null);
        try {
            for(String i:c.getColumnNames()){
                result.add(i);
            }
        } finally {
            c.close();
        }

        return result;
    }


    /**
     * DELETE COLUMN()
     *
     * @param columnName
     * @param helper
     */
    public void deleteColumn(String columnName,HelperFunctions helper) {

        if (columnName.equalsIgnoreCase("id")) {
            helper.makeToast("ID column should not be Deleted");
        } else {
            String tables = getTableString(columnName,helper);
            // String tables = getTableString(columnName,helper);
            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();
            db.execSQL(" CREATE TABLE backup AS SELECT "+tables+" FROM "+TABLENAME+" ;");
            db.execSQL("INSERT INTO backup SELECT "+tables+" FROM "+TABLENAME+";");
            db.execSQL("DROP TABLE "+TABLENAME+";");
            db.execSQL("CREATE TABLE "+TABLENAME+" ("+tables+");");
            db.execSQL("INSERT INTO "+TABLENAME+" SELECT "+tables+" FROM backup;");
            db.execSQL("DROP TABLE backup;");
            db.setTransactionSuccessful();
            db.endTransaction();
            helper.makeToast("Success");
        }


        }

    /**
     * GET TABLE STRING
     *
     * USED BY DELETE COLUMNS
     * @param columnName
     * @param helper
     * @return
     */
    public String getTableString(String columnName,HelperFunctions helper) {
        // Creting a String of columns separated by ','.
        String tables = "(";

            for (String i : listColumns()) {
                if (!i.equals(columnName)) {
                    tables += i;
                    tables += ",";
                } else {
                }
            }
        tables += ")";
        tables = tables.replace("(", "");
        tables = tables.replace(",)", "");

        return  tables;
    }

}

