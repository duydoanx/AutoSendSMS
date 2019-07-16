package com.duydoan.autosendsms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContentDataDao {
    @Insert(onConflict = REPLACE)
    public void insertData(ContentData contentData);
    @Update (onConflict = REPLACE)
    public void updateData(ContentData contentData);
    @Delete
    public void deleteData(ContentData contentData);
    @Query("delete from ContentData where id= :id")
    public void deleteDataById(int id);
    @Query("SELECT * FROM ContentData")
    public List<ContentData> loadAllData();
    @Query("SELECT * FROM ContentData")
    public LiveData<List<ContentData>> loadAllDataLive();
    @Query("DELETE From ContentData")
    public void deleteAll();
    @Query("Select * from ContentData where code = :code")
    public List<ContentData> findItem(String code);
}
