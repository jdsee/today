package com.mobila.project.today.activities.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Course;

import java.util.UUID;

public class AddCourseDialogFragment extends DialogFragment {

    public static String DIALOG_MESSAGE = "ETRA_DIALOG_MESSAGE";
    public static String DIALOG_CONFIRMING = "EXTRA_DIALOG_CONFIRMATION";
    public static String DIALOG_DECLINING = "EXTRA_DIALOG_DECLINING";

    private OnEnterListener callback;

    private EditText editTextCourseName;

    public AddCourseDialogFragment(){}

    //Todo add our uuid
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_course, null);

        builder.setTitle(bundle.getString(DIALOG_MESSAGE));
        builder.setView(inflater.inflate(R.layout.dialog_add_course, null));

        this.editTextCourseName = view.findViewById(R.id.course_name_edit_text);

        builder.setPositiveButton(bundle.getString(DIALOG_CONFIRMING),
                ((dialog, which) -> callback.onConfirmation(true,
                        new Course(UUID.randomUUID().toString(),
                                editTextCourseName.getText().toString()))));

        builder.setNegativeButton(bundle.getString(DIALOG_DECLINING),
                ((dialog, which) -> callback.onConfirmation(false, null)));


        return builder.create();
    }

    public void setOnEnterListener(OnEnterListener callback){
        this.callback=callback;
    }

    public interface OnEnterListener{
        void onConfirmation(Boolean confirmation, Course course);
    }
}
