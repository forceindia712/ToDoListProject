package com.company.todolistproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {

    private final Callback callback;

    public AlertDialogFragment(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setMessage("Do you want to delete this item from list?")
                .setCancelable(false)
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Yes", (dialog, which) -> {
                    callback.setPositiveButton();
                })
                .create();
    }

}
