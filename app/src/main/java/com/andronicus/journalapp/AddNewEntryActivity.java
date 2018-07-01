package com.andronicus.journalapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andronicus.journalapp.data.Entry;
import com.andronicus.journalapp.utils.AppExecutors;
import com.andronicus.journalapp.utils.DatePickerFragment;

import java.util.Calendar;

public class AddNewEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{

    public static final String ENTRY = "ENTRY";

    private Entry mEntry;
    private TextInputEditText mTitleEditText;
    private TextInputEditText mDescEditText;
    private RelativeLayout mDateLayout;
    private TextView mDateTextView;
    private long mDate = Long.MAX_VALUE;

    public static Intent newIntent(Context context,Entry entry){
        Intent intent = new Intent(context,AddNewEntryActivity.class);
        intent.putExtra(ENTRY,entry);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);
        mDateTextView = findViewById(R.id.date_textview);
        mTitleEditText = findViewById(R.id.title_edittext);
        mDescEditText = findViewById(R.id.desc_edittext);
        mDateLayout = findViewById(R.id.date_layout);
        mDateLayout.setOnClickListener(this);

        mEntry = getIntent().getParcelableExtra(ENTRY);
        if (mEntry != null){
           mDate = mEntry.getDate();
           mTitleEditText.setText(mEntry.getTitle());
           mDescEditText.setText(mEntry.getDescription());
        }
        updateDateDisplay();
    }

    @Override
    public void onClick(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"date-picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        setDate(calendar.getTimeInMillis());
    }
    private void setDate(long selectedDate){
        mDate = selectedDate;
        updateDateDisplay();
    }
    private long getDate(){
        return mDate;
    }
    private void updateDateDisplay(){
        if (getDate() == Long.MAX_VALUE){
            mDateTextView.setText(R.string.date_empty);
        }else {
            CharSequence date = DateUtils.getRelativeTimeSpanString(AddNewEntryActivity.this,mDate);
            mDateTextView.setText(date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_entry,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            String title = mTitleEditText.getText().toString().trim();
            String description = mDescEditText.getText().toString().trim();
            if (title.isEmpty() || description.isEmpty()){
                Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
            }
            if (mEntry != null){
                mEntry.setDate(mDate);
                mEntry.setTitle(title);
                mEntry.setDescription(description);
                updateEntry(mEntry);
                finish();
                return true;
            }else {
                saveEntry(new Entry(title,description,mDate));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEntry(final Entry entry) {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                JournalApp.getEntryDao().save(entry);
            }
        });
    }
    private void updateEntry(final Entry entry) {
        new AppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                JournalApp.getEntryDao().update(entry);
            }
        });
    }
}
