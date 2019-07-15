package com.shambu.passwordvault.Data_classes;

public class BANKINGCAT_data {
    private int bcSqlID;
    private String bank_name;

    public BANKINGCAT_data(){

    }

    public BANKINGCAT_data(int bcSqlID, String bank_name) {
        this.bcSqlID = bcSqlID;
        this.bank_name = bank_name;
    }

    public int getBcSqlID() {
        return bcSqlID;
    }

    public void setBcSqlID(int bcSqlID) {
        this.bcSqlID = bcSqlID;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
