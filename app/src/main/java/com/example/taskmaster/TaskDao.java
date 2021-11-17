package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// ----------------- this is interface to set and get the data you can use delete and update methods
@Dao
public interface TaskDao {

    // -------------- we got the data from this method by using get all ------------
    @Query("SELECT * FROM details")
    List<Details> getAll();


    // ------------ no need it to use -----------
//    @Query("SELECT * FROM details WHERE uid = :id")
//    List<Details> loadAllByIds(int[] id);


// ---------------- we insert all data from here methods --------------
    @Insert
    void insertAll(Details... details);

}
