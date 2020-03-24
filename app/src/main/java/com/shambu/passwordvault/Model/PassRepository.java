package com.shambu.passwordvault.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.shambu.passwordvault.Model.Daos.BANKING_data_DAO;
import com.shambu.passwordvault.Model.Daos.DEVICE_data_DAO;
import com.shambu.passwordvault.Model.Daos.FAV_data_DAO;
import com.shambu.passwordvault.Model.Daos.GSMOWOM_data_DAO;
import com.shambu.passwordvault.Model.Entities.BANKING_data;
import com.shambu.passwordvault.Model.Entities.DEVICE_data;
import com.shambu.passwordvault.Model.Entities.FAV_data;
import com.shambu.passwordvault.Model.Entities.GSMOWOM_data;

import java.security.PublicKey;
import java.util.List;

public class PassRepository {

    private BANKING_data_DAO banking_data_dao;
    private DEVICE_data_DAO device_data_dao;
    private GSMOWOM_data_DAO gsmowom_data_dao;
    private FAV_data_DAO fav_data_dao;

    private LiveData<List<BANKING_data>> allBankdata;
    private LiveData<List<DEVICE_data>> allDevices;
    private LiveData<List<GSMOWOM_data>> allGSMOWOM;
    private LiveData<List<FAV_data>> allFavdata;

    public PassRepository(Application application) {
        PassDatabase database = PassDatabase.getInstance(application);
        gsmowom_data_dao = database.gsmowom_data_dao();
        allGSMOWOM = gsmowom_data_dao.getAllGSMOWOMdata();
        banking_data_dao = database.banking_data_dao();
        allBankdata = banking_data_dao.getAllBankData();
        device_data_dao = database.device_data_dao();
        allDevices = device_data_dao.getAllDevices();
        fav_data_dao = database.fav_data_dao();
        allFavdata = fav_data_dao.getAllFavdata();
    }

    public void insert(BANKING_data data){
        new InsertBankDataAsyncTask(banking_data_dao).execute(data);
    }

    public void insert(DEVICE_data data){
        new InsertDeviceDataAsyncTask(device_data_dao).execute(data);
    }

    public void insert(GSMOWOM_data data){
        new InsertGSMOWOMDataAsyncTask(gsmowom_data_dao).execute(data);
    }

    public void insert(FAV_data data){
        new InsertFavDataAsyncTask(fav_data_dao).execute(data);
    }

    public void update(BANKING_data data){
        new UpdateBankDataAsyncTask(banking_data_dao).execute(data);
    }

    public void update(DEVICE_data data){
        new UpdateDeviceDataAsyncTask(device_data_dao).execute(data);
    }

    public void update(GSMOWOM_data data){
        new UpdateGSMOWOMDataAsyncTask(gsmowom_data_dao).execute(data);
    }

    public void update(FAV_data data){
        new UpdateFAVDataAsyncTask(fav_data_dao).execute(data);
    }

    public void delete(BANKING_data data){
        new DeleteBankDataAsyncTask(banking_data_dao).execute(data);
    }

    public void delete(DEVICE_data data){
        new DeleteDeviceDataAsyncTask(device_data_dao).execute(data);
    }

    public void delete(GSMOWOM_data data){
        new DeleteGSMOWOMDataAsyncTask(gsmowom_data_dao).execute(data);
    }

    public void delete(FAV_data data){
        new DeleteFAVDataAsyncTask(fav_data_dao).execute(data);
    }

    public void deleteAllBankdata(){
        new DeleteAllBankDataAsyncTask(banking_data_dao).execute();
    }

    public void deleteAllDevicedata(){
        new DeleteAllDeviceDataAsyncTask(device_data_dao).execute();
    }

    public void deleteAllGSMOWOMdata(Integer mailsocialother){
        new DeleteAllGSMOWOMDataAsyncTask(gsmowom_data_dao).execute();
    }

    public void deleteAllFAVdata(){
        new DeleteAllFAVDataAsyncTask(fav_data_dao).execute();
    }

    public LiveData<List<BANKING_data>> getAllBanks(){
        return allBankdata;
    }

    public LiveData<List<DEVICE_data>> getAllDevices(){
        return allDevices;
    }

    public LiveData<List<GSMOWOM_data>> getAllGSMOWOMdata(){
        return allGSMOWOM;
    }

    public LiveData<List<FAV_data>> getAllFavdata(){
        return allFavdata;
    }

    private static class InsertBankDataAsyncTask extends AsyncTask<BANKING_data, Void, Void>{

        private BANKING_data_DAO dao;

        public InsertBankDataAsyncTask(BANKING_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BANKING_data... banking_data) {
            dao.insertBankdata(banking_data[0]);
            return null;
        }
    }

