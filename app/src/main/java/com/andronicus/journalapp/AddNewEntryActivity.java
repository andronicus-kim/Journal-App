package com.andronicus.journalapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andronicus.journalapp.utils.DatePickerFragment;

import java.util.Calendar;

public class AddNewEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{

    private TextInputEditText mTitleEditText;
    private TextInputEditText mDescEditText;
    private RelativeLayout mDateLyout;
    private TextView mDateTextView;
    private long mDate = Long.MAX_VALUE;

    public static Intent newIntent(Context context){
        return new Intent(context,AddNewEntryActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);
        mDateTextView = findViewById(R.id.date_textview);
        mTitleEditText = findViewById(R.id.title_edittext);
        mDescEditText = findViewById(R.id.desc_edittext);
        mDateLyout = findViewById(R.id.date_layout);
        mDateLyout.setOnClickListener(this);
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
}
