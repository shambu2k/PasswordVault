package com.shambu.passwordvault.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.shambu.passwordvault.Model.Entities.DEVICE_data;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.PassRepository;

import java.util.List;

public class DevicesViewModel extends AndroidViewModel {

    private PassRepository passRepository;
    private LiveData<List<DEVICE_data>> allDevices;

    public DevicesViewModel(@NonNull Application application) {
        super(application);
        passRepository = new PassRepository(application);
        allDevices = passRepository.getAllDevices();
    }

    public void insert(DEVICE_data data){
        passRepository.insert(data);
    }

    public void update(DEVICE_data data){
        passRepository.update(data);
    }

    public void delete(DEVICE_data data){
        passRepository.delete(data);
    }

    public void addtoFav(DEVICE_data data){
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        passRepository.insert(new FAV_data(null, jsonData, null));
    }

    public void deleteAll(){
        passRepository.deleteAllDevicedata();
    }

    public LiveData<List<DEVICE_data>> getAllDevices(){
        return allDevices;
    }
}
