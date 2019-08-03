package com.shambu.passwordvault.Sql_dir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.shambu.passwordvault.Data_classes.GSMOWOM_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class GSMOWOM_sqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GSMOWOM";
    private static final String TABLE_NAME = "GSMOWOM_table";
    private static final String COL_id = "sno";
    private static final String COL_type = "type";
    private static final String COL_provider = "provider";
    private static final String COL_assoEmail = "assoEmail";
    private static final String COL_assoPhno = "assoPhno";
    private static final String COL_username = "username";
    private static final String COL_pass = "pass";
    private static final String COL_adiInfo = "adiInfo";

    private String msg = GSMOWOM_sqlHelper.class.getSimpleName();

    public GSMOWOM_sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table "+TABLE_NAME+" ( "+COL_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_type+" TEXT NOT NULL, "+COL_provider+" TEXT NOT NULL, "+COL_assoEmail+" TEXT NOT NULL, "+COL_assoPhno+" TEXT NOT NULL, "+COL_username+" TEXT NOT NULL, "+COL_pass+" TEXT NOT NULL, "+COL_adiInfo+" TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(GSMOWOM_data data, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_type, data.getD_type());
        values.put(COL_provider, data.getD_provider());
        values.put(COL_assoEmail, data.getD_assoEmail());
        values.put(COL_assoPhno, data.getD_assoPhno());
        values.put(COL_username, data.getD_username());
        values.put(COL_pass, data.getD_pass());
        values.put(COL_adiInfo, data.getD_adiInfo());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(msg, "Inserted!!!");
    }

    public List<GSMOWOM_data> getData(String Get_type, String Get_provider, SQLiteDatabase db){
        String query = "";
        if(Get_provider.equals("Others") && Get_type.equals("Social")){
            query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_type+" LIKE '%"+Get_type+"%' AND "+COL_provider+" NOT LIKE '%Facebook%' AND "+COL_provider+" NOT LIKE '%Twitter%' AND "+COL_provider+" NOT LIKE '%Instagram%' AND "+COL_provider+" NOT LIKE '%Reddit%' AND "+COL_provider+" NOT LIKE '%LinkedIn%' AND "+COL_provider+" NOT LIKE '%Tubmlr%' AND "+COL_provider+" NOT LIKE '%Pinterest%' AND "+COL_provider+" NOT LIKE '%Medium%' AND "+COL_provider+" NOT LIKE '%Tinder%'";
        }
        else if(!Get_provider.equals("Others") && Get_type.equals("Social")){
            query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_type+" LIKE '%"+Get_type+"%' AND "+COL_provider+" LIKE '%"+Get_provider+"%'";
        }
        else if(Get_type.equals("Google") || Get_type.equals("OtherMails") || Get_type.equals("OtherWebsites")){
            query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_type+" LIKE '%"+Get_type+"%'";
        }
        List<GSMOWOM_data> list_data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            GSMOWOM_data data = new GSMOWOM_data(cursor.getInt(cursor.getColumnIndex(COL_id)), cursor.getString(cursor.getColumnIndex(COL_type)), cursor.getString(cursor.getColumnIndex(COL_provider)),
                    cursor.getString(cursor.getColumnIndex(COL_assoEmail)), cursor.getString(cursor.getColumnIndex(COL_assoPhno)), cursor.getString(cursor.getColumnIndex(COL_username)),
                    cursor.getString(cursor.getColumnIndex(COL_pass)), cursor.getString(cursor.getColumnIndex(COL_adiInfo)));
            list_data.add(data);
        }
        cursor.close();
        db.close();
        Log.d(msg, "got DATA");
        return list_data;
    }

    public void removeRow(int rowID, SQLiteDatabase db){
        db.delete(TABLE_NAME, COL_id+" = "+rowID, null);
        db.close();
    }

    public void updateRow(GSMOWOM_data data, int rowID, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_type, data.getD_type());
        values.put(COL_provider, data.getD_provider());
        values.put(COL_assoEmail, data.getD_assoEmail());
        values.put(COL_assoPhno, data.getD_assoPhno());
        values.put(COL_username, data.getD_username());
        values.put(COL_pass, data.getD_pass());
        values.put(COL_adiInfo, data.getD_adiInfo());

        db.update(TABLE_NAME, values, COL_id+" = "+rowID, null);
        db.close();
        Log.d(msg, "Updated!!");
    }

    public void deleteTABLE(SQLiteDatabase db){
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();
    }
}
