package com.shambu.passwordvault.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.shambu.passwordvault.Model.Entities.BANKING_data;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.PassRepository;
import com.shambu.passwordvault.Views.MainActivity;

import java.util.List;

public class BankingViewModel extends AndroidViewModel {

    private PassRepository passRepository;
    private LiveData<List<BANKING_data>> allBankData;

    public BankingViewModel(@NonNull Application application) {
        super(application);
        passRepository = new PassRepository(application, MainActivity.lepass);
        allBankData = passRepository.getAllBanks();
    }

    public void insert(BANKING_data data){
        passRepository.insert(data);
    }

    public void update(BANKING_data data){
        passRepository.update(data);
    }

    public void delete(BANKING_data data){
        passRepository.delete(data);
    }

    public void addtoFav(BANKING_data data){
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        passRepository.insert(new FAV_data(null, null, jsonData));
    }

    public void deleteAll(){
        passRepository.deleteAllBankdata();
    }

    public LiveData<List<BANKING_data>> getAllBankData(){
        return allBankData;
    }
}
