package com.andronicus.journalapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by andronicus on 7/1/2018.
 */
@Entity(tableName = "entries")
public class Entry {

    public static final long NO_DATE = Long.MAX_VALUE;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private long mDate;

    public Entry(int id, String title, String description, long date) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mDate = date;
    }

    @Ignore
    public Entry(String title, String description, long date) {
        mTitle = title;
        mDescription = description;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
