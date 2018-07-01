package com.andronicus.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.andronicus.journalapp.data.Entry;
import com.andronicus.journalapp.utils.AppExecutors;

public class EntryDetailsActivity extends AppCompatActivity {

    public static final String ENTRY = "ENTRY";
    private Entry mEntry;
    private TextView mDateTextview;
    private TextView mTitleTextview;
    private TextView mDescTextview;
    private long mDate = Long.MAX_VALUE;

    public static Intent newIntent(Context context,Entry entry){
        Intent intent = new Intent(context,EntryDetailsActivity.class);
        intent.putExtra(ENTRY,entry);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);
        mDateTextview = findViewById(R.id.date_textview);
        mTitleTextview = findViewById(R.id.title_textview);
        mDescTextview = findViewById(R.id.desc_textview);
        mEntry = getIntent().getParcelableExtra(ENTRY);
        if (mEntry != null){
            if (mEntry.getDate() == mDate){
                mDateTextview.setText(R.string.date_empty);
            }else {
                CharSequence date = DateUtils.getRelativeTimeSpanString(this,mEntry.getDate());
                mDateTextview.setText(date);
            }
            String title = "# " + mEntry.getTitle();
            mTitleTextview.setText(title);
            mDescTextview.setText(mEntry.getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit){
            startActivity(AddNewEntryActivity.newIntent(EntryDetailsActivity.this,mEntry));
            finish();

        }else if (item.getItemId() == R.id.action_delete){
            new AppExecutors().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    JournalApp.getEntryDao().delete(mEntry);
                }
            });
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
