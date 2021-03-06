package com.shambu.passwordvault.Model.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "banking_table")
public class BANKING_data {

    @PrimaryKey(autoGenerate = true)
    private int BsqlID;

    private String BankName;
    private String Accountnum;
    private String AssoBankPhno;
    private String AssoBankmail;
    private String BankAddress;
    private String creditcardnum;
    private String debitcardnum;
    private String netBankinguserid;
    private String netBankingpass;
    private String adiNotes;

    public BANKING_data(){

    }

    public BANKING_data(int bsqlID, String bankName, String accountnum, String assoBankPhno, String assoBankmail,
                        String bankAddress, String creditcardnum, String debitcardnum, String netBankinguserid,
                        String netBankingpass, String adiNotes) {
        BsqlID = bsqlID;
        BankName = bankName;
        Accountnum = accountnum;
        AssoBankPhno = assoBankPhno;
        AssoBankmail = assoBankmail;
        BankAddress = bankAddress;
        this.creditcardnum = creditcardnum;
        this.debitcardnum = debitcardnum;
        this.netBankinguserid = netBankinguserid;
        this.netBankingpass = netBankingpass;
        this.adiNotes = adiNotes;
    }

    public BANKING_data(String bankName, String accountnum, String assoBankPhno, String assoBankmail,
                        String bankAddress, String creditcardnum, String debitcardnum, String netBankinguserid,
                        String netBankingpass, String adiNotes) {
        BankName = bankName;
        Accountnum = accountnum;
        AssoBankPhno = assoBankPhno;
        AssoBankmail = assoBankmail;
        BankAddress = bankAddress;
        this.creditcardnum = creditcardnum;
        this.debitcardnum = debitcardnum;
        this.netBankinguserid = netBankinguserid;
        this.netBankingpass = netBankingpass;
        this.adiNotes = adiNotes;
    }

    public int getBsqlID() {
        return BsqlID;
    }

    public void setBsqlID(int bsqlID) {
        BsqlID = bsqlID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getAccountnum() {
        return Accountnum;
    }

    public void setAccountnum(String accountnum) {
        Accountnum = accountnum;
    }

    public String getAssoBankPhno() {
        return AssoBankPhno;
    }

    public void setAssoBankPhno(String assoBankPhno) {
        AssoBankPhno = assoBankPhno;
    }

    public String getAssoBankmail() {
        return AssoBankmail;
    }

    public void setAssoBankmail(String assoBankmail) {
        AssoBankmail = assoBankmail;
    }

    public String getBankAddress() {
        return BankAddress;
    }

    public void setBankAddress(String bankAddress) {
        BankAddress = bankAddress;
    }

    public String getCreditcardnum() {
        return creditcardnum;
    }

    public void setCreditcardnum(String creditcardnum) {
        this.creditcardnum = creditcardnum;
    }

    public String getDebitcardnum() {
        return debitcardnum;
    }

    public void setDebitcardnum(String debitcardnum) {
        this.debitcardnum = debitcardnum;
    }

    public String getNetBankinguserid() {
        return netBankinguserid;
    }

    public void setNetBankinguserid(String netBankinguserid) {
        this.netBankinguserid = netBankinguserid;
    }

    public String getNetBankingpass() {
        return netBankingpass;
    }

    public void setNetBankingpass(String netBankingpass) {
        this.netBankingpass = netBankingpass;
    }

    public String getAdiNotes() {
        return adiNotes;
    }

    public void setAdiNotes(String adiNotes) {
        this.adiNotes = adiNotes;
    }
}
