package com.shambu.passwordvault.Sql_dir;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class password_text_sql extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pass";
    private static final String TABLE_NAME = "passchecker";
    private static final String COL_id = "num";

    public password_text_sql(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "+COL_id+" INTEGER PRIMARY KEY AUTOINCREMENT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void tester(SQLiteDatabase db){
        db.delete(TABLE_NAME, COL_id+" = "+5, null);
        db.close();
    }
}
