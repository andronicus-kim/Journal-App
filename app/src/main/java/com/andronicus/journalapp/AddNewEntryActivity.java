package com.andronicus.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddNewEntryActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,AddNewEntryActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);
    }
}
