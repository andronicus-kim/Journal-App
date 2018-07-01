package com.andronicus.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryDetailsActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,EntryDetailsActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);
    }
}
