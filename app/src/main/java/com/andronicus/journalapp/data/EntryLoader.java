package com.andronicus.journalapp.data;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by andronicus on 7/1/2018.
 */
public class EntryLoader extends AsyncTaskLoader<List<Entry>> {

    private EntryDao mDao;
    public EntryLoader(Context context,EntryDao entryDao) {
        super(context);
        mDao = entryDao;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Entry> loadInBackground() {
        return mDao.getAll();
    }

    @Override
    public void deliverResult(List<Entry> data) {
        super.deliverResult(data);
    }
}
