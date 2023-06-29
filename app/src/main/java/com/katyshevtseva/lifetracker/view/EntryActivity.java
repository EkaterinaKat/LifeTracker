package com.katyshevtseva.lifetracker.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.katyshevtseva.lifetracker.R;
import com.katyshevtseva.lifetracker.view.EntryRecyclerView.EntryListAdapter;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        RecyclerView activityList = findViewById(R.id.entry_recycle_view);
        activityList.setLayoutManager(new LinearLayoutManager(this));
        EntryListAdapter activityListAdapter = new EntryListAdapter(this);
        activityList.setAdapter(activityListAdapter);
    }
}