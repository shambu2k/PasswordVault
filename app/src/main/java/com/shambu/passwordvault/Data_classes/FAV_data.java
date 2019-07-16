package com.shambu.passwordvault.Data_classes;

public class FAV_data {
    private int FAV_sqlID;
    private GSMOWOM_data gsmowom_data;
    private DEVICE_data device_data;
    private BANKING_data banking_data;

    public int getFAV_sqlID() {
        return FAV_sqlID;
    }

    public void setFAV_sqlID(int FAV_sqlID) {
        this.FAV_sqlID = FAV_sqlID;
    }

    public GSMOWOM_data getGsmowom_data() {
        return gsmowom_data;
    }

    public void setGsmowom_data(GSMOWOM_data gsmowom_data) {
        this.gsmowom_data = gsmowom_data;
    }

    public DEVICE_data getDevice_data() {
        return device_data;
    }

    public void setDevice_data(DEVICE_data device_data) {
        this.device_data = device_data;
    }

    public BANKING_data getBanking_data() {
        return banking_data;
    }

    public void setBanking_data(BANKING_data banking_data) {
        this.banking_data = banking_data;
    }
}
