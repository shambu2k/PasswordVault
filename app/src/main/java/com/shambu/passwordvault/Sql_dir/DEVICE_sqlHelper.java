package com.shambu.passwordvault.Sql_dir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.shambu.passwordvault.Data_classes.DEVICE_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DEVICE_sqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DEVICE";
    private static final String TABLE_NAME = "DEVICE_table";
    private static final String COL_id = "sno";
    private static final String COL_type = "type";
    private static final String COL_devicetype = "devicetype";
    private static final String COL_name = "name";
    private static final String COL_securitytype = "securitytype";
    private static final String COL_pinorpassorpattern = "pinorpassorpattern";

    private String msg = DEVICE_sqlHelper.class.getSimpleName();


    public DEVICE_sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table "+TABLE_NAME+" ( "+COL_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_type+" TEXT NOT NULL, "+COL_devicetype+" TEXT NOT NULL, "+COL_name+" TEXT NOT NULL, "+COL_securitytype+" TEXT NOT NULL, "+COL_pinorpassorpattern+" TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertDEVICEdata(DEVICE_data data, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_type, data.getData_type());
        values.put(COL_devicetype, data.getDevice_Type());
        values.put(COL_name, data.getDevice_name());
        values.put(COL_securitytype, data.getSecurityType());
        values.put(COL_pinorpassorpattern, data.getPINorPassorPattern());

        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(msg, "Inserted DEVICE DATA!!!");
    }

    public List<DEVICE_data> getallDEVICEdata(SQLiteDatabase db){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_type+" LIKE '%Device%'";
        List<DEVICE_data> list_data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            DEVICE_data data = new DEVICE_data(cursor.getInt(cursor.getColumnIndex(COL_id)), cursor.getString(cursor.getColumnIndex(COL_type)), cursor.getString(cursor.getColumnIndex(COL_devicetype)),
                    cursor.getString(cursor.getColumnIndex(COL_name)), cursor.getString(cursor.getColumnIndex(COL_pinorpassorpattern)), cursor.getString(cursor.getColumnIndex(COL_securitytype)));
            list_data.add(data);
        }
        cursor.close();
        db.close();

        return list_data;
    }

    public void DEVICEremoveRow(int rowID, SQLiteDatabase db){
        db.delete(TABLE_NAME, COL_id+" = "+rowID, null);
        db.close();
    }

    public void DEVICEupdateRow(DEVICE_data data, int rowID, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_type, data.getData_type());
        values.put(COL_devicetype, data.getDevice_Type());
        values.put(COL_name, data.getDevice_name());
        values.put(COL_securitytype, data.getSecurityType());
        values.put(COL_pinorpassorpattern, data.getPINorPassorPattern());

        db.update(TABLE_NAME, values, COL_id+" = "+rowID, null);
        db.close();
        Log.d(msg, "Updated!!");
    }

    public void deleteTABLE(SQLiteDatabase db){
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();
    }

}
