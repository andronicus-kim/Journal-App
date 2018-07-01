package com.andronicus.journalapp.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andronicus.journalapp.EntryDetailsActivity;
import com.andronicus.journalapp.R;

import java.util.List;

/**
 * Created by andronicus on 7/1/2018.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{

    private Context mContext;
    private List<Entry> mEntries;
    public EntryAdapter(Context context, List<Entry> entries){
        mContext = context;
        mEntries = entries;
    }
    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item,parent,false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        holder.bind(mEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public void swap(List<Entry> entries){
        if (mEntries == null || mEntries.size() == 0){
            mEntries = entries;
            notifyDataSetChanged();
        }
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mMonthTextview;
        private TextView mDayTextview;
        private TextView mTitleTextview;
        private TextView mDescTextview;
        public EntryViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mMonthTextview = view.findViewById(R.id.month_textview);
            mDayTextview = view.findViewById(R.id.day_textview);
            mTitleTextview = view.findViewById(R.id.title_textview);
            mDescTextview = view.findViewById(R.id.desc_textview);
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(EntryDetailsActivity.newIntent(mContext));
        }
        public void bind(Entry entry){
            String date = DateUtils.getRelativeTimeSpanString(mContext,entry.getDate()).toString();
            String[] array = date.split("");
            String month = array[0];
            String day = array[1];
            mMonthTextview.setText(month);
            mDayTextview.setText(day);
            mTitleTextview.setText(entry.getTitle());
            mDescTextview.setText(entry.getDescription());
        }
    }
}
