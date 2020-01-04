package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mobila.project.today.R;

import java.util.Objects;

public class TwoEditTextDialogFragment extends GeneralConfirmationDialogFragment {
    public static final String FIRST_EDIT_TEXT_HINT = "FIRST_HINT_STRING";
    public static final String SECOND_EDIT_TEXT_HINT = "SECOND_HINT_STRING";
    public static final String FIRST_EDIT_TEXT_CONTENT = "FIRST_CONTENT_STRING";
    public static final String SECOND_EDIT_TEXT_CONTENT = "SECOND_CONTENT_STRING";


    private EditText firstEditText;
    private EditText secondEditText;

    @Override
    void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_two_edit_text, null);
        builder.setView(view);
        this.firstEditText = view.findViewById(R.id.first_edit_text);
        this.firstEditText.setHint(bundle.getString(FIRST_EDIT_TEXT_HINT));
        this.secondEditText = view.findViewById(R.id.second_edit_text);
        this.secondEditText.setHint(bundle.getString(SECOND_EDIT_TEXT_HINT));
    }

    @Override
    void onConfirmation(Bundle resultBundle) {
        resultBundle.putString(FIRST_EDIT_TEXT_CONTENT,this.firstEditText.getText().toString());
        resultBundle.putString(SECOND_EDIT_TEXT_CONTENT,this.secondEditText.getText().toString());
    }
}
