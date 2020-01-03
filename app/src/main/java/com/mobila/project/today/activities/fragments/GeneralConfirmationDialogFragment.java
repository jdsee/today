package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class GeneralConfirmationDialogFragment extends DialogFragment {

    public static final String DIALOG_MESSAGE_EXTRA = "EXTRA_DIALOG_MESSAGE";
    public static final String DIALOG_CONFIRMING_EXTRA = "EXTRA_DIALOG_CONFIRMATION";
    public static final String DIALOG_DECLINING_EXTRA = "EXTRA_DIALOG_DECLINING";

    public static final String RESPONSE_CONFIRMED_EXTRA = "EXTRA_DIALOG_RESPONSE";

    private OnConfirmationListener callback;

    public GeneralConfirmationDialogFragment() {
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle entryBundle = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder = this.addInitialMessageToBuilder(entryBundle, builder);

        builder = this.getOptionalDialogAddendum(entryBundle, builder);

        builder = this.addButtonsToBuilder(entryBundle, builder);

        return builder.create();
    }

    private AlertDialog.Builder addInitialMessageToBuilder(Bundle bundle, AlertDialog.Builder builder) {
        builder.setMessage(bundle.getString(DIALOG_MESSAGE_EXTRA));
        return builder;
    }

    AlertDialog.Builder getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
        return builder;
    }

    @NonNull
    Bundle getConfirmationResultBundle() {
        return new Bundle();
    }

    private AlertDialog.Builder addButtonsToBuilder(Bundle entryBundle, AlertDialog.Builder builder) {
        Bundle resultBundle = this.getConfirmationResultBundle();

        builder.setPositiveButton(entryBundle.getString(DIALOG_CONFIRMING_EXTRA),
                (dialog, which) -> {
                    resultBundle.putBoolean(RESPONSE_CONFIRMED_EXTRA, true);
                    callback.onConfirmation(resultBundle);
                });
        builder.setNegativeButton(entryBundle.getString(DIALOG_DECLINING_EXTRA),
                (dialog, which) -> {
                    resultBundle.putBoolean(RESPONSE_CONFIRMED_EXTRA, false);
                    callback.onConfirmation(resultBundle);
                });
        return builder;
    }

    public void setOnConfirmationListener(OnConfirmationListener callback) {
        this.callback = callback;
    }

    public interface OnConfirmationListener {
        void onConfirmation(Bundle bundle);
    }
}
