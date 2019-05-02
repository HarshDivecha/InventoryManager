package com.three_amigos.inventorymanager.globals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    final String ID="ID";
    final String NAME="name";
    final String PRICE="price";
    final Context CONTEXT;
    static final String DATABASE_NAME="Inventory.db";
    static final String TABLENAME="kitchen_inventory";
    static final int DATABASE_VERSION = 1;


    /**
     * INITIATES:
     *
     * @param context
     */
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.CONTEXT = context;
    }

    /**
     * IMPLEMENTED BY DEFAULT BY THE EXTEND
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, name,price)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    //*********************   HELPERS   **********************


    public boolean addData(String name,String price){
        name = name.trim().toLowerCase().replace(" ","_");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(PRICE,price);

        long result = db.insert(TABLENAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }



    public ArrayList<String> getListContents() {

        ArrayList<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLENAME, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(CONTEXT, "Empty Database", Toast.LENGTH_LONG);
        } else {
            while (cursor.moveToNext()) {
                String r = cursor.getString(0)+"    ";
                r+= cursor.getString(1)+"    ";
                r+=cursor.getString(2);
                result.add(r);
            }
        }

        return result;
    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLENAME,ID+"="+id,null);

        String autoincrQuery = "delete from sqlite_sequence where name='"+TABLENAME+"';";
        db.execSQL(autoincrQuery);
    }

    public Boolean checkDuplicate(String item){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLENAME+" WHERE "+NAME+" = '"+item+"' ",null);

        if (cursor!=null) {
            while (cursor.moveToNext()) {
                String recorded_lastitem = cursor.getString(cursor.getColumnIndex(NAME));
                if (recorded_lastitem.equals(item)) {
                    return true;
                }
            }
        }
        return false;
    }

}

