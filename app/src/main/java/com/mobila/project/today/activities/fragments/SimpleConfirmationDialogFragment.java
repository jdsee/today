
package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

public class SimpleConfirmationDialogFragment extends GeneralConfirmationDialogFragment {
    private SimpleDialogListener callback;

    @Override
    void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
    }

    @Override
    void onConfirmation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog) {
        this.callback.onSimpleDialogConfirmation(resultBundle, dialog);
    }

    @Override
    void onCancellation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog) {
        this.callback.onSimpleDialogConfirmation(resultBundle, dialog);
    }

    public void setSimpleDialogListener(SimpleDialogListener listener) {
        this.callback = listener;
    }

    public interface SimpleDialogListener {
        void onSimpleDialogConfirmation(Bundle bundle, GeneralConfirmationDialogFragment dialog);
    }
}