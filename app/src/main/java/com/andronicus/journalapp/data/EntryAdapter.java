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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andronicus on 7/1/2018.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{

    private Context mContext;
    private List<Entry> mEntries;
    private long mDate = Long.MAX_VALUE;
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
        mEntries = entries;
        notifyDataSetChanged();
    }

    public void setFilter(List<Entry> filteredEntries) {
        mEntries = new ArrayList<>();
        mEntries.addAll(filteredEntries);
        notifyDataSetChanged();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mDateTextview;
        private TextView mTitleTextview;
        private TextView mDescTextview;
        public EntryViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mDateTextview = view.findViewById(R.id.date_textview);
            mTitleTextview = view.findViewById(R.id.title_textview);
            mDescTextview = view.findViewById(R.id.desc_textview);
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(EntryDetailsActivity.newIntent(mContext,mEntries.get(getAdapterPosition())));
        }
        public void bind(Entry entry){
            if (entry.getDate() == mDate){
                mDateTextview.setText(R.string.date_empty);
            }else {
                CharSequence date = DateUtils.getRelativeTimeSpanString(mContext,entry.getDate());
                mDateTextview.setText(date);
            }
            mTitleTextview.setText(entry.getTitle());
            mDescTextview.setText(entry.getDescription());
        }
    }
}
