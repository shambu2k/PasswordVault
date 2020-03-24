package com.shambu.passwordvault.Model.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_table")
public class FAV_data {

    @PrimaryKey(autoGenerate = true)
    private int FAV_sqlID;

    private String gsmowom_data;
    private String device_data;
    private String banking_data;

    public FAV_data(String gsmowom_data, String device_data, String banking_data) {
        this.gsmowom_data = gsmowom_data;
        this.device_data = device_data;
        this.banking_data = banking_data;
    }

    public int getFAV_sqlID() {
        return FAV_sqlID;
    }

    public void setFAV_sqlID(int FAV_sqlID) {
        this.FAV_sqlID = FAV_sqlID;
    }

    public String getGsmowom_data() {
        return gsmowom_data;
    }

    public void setGsmowom_data(String gsmowom_data) {
        this.gsmowom_data = gsmowom_data;
    }

    public String getDevice_data() {
        return device_data;
    }

    public void setDevice_data(String device_data) {
        this.device_data = device_data;
    }

    public String getBanking_data() {
        return banking_data;
    }

    public void setBanking_data(String banking_data) {
        this.banking_data = banking_data;
    }
}
