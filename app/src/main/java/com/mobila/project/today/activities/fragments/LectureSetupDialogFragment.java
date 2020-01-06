package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.mobila.project.today.R;
import com.mobila.project.today.control.utils.DatePickerFragment;
import com.mobila.project.today.control.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class LectureSetupDialogFragment extends GeneralConfirmationDialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = LectureSetupDialogFragment.class.getName();

    public static final String ROOM_NR_EDIT_TEXT_HINT = "ROOM_NR_HINT_STRING";
    public static final String DATE_EDIT_TEXT_HINT = "DATE_EDIT_HINT_STRING";
    public static final String ROOM_NR_EDIT_TEXT_CONTENT = "ROOM_NR_CONTENT_STRING";
    public static final String DATE_EDIT_TEXT_CONTENT = "DATE_CONTENT_STRING";


    private EditText txtRoomNr;
    private EditText txtDate;

    @Override
    void getOptionalDialogAddendum(Bundle bundle, AlertDialog.Builder builder) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_set_up_lecture_text, null);
        builder.setView(view);
        this.txtRoomNr = view.findViewById(R.id.txt_room_nr_pick);
        this.txtRoomNr.setHint(bundle.getString(ROOM_NR_EDIT_TEXT_HINT));
        this.txtDate = view.findViewById(R.id.txt_date_pick);
        this.txtDate.setHint(bundle.getString(DATE_EDIT_TEXT_HINT));
        this.txtDate.setOnClickListener(v -> this.showDatePickerDialog());
    }

    @Override
    void onConfirmation(Bundle resultBundle) {
        resultBundle.putString(ROOM_NR_EDIT_TEXT_CONTENT, this.txtRoomNr.getText().toString());
        resultBundle.putString(DATE_EDIT_TEXT_CONTENT, this.txtDate.getText().toString());
    }

    private void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment(this);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        java.text.SimpleDateFormat dateFormat =
                new SimpleDateFormat(DateUtils.DAY_DATE_FORMAT, Locale.getDefault());

        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        String date = dateFormat.format(c.getTime());

        Log.d(TAG, "user entered date: " + date);

        this.txtDate.setText(date);
    }
}
