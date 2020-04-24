package com.shambu.passwordvault.Model.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;

import java.util.List;

@Dao
public interface GSMOWOM_data_DAO {

    @Insert
    void insertGSMOWOMData(GSMOWOM_data data);

    @Update
    void updateGSMOWOMData(GSMOWOM_data data);

    @Delete
    void deleteGSMOWOMData(GSMOWOM_data data);

    @Query("DELETE FROM gsmowom_table WHERE D_type BETWEEN :MailOtherSocial AND :MailOtherSocial")
    void deleteAllGSMOWOMData(int MailOtherSocial);

    @Query("SELECT * FROM gsmowom_table WHERE D_type = :typeofGSMOWOM")
    LiveData<List<GSMOWOM_data>> getAllGSMOWOMdata(int typeofGSMOWOM);

}
