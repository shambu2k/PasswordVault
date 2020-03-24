package com.shambu.passwordvault.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shambu.passwordvault.Views.MainActivity;
import com.shambu.passwordvault.Model.Daos.BANKING_data_DAO;
import com.shambu.passwordvault.Model.Daos.DEVICE_data_DAO;
import com.shambu.passwordvault.Model.Daos.FAV_data_DAO;
import com.shambu.passwordvault.Model.Daos.GSMOWOM_data_DAO;
import com.shambu.passwordvault.Model.Entities.BANKING_data;
import com.shambu.passwordvault.Model.Entities.DEVICE_data;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

@Database(entities = {BANKING_data.class, DEVICE_data.class, FAV_data.class, GSMOWOM_data.class},
        version = 1)
public abstract class PassDatabase extends RoomDatabase {

    private static PassDatabase instance;

    public abstract BANKING_data_DAO banking_data_dao();
    public abstract DEVICE_data_DAO device_data_dao();
    public abstract GSMOWOM_data_DAO gsmowom_data_dao();
    public abstract FAV_data_DAO fav_data_dao();

    public static synchronized PassDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PassDatabase.class, "pass_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

