package com.shambu.passwordvault.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;
import com.shambu.passwordvault.Model.PassRepository;
import com.shambu.passwordvault.Views.DatabasePasswordActivity;
import com.shambu.passwordvault.Views.MainActivity;

import java.util.List;

public class GSMOWOMViewModel extends AndroidViewModel {

    private PassRepository passRepository;

    public GSMOWOMViewModel(@NonNull Application application) {
        super(application);
        passRepository = new PassRepository(application, DatabasePasswordActivity.lepass);
    }

    public void insert(GSMOWOM_data data){
        passRepository.insert(data);
    }

    public void update(GSMOWOM_data data){
        passRepository.update(data);
    }

    public void delete(GSMOWOM_data data){
        passRepository.delete(data);
    }

    public void addtoFav(GSMOWOM_data data){
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        passRepository.insert(new FAV_data(jsonData, null, null));
    }

    public void deleteAll(Integer type){
        passRepository.deleteAllGSMOWOMdata(type);
    }

    public LiveData<List<GSMOWOM_data>> getAllGSMOWOMData(int dtype){
        return passRepository.getAllGSMOWOMdata(dtype);
    }
}
