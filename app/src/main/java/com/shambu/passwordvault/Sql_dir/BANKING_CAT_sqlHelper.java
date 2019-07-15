package com.shambu.passwordvault.Sql_dir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.shambu.passwordvault.Data_classes.BANKINGCAT_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BANKING_CAT_sqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BANKING_CAT";
    private static final String TABLE_NAME = "BANKING_CAT_table";
    private static final String COL_id = "sno";
    private static final String COL_Bname = "bankName";

    public BANKING_CAT_sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+COL_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_Bname+" TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertBankName(BANKINGCAT_data data, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_Bname, data.getBank_name());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updatebankName(BANKINGCAT_data data, SQLiteDatabase db, int rowID){
        ContentValues values = new ContentValues();
        values.put(COL_Bname, data.getBank_name());

        db.update(TABLE_NAME, values, COL_id+" = "+rowID, null);
        db.close();
    }

    public List<BANKINGCAT_data> getAllBnames(SQLiteDatabase db){
        String query = "SELECT * FROM "+TABLE_NAME;
        List<BANKINGCAT_data> list_data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            BANKINGCAT_data data = new BANKINGCAT_data(cursor.getInt(cursor.getColumnIndex(COL_id)), cursor.getString(cursor.getColumnIndex(COL_Bname)));
            list_data.add(data);
        }
        cursor.close();
        db.close();

        return list_data;
    }

    public void removebankRow(int rowID, SQLiteDatabase db, SQLiteDatabase dbdetails, String bname){
        db.delete(TABLE_NAME, COL_id+" = "+rowID, null);
        db.close();
        String sql = "DELETE FROM BANKING_table WHERE bankName LIKE '%"+bname+"%'";
        dbdetails.execSQL(sql);
        dbdetails.close();
    }
}
