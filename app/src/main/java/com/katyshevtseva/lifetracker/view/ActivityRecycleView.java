package com.katyshevtseva.lifetracker.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.katyshevtseva.lifetracker.R;
import com.katyshevtseva.lifetracker.core.Service;
import com.katyshevtseva.lifetracker.core.entity.Activity;

import java.util.List;

public class ActivityRecycleView {

    static class ActivityHolder extends RecyclerView.ViewHolder {
        private final ActivityListAdapter activityListAdapter;
        private final AppCompatActivity context;

        ActivityHolder(View view, ActivityListAdapter activityListAdapter, AppCompatActivity context) {
            super(view);
            this.activityListAdapter = activityListAdapter;
            this.context = context;
        }

        void bind(Activity activity) {
            ((TextView) itemView.findViewById(R.id.activity_title_label)).setText(activity.getTitle());

            itemView.findViewById(R.id.activity_edit_button).setOnClickListener(view ->
                    new ActivityEditDialog(activity, activityListAdapter::updateContent)
                            .show(context.getSupportFragmentManager(), "dialog2"));
        }
    }

    public static class ActivityListAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private List<Activity> items;
        private final AppCompatActivity context;

        public ActivityListAdapter(AppCompatActivity context) {
            this.context = context;
            updateContent();
        }

        @NonNull
        @Override
        public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
            return new ActivityHolder(view, this, context);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
            Activity item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void updateContent() {
            items = Service.INSTANCE.getActivities();
            notifyDataSetChanged();
        }
    }
}
