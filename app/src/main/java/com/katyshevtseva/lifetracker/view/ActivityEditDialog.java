package com.katyshevtseva.lifetracker.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.katyshevtseva.lifetracker.R;
import com.katyshevtseva.lifetracker.core.Service;
import com.katyshevtseva.lifetracker.core.entity.Activity;
import com.katyshevtseva.lifetracker.core.utils.NoArgKnob;

public class ActivityEditDialog extends DialogFragment {
    private final Activity existing;
    private final NoArgKnob activityUpdateKnob;
    private Button saveButton;
    private EditText editText;

    public ActivityEditDialog(Activity existing, NoArgKnob activityUpdateKnob) {
        this.existing = existing;
        this.activityUpdateKnob = activityUpdateKnob;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_activity_edit, null);

        saveButton = v.findViewById(R.id.save_button);
        editText = v.findViewById(R.id.activity_title_edit_text);

        saveButton.setOnClickListener(view -> saveSetting());
        if (existing != null) {
            editText.setText(existing.getTitle());
        }

        ViewUtils.associateButtonWithControls(saveButton, editText);

        return v;
    }

    private void saveSetting() {
        Service.INSTANCE.saveActivity(existing, editText.getText().toString());
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        activityUpdateKnob.execute();
    }
}
