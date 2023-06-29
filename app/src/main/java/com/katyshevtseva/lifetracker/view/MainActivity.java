package com.katyshevtseva.lifetracker.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.katyshevtseva.lifetracker.R;
import com.katyshevtseva.lifetracker.core.Service;
import com.katyshevtseva.lifetracker.core.entity.Activity;
import com.katyshevtseva.lifetracker.db.DlDao;

public class MainActivity extends AppCompatActivity {
    private TextView currentStateLabel;
    private GridLayout activityContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DlDao dao = new DlDao(this);
        Service.init(dao);

        currentStateLabel = findViewById(R.id.current_state_label);
        activityContainer = findViewById(R.id.activity_container);

        findViewById(R.id.activity_button).setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), ActivityActivity.class)));
        findViewById(R.id.entry_button).setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), EntryActivity.class)));
    }

    @Override
    protected void onStart() {
        fillCurrentStateLabel();
        fillActivityContainer();
        super.onStart();
    }

    private void fillCurrentStateLabel() {
        currentStateLabel.setText(Service.INSTANCE.getCurrentStateInfo());
    }

    private void fillActivityContainer() {
        activityContainer.removeAllViews();
        for (Activity activity : Service.INSTANCE.getActivities()) {
            TextView textView = new TextView(this);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.block));
            textView.setTextSize(20);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setText(activity.getTitle());
            textView.setOnClickListener(getActivityClickListener(activity));
            textView.setPadding(10, 10, 10, 10);
            textView.setMaxWidth(300);
            activityContainer.addView(textView);
        }
    }

    private View.OnClickListener getActivityClickListener(Activity activity) {
        return new View.OnClickListener() {
            private static final int TIME_INTERVAL = 2000;
            private long prevClickTime;

            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() - prevClickTime < TIME_INTERVAL) {
                    Log.e("jahg", activity.getTitle());
                    onActivityClick(activity);
                }
                prevClickTime = System.currentTimeMillis();
            }
        };
    }

    private void onActivityClick(Activity activity) {
        Service.INSTANCE.startActivity(activity);
        fillCurrentStateLabel();
    }
}