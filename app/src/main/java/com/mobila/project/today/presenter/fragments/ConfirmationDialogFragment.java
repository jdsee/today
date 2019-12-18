package com.mobila.project.today.presenter.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class ConfirmationDialogFragment extends DialogFragment {

    public static String DIALOG_MESSAGE = "EXTRA_DIALOG-MESSAGE";
    public static String DIALOG_CONFIRMING = "EXTRA_DIALOG_CONFIRMATION";
    public static String DIALOG_DECLINING = "EXTRA_DIALOG_DECLINING";

    private OnConfirmationListener callback;

    public ConfirmationDialogFragment(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(bundle.getString(DIALOG_MESSAGE));
        builder.setPositiveButton(bundle.getString(DIALOG_CONFIRMING),
                (dialog, which) -> callback.onConfirmation(true));
        builder.setNegativeButton(bundle.getString(DIALOG_DECLINING),
                (dialog, which) -> callback.onConfirmation(false));
        return builder.create();
    }

    public void setOnConfirmationListener(OnConfirmationListener callback){
        this.callback=callback;
    }

    public interface OnConfirmationListener{
        void onConfirmation(Boolean confirmed);
    }
}
