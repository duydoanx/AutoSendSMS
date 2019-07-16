package com.duydoan.autosendsms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContentDataViewModel extends AndroidViewModel {

    private DataRepository repository;
    private LiveData<List<ContentData>> allData;

    public ContentDataViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allData = repository.getAllData();
    }

    public void insert(ContentData contentData){
        repository.insert(contentData);
    }

    public void update(ContentData contentData){
        repository.update(contentData);
    }
    public void detele(ContentData contentData){
        repository.delete(contentData);
    }
    public void deleteAllData(){
        repository.deleteAllData();
    }
    public List<ContentData> findItem(String code){
        return repository.findItem(code);
    }

    public LiveData<List<ContentData>> getAllData() {
        return allData;
    }
}
