package com.andronicus.journalapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
    private List<Entry> mEntries;

    public static Intent newIntent(Context context){
        return new Intent(context,EntriesActivity.class);
    }

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
        startActivity(AddNewEntryActivity.newIntent(EntriesActivity.this,null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entries,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Entry> filteredEntries = filter(mEntries,newText);
                mAdapter.setFilter(filteredEntries);
                mRecyclerView.scrollToPosition(0);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    private List<Entry> filter(List<Entry> entries, String newText) {
        String convertedToLowerCase = newText.toLowerCase();
        List<Entry> filteredList = new ArrayList<>();
        for (Entry entry: entries){
            String title = entry.getTitle().toLowerCase();
            if (title.contains(convertedToLowerCase)){
                filteredList.add(entry);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search){
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
        mEntries = entries;
    }

    @Override
    public void onLoaderReset(Loader<List<Entry>> loader) {
        mAdapter.swap(new ArrayList<Entry>());
    }
}
