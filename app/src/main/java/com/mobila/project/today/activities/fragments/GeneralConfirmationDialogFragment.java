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

    /**
     * Default Constructor for compiler
     */
    public GeneralConfirmationDialogFragment() { }

    /**
     * Method that gets initialized if the fragment gets created
     * @param savedInstanceState a bundle containing saved information
     * @return a dialog ready to be shown
     */
    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle entryBundle = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.addMessageToBuilder(entryBundle, builder);
        this.getOptionalDialogAddendum(entryBundle, builder);
        this.addButtonsToBuilder(entryBundle, builder);

        return builder.create();
    }

    /**
     * Adding a message to the dialog is done here
     * @param bundle the bundle containing the message
     * @param builder the builder of the dialog
     */
    private void addMessageToBuilder(Bundle bundle, AlertDialog.Builder builder) {
        builder.setMessage(bundle.getString(DIALOG_MESSAGE_EXTRA));
    }

    /**
     * Method intended to be overwritten in case additional information has to be displayed
     * @param bundle
     * @param builder
     */
    void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
    }

    @NonNull
    Bundle getConfirmationResultBundle() {
        return new Bundle();
    }

    private void addButtonsToBuilder(Bundle entryBundle, AlertDialog.Builder builder) {
        Bundle resultBundle = this.getConfirmationResultBundle();

        builder.setPositiveButton(entryBundle.getString(DIALOG_CONFIRMING_EXTRA),
                (dialog, which) -> {
                    resultBundle.putBoolean(RESPONSE_CONFIRMED_EXTRA, true);
                    this.callback.onConfirmation(resultBundle);
                });
        builder.setNegativeButton(entryBundle.getString(DIALOG_DECLINING_EXTRA),
                (dialog, which) -> {
                    resultBundle.putBoolean(RESPONSE_CONFIRMED_EXTRA, false);
                    this.callback.onConfirmation(resultBundle);
                });
    }

    public void setOnConfirmationListener(OnConfirmationListener callback) {
        this.callback = callback;
    }

    public interface OnConfirmationListener {
        void onConfirmation(Bundle bundle);
    }
}
