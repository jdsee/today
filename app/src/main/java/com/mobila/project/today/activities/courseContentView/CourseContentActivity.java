package com.mobila.project.today.activities.courseContentView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.DatabaseConnectionActivity;
import com.mobila.project.today.activities.adapters.RecyclerViewButtonClickListener;
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.activities.fragments.LectureSetupDialogFragment;
import com.mobila.project.today.activities.fragments.TwoEditTextDialogFragment;
import com.mobila.project.today.control.TaskController;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.activities.adapters.SectionAdapter;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CourseContentActivity extends DatabaseConnectionActivity
        implements GeneralConfirmationDialogFragment.DialogListener, RecyclerViewButtonClickListener {
    private static final String TAG = CourseContentActivity.class.getName();

    private static final String ADD_SECTION_DIALOG_CODE = "ADD_SECTION_DIALOG";
    private static final String ADD_LECTURE_DIALOG_CODE = "ADD_LECTURE_DIALOG_CODE";

    private Course course;
    private List<Section> sections;
    private int currentPosition;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);

        this.course = this.getIntent().getParcelableExtra(Course.INTENT_EXTRA_CODE);

        ActionBar ab = this.getSupportActionBar();
        assert ab != null;
        ab.setTitle(this.course.getTitle());

        this.initTaskView();

        this.sections = this.course.getSections();
        this.initSectionView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.taskAdapter = new TaskAdapter(this, this.course.getTasks());
        recyclerView.setAdapter(taskAdapter);
        EditText taskEnterField = findViewById(R.id.edit_text_add_task);
        ImageButton confirmationButton = findViewById(R.id.add_task_button);
        new TaskController(taskEnterField, confirmationButton, this.course, taskAdapter);
    }

    private void initSectionView() {
        RecyclerView recyclerView = findViewById(R.id.rv_section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SectionAdapter sectionAdapter = new SectionAdapter(this, this.sections, this);
        recyclerView.setAdapter(sectionAdapter);
    }

    public void onAddSectionClicked(View view) {
        TwoEditTextDialogFragment dialog = new TwoEditTextDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(TwoEditTextDialogFragment.DIALOG_MESSAGE_EXTRA, "Add section");
        bundle.putString(TwoEditTextDialogFragment.FIRST_EDIT_TEXT_HINT, "section name");
        bundle.putString(TwoEditTextDialogFragment.SECOND_EDIT_TEXT_HINT, "lecturer");
        bundle.putString(TwoEditTextDialogFragment.DIALOG_CONFIRMING_EXTRA, "add");
        bundle.putString(TwoEditTextDialogFragment.DIALOG_DECLINING_EXTRA, "cancel");

        dialog.setArguments(bundle);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), ADD_SECTION_DIALOG_CODE);
    }

    @Override
    public void recyclerViewButtonClicked(View view, int position) {
        this.currentPosition = position;

        LectureSetupDialogFragment dialog = new LectureSetupDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(LectureSetupDialogFragment.DIALOG_MESSAGE_EXTRA, "Add lecture");
        bundle.putString(LectureSetupDialogFragment.ROOM_NR_EDIT_TEXT_HINT, "room number");
        bundle.putString(LectureSetupDialogFragment.DATE_EDIT_TEXT_HINT, "lecture date");
        bundle.putString(LectureSetupDialogFragment.DIALOG_CONFIRMING_EXTRA, "add");
        bundle.putString(LectureSetupDialogFragment.DIALOG_DECLINING_EXTRA, "cancel");

        dialog.setArguments(bundle);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), ADD_LECTURE_DIALOG_CODE);
    }

    @Override
    public void onDialogConfirmation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog) {
        String dialogType = dialog.getTag();
        Objects.requireNonNull(dialogType, "dialog tag missing");
        switch (dialogType) {
            case ADD_SECTION_DIALOG_CODE:
                this.onAddSectionDialogConfirmation(resultBundle);
                break;
            case ADD_LECTURE_DIALOG_CODE:
                this.onAddLectureDialogConfirmation(resultBundle);
                break;
            default:
                NullPointerException e = new NullPointerException("dialog code unknown");
                Log.e(TAG, e.getMessage(), e);
                throw e;
        }
    }

    private void onAddLectureDialogConfirmation(Bundle resultBundle) {
        Section currentSection = this.sections.get(this.currentPosition);
        Optional maxLectureNr = currentSection.getLectures().stream()
                .map(Lecture::getLectureNr)
                .max(Integer::compareTo);
        int lectureNr = maxLectureNr.isPresent() ? (int) maxLectureNr.get() + 1 : 1;
        Date date = null;
        try {
            date = DateUtils.parseStringToDate(resultBundle.getString(LectureSetupDialogFragment.DATE_EDIT_TEXT_CONTENT));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "given date could not be parsed", e);
        }
        String roomNr = resultBundle.getString(LectureSetupDialogFragment.ROOM_NR_EDIT_TEXT_HINT);
        Lecture lecture = new Lecture(lectureNr, date, roomNr);

        currentSection.addLecture(lecture);
    }

    private void onAddSectionDialogConfirmation(Bundle resultBundle) {
        Section section = new Section(
                resultBundle.getString(TwoEditTextDialogFragment.FIRST_EDIT_TEXT_CONTENT),
                resultBundle.getString(TwoEditTextDialogFragment.SECOND_EDIT_TEXT_CONTENT)
        );
        this.course.addSection(section);
    }
}