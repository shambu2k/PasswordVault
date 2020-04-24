package com.shambu.passwordvault.Model.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shambu.passwordvault.Model.Entities.FAV_data;

import java.util.List;

@Dao
public interface FAV_data_DAO {
    @Insert
    void insertFavData(FAV_data data);

    @Update
    void updateFavData(FAV_data data);

    @Delete
    void deleteFavData(FAV_data data);

    @Query("DELETE FROM fav_table")
    void deleteAllFavData();

    @Query("SELECT * FROM fav_table")
    LiveData<List<FAV_data>> getAllFavdata();
}
