package com.shambu.passwordvault.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.PassRepository;
import com.shambu.passwordvault.Views.MainActivity;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private PassRepository passRepository;
    private LiveData<List<FAV_data>> allFavdata;

    public FavViewModel(@NonNull Application application) {
        super(application);
        passRepository = new PassRepository(application, MainActivity.lepass);
        allFavdata = passRepository.getAllFavdata();
    }

    public void update(FAV_data data){
        passRepository.update(data);
    }

    public void delete(FAV_data data){
        passRepository.delete(data);
    }

    public void deleteAll(){
        passRepository.deleteAllFAVdata();
    }

    public LiveData<List<FAV_data>> getAllFavData(){
        return allFavdata;
    }
}
