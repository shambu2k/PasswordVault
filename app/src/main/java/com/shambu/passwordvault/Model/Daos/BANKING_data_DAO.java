package com.shambu.passwordvault.Model.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shambu.passwordvault.Model.Entities.BANKING_data;

import java.util.List;

@Dao
public interface BANKING_data_DAO {
    @Insert
    void insertBankdata(BANKING_data data);

    @Update
    void updateBankdata(BANKING_data data);

    @Delete
    void deleteBankdata(BANKING_data data);

    @Query("DELETE FROM banking_table")
    void deleteAllBankdata();

    @Query("SELECT * FROM banking_table")
    LiveData<List<BANKING_data>> getAllBankData();


}
