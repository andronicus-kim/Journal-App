package com.andronicus.journalapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class EntriesActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries);
        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(AddNewEntryActivity.newIntent(EntriesActivity.this));
    }
}