    private static class InsertDeviceDataAsyncTask extends AsyncTask<DEVICE_data, Void, Void>{

        private DEVICE_data_DAO dao;

        public InsertDeviceDataAsyncTask(DEVICE_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DEVICE_data... device_data) {
            dao.insertDevice(device_data[0]);
            return null;
        }
    }

    private static class InsertGSMOWOMDataAsyncTask extends AsyncTask<GSMOWOM_data, Void, Void>{

        private GSMOWOM_data_DAO dao;

        public InsertGSMOWOMDataAsyncTask(GSMOWOM_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GSMOWOM_data... gsmowom_data) {
            dao.insertGSMOWOMData(gsmowom_data[0]);
            return null;
        }
    }

    private static class InsertFavDataAsyncTask extends AsyncTask<FAV_data, Void, Void>{

        private FAV_data_DAO dao;

        public InsertFavDataAsyncTask(FAV_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FAV_data... fav_data) {
            dao.insertFavData(fav_data[0]);
            return null;
        }
    }

    private static class UpdateBankDataAsyncTask extends AsyncTask<BANKING_data, Void, Void>{

        private BANKING_data_DAO dao;

        public UpdateBankDataAsyncTask(BANKING_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BANKING_data... banking_data) {
            dao.updateBankdata(banking_data[0]);
            return null;
        }
    }

    private static class UpdateDeviceDataAsyncTask extends AsyncTask<DEVICE_data, Void, Void>{

        private DEVICE_data_DAO dao;

        public UpdateDeviceDataAsyncTask(DEVICE_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DEVICE_data... device_data) {
            dao.updateDevice(device_data[0]);
            return null;
        }
    }

    private static class UpdateGSMOWOMDataAsyncTask extends AsyncTask<GSMOWOM_data, Void, Void>{

        private GSMOWOM_data_DAO dao;

        public UpdateGSMOWOMDataAsyncTask(GSMOWOM_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GSMOWOM_data... gsmowom_data) {
            dao.updateGSMOWOMData(gsmowom_data[0]);
            return null;
        }
    }

    private static class UpdateFAVDataAsyncTask extends AsyncTask<FAV_data, Void, Void>{

        private FAV_data_DAO dao;

        public UpdateFAVDataAsyncTask(FAV_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FAV_data... fav_data) {
            dao.updateFavData(fav_data[0]);
            return null;
        }
    }

    private static class DeleteBankDataAsyncTask extends AsyncTask<BANKING_data, Void, Void>{

        private BANKING_data_DAO dao;

        public DeleteBankDataAsyncTask(BANKING_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BANKING_data... banking_data) {
            dao.deleteBankdata(banking_data[0]);
            return null;
        }
    }

    private static class DeleteDeviceDataAsyncTask extends AsyncTask<DEVICE_data, Void, Void>{

        private DEVICE_data_DAO dao;

        public DeleteDeviceDataAsyncTask(DEVICE_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DEVICE_data... device_data) {
            dao.deleteDevice(device_data[0]);
            return null;
        }
    }

    private static class DeleteGSMOWOMDataAsyncTask extends AsyncTask<GSMOWOM_data, Void, Void>{

        private GSMOWOM_data_DAO dao;

        public DeleteGSMOWOMDataAsyncTask(GSMOWOM_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(GSMOWOM_data... gsmowom_data) {
            dao.deleteGSMOWOMData(gsmowom_data[0]);
            return null;
        }
    }

    private static class DeleteFAVDataAsyncTask extends AsyncTask<FAV_data, Void, Void>{

        private FAV_data_DAO dao;

        public DeleteFAVDataAsyncTask(FAV_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FAV_data... fav_data) {
            dao.deleteFavData(fav_data[0]);
            return null;
        }
    }

    private static class DeleteAllBankDataAsyncTask extends AsyncTask<Void, Void, Void>{

        private BANKING_data_DAO dao;

        public DeleteAllBankDataAsyncTask(BANKING_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllBankdata();
            return null;
        }
    }

    private static class DeleteAllDeviceDataAsyncTask extends AsyncTask<Void, Void, Void>{

        private DEVICE_data_DAO dao;

        public DeleteAllDeviceDataAsyncTask(DEVICE_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllDevices();
            return null;
        }
    }

    private static class DeleteAllGSMOWOMDataAsyncTask extends AsyncTask<Integer, Void, Void>{

        private GSMOWOM_data_DAO dao;

        public DeleteAllGSMOWOMDataAsyncTask(GSMOWOM_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteAllGSMOWOMData(integers[0]);
            return null;
        }
    }

    private static class DeleteAllFAVDataAsyncTask extends AsyncTask<Void, Void, Void>{

        private FAV_data_DAO dao;

        public DeleteAllFAVDataAsyncTask(FAV_data_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllFavData();
            return null;
        }
    }


}
