package com.duydoan.autosendsms;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private ContentDataDao dataDao;
    private LiveData<List<ContentData>> allData;

    public DataRepository(Application application){
        Database database = Database.getInstance(application);
        dataDao = database.contentDataDao();
        allData = dataDao.loadAllDataLive();
    }
    public void insert(ContentData contentData){
        new InsertDataAsyncTask(dataDao).execute(contentData);
    }
    public void delete(ContentData contentData){
        new DeleteDataAsyncTask(dataDao).execute(contentData);
    }
    public void update(ContentData contentData){
        new UpdateDataAsyncTask(dataDao).execute(contentData);
    }
    public void deleteAllData(){
        new DeleteAllDataAsyncTask(dataDao).execute();
    }
    public List<ContentData> findItem(String code){
        return dataDao.findItem(code);
    }

    public LiveData<List<ContentData>> getAllData() {
        return allData;
    }
    private static class  InsertDataAsyncTask extends AsyncTask<ContentData, Void, Void>{

        private ContentDataDao dataDao;
        private InsertDataAsyncTask(ContentDataDao contentDataDao){
            this.dataDao = contentDataDao;
        }
        @Override
        protected Void doInBackground(ContentData... contentData) {
            dataDao.insertData(contentData[0]);
            return null;
        }
    }
    private static class  UpdateDataAsyncTask extends AsyncTask<ContentData, Void, Void>{

        private ContentDataDao dataDao;
        private UpdateDataAsyncTask(ContentDataDao contentDataDao){
            this.dataDao = contentDataDao;
        }
        @Override
        protected Void doInBackground(ContentData... contentData) {
            dataDao.updateData(contentData[0]);
            return null;
        }
    }
    private static class  DeleteDataAsyncTask extends AsyncTask<ContentData, Void, Void>{

        private ContentDataDao dataDao;
        private DeleteDataAsyncTask(ContentDataDao contentDataDao){
            this.dataDao = contentDataDao;
        }
        @Override
        protected Void doInBackground(ContentData... contentData) {
            dataDao.deleteData(contentData[0]);
            return null;
        }
    }
    private static class DeleteAllDataAsyncTask extends AsyncTask<Void, Void, Void>{

        private ContentDataDao dataDao;
        private DeleteAllDataAsyncTask(ContentDataDao contentDataDao){
            this.dataDao = contentDataDao;
        }
        @Override
        protected Void doInBackground(Void... contentData) {
            dataDao.deleteAll();
            return null;
        }
    }
    private static class FindItemAsyncTask extends AsyncTask<String, Void, List<ContentData>>{
        private ContentDataDao dataDao;
        private FindItemAsyncTask(ContentDataDao contentDataDao){
            this.dataDao = contentDataDao;
        }

        @Override
        protected List<ContentData> doInBackground(String... strings) {
            return dataDao.findItem(strings[0]);
        }

        @Override
        protected void onPostExecute(List<ContentData> contentData) {
            super.onPostExecute(contentData);
        }
    }
}
