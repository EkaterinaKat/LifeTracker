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

import java.util.List;

public class EntryRecyclerView {

    static class EntryHolder extends RecyclerView.ViewHolder {
        private final EntryListAdapter entryListAdapter;
        private final AppCompatActivity context;

        EntryHolder(View view, EntryListAdapter entryListAdapter, AppCompatActivity context) {
            super(view);
            this.entryListAdapter = entryListAdapter;
            this.context = context;
        }

        void bind(String str) {
            ((TextView) itemView.findViewById(R.id.text_view)).setText(str);
        }
    }

    public static class EntryListAdapter extends RecyclerView.Adapter<EntryHolder> {
        private List<String> items;
        private final AppCompatActivity context;

        public EntryListAdapter(AppCompatActivity context) {
            this.context = context;
            updateContent();
        }

        @NonNull
        @Override
        public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.text_item, parent, false);
            return new EntryHolder(view, this, context);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryHolder holder, int position) {
            String item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void updateContent() {
            items = Service.INSTANCE.getEntries();
            notifyDataSetChanged();
        }
    }
}
