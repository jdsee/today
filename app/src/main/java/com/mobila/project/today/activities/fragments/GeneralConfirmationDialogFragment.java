package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public abstract class GeneralConfirmationDialogFragment extends DialogFragment {
    public static final String DIALOG_MESSAGE_EXTRA = "EXTRA_DIALOG_MESSAGE";
    public static final String DIALOG_CONFIRMING_EXTRA = "EXTRA_DIALOG_CONFIRMATION";
    public static final String DIALOG_DECLINING_EXTRA = "EXTRA_DIALOG_DECLINING";

    public static final String RESPONSE_CONFIRMED_EXTRA = "EXTRA_DIALOG_RESPONSE";
    private DialogListener callback;

    public interface DialogListener {
        void onDialogConfirmation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog);
    }

    public void setDialogListener(DialogListener listener) {
        this.callback = listener;
    }

    /**
     * Method that gets initialized if the fragment gets created
     *
     * @param savedInstanceState a bundle containing saved information
     * @return a dialog ready to be shown
     */
    @NonNull
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle entryBundle = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.addMessageToBuilder(Objects.requireNonNull(entryBundle), builder);
        this.getOptionalDialogAddendum(entryBundle, builder);
        this.addButtonsToBuilder(entryBundle, builder);
        return builder.create();
    }

    /**
     * Adding a message to the dialog is done here
     *
     * @param bundle  the bundle containing the message
     * @param builder the builder of the dialog
     */
    private void addMessageToBuilder(Bundle bundle, AlertDialog.Builder builder) {
        builder.setMessage(bundle.getString(DIALOG_MESSAGE_EXTRA));
    }

    /**
     * Method intended to be overwritten in case additional information has to be displayed
     *
     * @param bundle  the bundle containing all information intended for the dialog
     * @param builder the builder of the dialog
     */
    abstract void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder);

    /**
     * Adding buttons and their functionality is done here
     *
     * @param entryBundle the bundle containing the messages written on the buttons
     * @param builder     the builder of the dialog
     */
    private void addButtonsToBuilder(Bundle entryBundle, AlertDialog.Builder builder) {
        Bundle resultBundle = new Bundle();
        resultBundle.putAll(entryBundle);
        builder.setPositiveButton(entryBundle.getString(DIALOG_CONFIRMING_EXTRA),
                (dialog, which) -> {
                    resultBundle.putBoolean(RESPONSE_CONFIRMED_EXTRA, true);
                    onConfirmation(resultBundle);
                    callback.onDialogConfirmation(resultBundle, this);
                });
        builder.setNegativeButton(entryBundle.getString(DIALOG_DECLINING_EXTRA), null);

    }

    /**
     * Reacting on positive feedback is done here.
     * The DialogListener interface implemented by the class that creates the dialog.
     * The calling class should register itself as a subscriber and the single-method should be
     * called in this and the following method.
     */
    abstract void onConfirmation(Bundle resultBundle);
}