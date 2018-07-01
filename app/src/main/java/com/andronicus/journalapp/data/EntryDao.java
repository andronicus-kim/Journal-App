package com.andronicus.journalapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by andronicus on 7/1/2018.
 */
@Dao
public interface EntryDao {

    @Query("SELECT * FROM entries")
    List<Entry> getAll();

    @Query("SELECT * FROM entries WHERE id=:id")
    Entry getOne(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Entry entry);

    @Update
    int update(Entry entry);
}
