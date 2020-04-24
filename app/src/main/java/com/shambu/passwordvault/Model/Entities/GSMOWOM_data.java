package com.shambu.passwordvault.Model.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gsmowom_table")
public class GSMOWOM_data {

    @PrimaryKey(autoGenerate = true)
    private int d_ID;

    private int D_type;
    private String D_provider;
    private String D_assoEmail;
    private String D_assoPhno;
    private String D_username;
    private String D_pass;
    private String D_adiInfo;

    public GSMOWOM_data(){

    }

    public GSMOWOM_data(int d_ID, int d_type, String d_provider, String d_assoEmail, String d_assoPhno,
                        String d_username, String d_pass, String d_adiInfo) {
        this.d_ID = d_ID;
        D_type = d_type;
        D_provider = d_provider;
        D_assoEmail = d_assoEmail;
        D_assoPhno = d_assoPhno;
        D_username = d_username;
        D_pass = d_pass;
        D_adiInfo = d_adiInfo;
    }

    public GSMOWOM_data(int d_type, String d_provider, String d_assoEmail, String d_assoPhno,
                        String d_username, String d_pass, String d_adiInfo) {
        D_type = d_type;
        D_provider = d_provider;
        D_assoEmail = d_assoEmail;
        D_assoPhno = d_assoPhno;
        D_username = d_username;
        D_pass = d_pass;
        D_adiInfo = d_adiInfo;
    }

    public int getD_ID() {
        return d_ID;
    }

    public void setD_ID(int d_ID) {
        this.d_ID = d_ID;
    }

    public int getD_type() {
        return D_type;
    }

    public void setD_type(int d_type) {
        D_type = d_type;
    }

    public String getD_provider() {
        return D_provider;
    }

    public void setD_provider(String d_provider) {
        D_provider = d_provider;
    }

    public String getD_assoEmail() {
        return D_assoEmail;
    }

    public void setD_assoEmail(String d_assoEmail) {
        D_assoEmail = d_assoEmail;
    }

    public String getD_assoPhno() {
        return D_assoPhno;
    }

    public void setD_assoPhno(String d_assoPhno) {
        D_assoPhno = d_assoPhno;
    }

    public String getD_username() {
        return D_username;
    }

    public void setD_username(String d_username) {
        D_username = d_username;
    }

    public String getD_pass() {
        return D_pass;
    }

    public void setD_pass(String d_pass) {
        D_pass = d_pass;
    }

    public String getD_adiInfo() {
        return D_adiInfo;
    }

    public void setD_adiInfo(String d_adiInfo) {
        D_adiInfo = d_adiInfo;
    }
}
