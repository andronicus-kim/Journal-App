package com.andronicus.journalapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by andronicus on 7/1/2018.
 */
@Database(entities = {Entry.class},exportSchema = false,version = 1)
public abstract class JournalAppDatabase extends RoomDatabase{

    public abstract EntryDao mEntryDao();
}
