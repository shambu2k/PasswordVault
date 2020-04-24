package com.shambu.passwordvault.Model.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shambu.passwordvault.Model.Entities.DEVICE_data;

import java.util.List;

@Dao
public interface DEVICE_data_DAO {
    @Insert
    void insertDevice(DEVICE_data data);

    @Update
    void updateDevice(DEVICE_data data);

    @Delete
    void deleteDevice(DEVICE_data data);

    @Query("DELETE FROM device_table")
    void deleteAllDevices();

    @Query("SELECT * FROM device_table")
    LiveData<List<DEVICE_data>> getAllDevices();
}
