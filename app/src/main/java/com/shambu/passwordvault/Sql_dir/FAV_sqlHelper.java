package com.shambu.passwordvault.Sql_dir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.shambu.passwordvault.Data_classes.BANKING_data;
import com.shambu.passwordvault.Data_classes.DEVICE_data;
import com.shambu.passwordvault.Data_classes.FAV_data;
import com.shambu.passwordvault.Data_classes.GSMOWOM_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FAV_sqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fav";
    private static final String TABLE_NAME = "Favourites";
    private static final String COL_id = "sno";
    private static final String COL_dataType = "DataType";
    private static final String COL_data = "Data";
    private static String TYPE_GSMOWOM = "GSMOWOM";
    private static String TYPE_DEVICE = "DEVICE";
    private static String TYPE_BANKD = "BANKD";


    public FAV_sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+COL_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_dataType+" TEXT NOT NULL, "+
                COL_data+" TEXT NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertFAVGSMOWOMData(GSMOWOM_data data, SQLiteDatabase db){
        Gson gson = new Gson();
        String GSMOWOMjson = gson.toJson(data);
        ContentValues values = new ContentValues();
        values.put(COL_dataType, TYPE_GSMOWOM);
        values.put(COL_data, GSMOWOMjson);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertFAVDEVICEData(DEVICE_data data, SQLiteDatabase db){
        Gson gson = new Gson();
        String GSMOWOMjson = gson.toJson(data);
        ContentValues values = new ContentValues();
        values.put(COL_dataType, TYPE_DEVICE);
        values.put(COL_data, GSMOWOMjson);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertFAVBANKDETAILSData(BANKING_data data, SQLiteDatabase db){
        Gson gson = new Gson();
        String GSMOWOMjson = gson.toJson(data);
        ContentValues values = new ContentValues();
        values.put(COL_dataType, TYPE_BANKD);
        values.put(COL_data, GSMOWOMjson);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<FAV_data> getALLfavData(SQLiteDatabase db){
        List<FAV_data> listdata = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            FAV_data data  = new FAV_data();
            Gson gson = new Gson();
            if(cursor.getString(cursor.getColumnIndex(COL_dataType)).equals(TYPE_GSMOWOM)){
                data.setFAV_sqlID(cursor.getInt(cursor.getColumnIndex(COL_id)));
                data.setGsmowom_data(gson.fromJson(cursor.getString(cursor.getColumnIndex(COL_data)), GSMOWOM_data.class));
            }
            else if(cursor.getString(cursor.getColumnIndex(COL_dataType)).equals(TYPE_DEVICE)){
                data.setFAV_sqlID(cursor.getInt(cursor.getColumnIndex(COL_id)));
                data.setDevice_data(gson.fromJson(cursor.getString(cursor.getColumnIndex(COL_data)), DEVICE_data.class));
            }
            else if (cursor.getString(cursor.getColumnIndex(COL_dataType)).equals(TYPE_BANKD)){
                data.setFAV_sqlID(cursor.getInt(cursor.getColumnIndex(COL_id)));
                data.setBanking_data(gson.fromJson(cursor.getString(cursor.getColumnIndex(COL_data)), BANKING_data.class));
            }
            listdata.add(data);
        }
        cursor.close();
        db.close();
        return listdata;
    }

    public void removeFAV(int rowID, SQLiteDatabase db){
        db.delete(TABLE_NAME, COL_id+" = "+rowID, null);
        db.close();
    }

    public void deleteTABLE(SQLiteDatabase db){
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();
    }
}
