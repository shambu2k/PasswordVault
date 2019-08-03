package com.shambu.passwordvault.Sql_dir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.shambu.passwordvault.Data_classes.BANKING_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BANKING_sqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BANKING";
    private static final String TABLE_NAME = "BANKING_table";
    private static final String COL_id = "sno";
    private static final String COL_Bname = "bankName";
    private static final String COL_Accno = "Accno";
    private static final String COL_Assobphno = "Assobphno";
    private static final String COL_Assobmail = "Assobmail";
    private static final String COL_Baddr = "Baddr";
    private static final String COL_Ccnum = "ccnum";
    private static final String COL_Dcnum = "dcnum";
    private static final String COL_netBuid = "netBuid";
    private static final String COL_netBpass = "netBpass";
    private static final String COL_adiNotes = "adiNotes";

    private String msg = BANKING_sqlHelper.class.getSimpleName();


    public BANKING_sqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_Bname + " TEXT NOT NULL, " +
                COL_Accno + " TEXT NOT NULL, " + COL_Assobphno + " TEXT NOT NULL, " + COL_Assobmail + " TEXT NOT NULL, " + COL_Baddr + " TEXT NOT NULL, " +
                COL_Ccnum + " TEXT NOT NULL, " + COL_Dcnum + " TEXT NOT NULL, " + COL_netBuid + " TEXT NOT NULL, " + COL_netBpass + " TEXT NOT NULL, " +
                COL_adiNotes + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertBankDetails(BANKING_data data, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COL_Bname, data.getBankName());
        values.put(COL_Accno, data.getAccountnum());
        values.put(COL_Assobphno, data.getAssoBankPhno());
        values.put(COL_Assobmail, data.getAssoBankmail());
        values.put(COL_Baddr, data.getBankAddress());
        values.put(COL_Ccnum, data.getCreditcardnum());
        values.put(COL_Dcnum, data.getDebitcardnum());
        values.put(COL_netBuid, data.getNetBankinguserid());
        values.put(COL_netBpass, data.getNetBankingpass());
        values.put(COL_adiNotes, data.getAdiNotes());

        db.insert(TABLE_NAME, null, values);

        db.close();

        Log.d(msg, "Inserted!");

    }

    public List<BANKING_data> getAllAccountsofBank(String bankname, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_Bname + " LIKE '%" + bankname + "%'";
        List<BANKING_data> listdata = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            BANKING_data data = new BANKING_data(cursor.getInt(cursor.getColumnIndex(COL_id)), cursor.getString(cursor.getColumnIndex(COL_Bname)), cursor.getString(cursor.getColumnIndex(COL_Accno)),
                    cursor.getString(cursor.getColumnIndex(COL_Assobphno)), cursor.getString(cursor.getColumnIndex(COL_Assobmail)),
                    cursor.getString(cursor.getColumnIndex(COL_Baddr)), cursor.getString(cursor.getColumnIndex(COL_Ccnum)),
                    cursor.getString(cursor.getColumnIndex(COL_Dcnum)), cursor.getString(cursor.getColumnIndex(COL_netBuid)),
                    cursor.getString(cursor.getColumnIndex(COL_netBpass)), cursor.getString(cursor.getColumnIndex(COL_adiNotes)));

            listdata.add(data);
        }
        cursor.close();
        db.close();

        return listdata;
    }

    public void updateBankDetails(BANKING_data data, int rowID, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_Bname, data.getBankName());
        values.put(COL_Accno, data.getAccountnum());
        values.put(COL_Assobphno, data.getAssoBankPhno());
        values.put(COL_Assobmail, data.getAssoBankmail());
        values.put(COL_Baddr, data.getBankAddress());
        values.put(COL_Ccnum, data.getCreditcardnum());
        values.put(COL_Dcnum, data.getDebitcardnum());
        values.put(COL_netBuid, data.getNetBankinguserid());
        values.put(COL_netBpass, data.getNetBankingpass());
        values.put(COL_adiNotes, data.getAdiNotes());

        db.update(TABLE_NAME, values, COL_id+" = "+rowID, null);
        db.close();
        Log.d(msg, "Updated!!");
    }

    public void bankNameupdate(String name, String oldBname, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COL_Bname, name);

        String sql = "UPDATE "+TABLE_NAME+" SET " +COL_Bname+ " = '"+name+"' WHERE "+COL_Bname+" LIKE '%"+oldBname+"%'";
        db.execSQL(sql);
        db.close();
        Log.d(msg, oldBname+" changed to "+name);
    }

    public void deleteAccountDetails(int rowID, SQLiteDatabase db){
        db.delete(TABLE_NAME, COL_id+" = "+rowID, null);
        db.close();
    }

    public void deleteTABLE(SQLiteDatabase db){
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();
    }

}
