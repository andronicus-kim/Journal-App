package com.andronicus.journalapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.andronicus.journalapp.data.EntryDao;
import com.andronicus.journalapp.data.JournalAppDatabase;

/**
 * Created by andronicus on 7/1/2018.
 */
public class JournalApp extends Application {

    private static JournalAppDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = Room.databaseBuilder(this,
                JournalAppDatabase.class,"JournalAppDatabase").build();
    }
    public static EntryDao getEntryDao(){
        return mDatabase.mEntryDao();
    }
}
