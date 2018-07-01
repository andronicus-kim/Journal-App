package com.andronicus.journalapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andronicus.journalapp.data.Entry;
import com.andronicus.journalapp.data.EntryAdapter;
import com.andronicus.journalapp.data.EntryLoader;

import java.util.ArrayList;
import java.util.List;

public class EntriesActivity extends AppCompatActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<List<Entry>>{

    public static final int ENTRIES_LOADER = 1;
    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(EntriesActivity.this));
        mAdapter = new EntryAdapter(this,new ArrayList<Entry>());
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(ENTRIES_LOADER,null,this);
    }

    @Override
    public void onClick(View view) {
        startActivity(AddNewEntryActivity.newIntent(EntriesActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entries,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search){
            Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(ENTRIES_LOADER,null,this);
    }

    @Override
    public Loader<List<Entry>> onCreateLoader(int i, Bundle bundle) {
        return new EntryLoader(EntriesActivity.this,JournalApp.getEntryDao());
    }

    @Override
    public void onLoadFinished(Loader<List<Entry>> loader, List<Entry> entries) {
        mAdapter.swap(entries);
    }

    @Override
    public void onLoaderReset(Loader<List<Entry>> loader) {
        mAdapter.swap(new ArrayList<Entry>());
    }
}
