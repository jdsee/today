package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mobila.project.today.R;

import java.util.Objects;

public class OneEditTextDialogFragment extends GeneralConfirmationDialogFragment {
    public static final String EDIT_TEXT_HINT = "HINT_STRING";

    public static final String CONFIRMED_STRING = "CONFIRMATION_STRING";

    private EditText editText;

    @Override
    void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_one_edit_text, null);
        builder.setView(view);
        this.editText = view.findViewById(R.id.edit_text);
        this.editText.setHint(bundle.getString(EDIT_TEXT_HINT));
    }

    @Override
    void onConfirmation(Bundle resultBundle) {
        resultBundle.putString(CONFIRMED_STRING, editText.getText().toString());
    }
}