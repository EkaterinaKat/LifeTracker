package com.katyshevtseva.lifetracker.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.katyshevtseva.lifetracker.R;
import com.katyshevtseva.lifetracker.view.ActivityRecycleView.ActivityListAdapter;

public class ActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        RecyclerView activityList = findViewById(R.id.activity_recycle_view);
        activityList.setLayoutManager(new LinearLayoutManager(this));
        ActivityListAdapter activityListAdapter = new ActivityListAdapter(this);
        activityList.setAdapter(activityListAdapter);

        findViewById(R.id.new_activity_button).setOnClickListener(view ->
                new ActivityEditDialog(null, activityListAdapter::updateContent)
                        .show(getSupportFragmentManager(), "dialog2"));
    }
}