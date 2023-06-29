package com.katyshevtseva.lifetracker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class QuestionDialog extends DialogFragment {
    private String message;
    private AnswerHandler answerHandler;

    public QuestionDialog(String message, AnswerHandler answerHandler) {
        this.message = message;
        this.answerHandler = answerHandler;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .setMessage(message);
        return adb.create();
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    answerHandler.execute(true);
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    answerHandler.execute(false);
            }
        }
    };

    public interface AnswerHandler {
        void execute(boolean answer);
    }
}
