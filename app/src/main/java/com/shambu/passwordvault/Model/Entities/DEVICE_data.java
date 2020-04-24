package com.shambu.passwordvault.Model.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "device_table")
public class DEVICE_data {

    @PrimaryKey(autoGenerate = true)
    private int sqldeviceID;

    private String data_type;
    private String device_Type;
    private String device_name;
    private String PINorPassorPattern;
    private String securityType;

    public DEVICE_data(){

    }

    public DEVICE_data(int sqldeviceID, String data_type, String device_Type, String device_name,
                       String PINorPassorPattern, String securityType) {
        this.sqldeviceID = sqldeviceID;
        this.data_type = data_type;
        this.device_Type = device_Type;
        this.device_name = device_name;
        this.PINorPassorPattern = PINorPassorPattern;
        this.securityType = securityType;
    }

    public DEVICE_data(String data_type, String device_Type, String device_name,
                       String PINorPassorPattern, String securityType) {
        this.data_type = data_type;
        this.device_Type = device_Type;
        this.device_name = device_name;
        this.PINorPassorPattern = PINorPassorPattern;
        this.securityType = securityType;
    }

    public int getSqldeviceID() {
        return sqldeviceID;
    }

    public void setSqldeviceID(int sqldeviceID) {
        this.sqldeviceID = sqldeviceID;
    }

    public String getPINorPassorPattern() {
        return PINorPassorPattern;
    }

    public void setPINorPassorPattern(String PINorPassorPattern) {
        this.PINorPassorPattern = PINorPassorPattern;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getDevice_Type() {
        return device_Type;
    }

    public void setDevice_Type(String device_Type) {
        this.device_Type = device_Type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

}
