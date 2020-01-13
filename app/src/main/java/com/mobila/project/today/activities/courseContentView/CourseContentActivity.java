package com.mobila.project.today.activities.courseContentView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.DatabaseConnectionActivity;
import com.mobila.project.today.activities.adapters.RecyclerViewButtonClickListener;
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.activities.fragments.LectureSetupDialogFragment;
import com.mobila.project.today.activities.fragments.SimpleConfirmationDialogFragment;
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

import static com.mobila.project.today.activities.TodayActivity.DELETE_SEMESTER_DIALOG_CODE;

public class CourseContentActivity extends DatabaseConnectionActivity
        implements GeneralConfirmationDialogFragment.DialogListener, RecyclerViewButtonClickListener {
    private static final String TAG = CourseContentActivity.class.getName();

    private static final String ADD_SECTION_DIALOG_CODE = "ADD_SECTION_DIALOG_CODE";
    private static final String ADD_LECTURE_DIALOG_CODE = "ADD_LECTURE_DIALOG_CODE";
    private static final String DELETE_SECTION_DIALOG_CODE = "DELETE_SECTION_DIALOG_CODE";
    private static final String SECTION_POSITION_DIALOG_CODE = "SECTION_POSITION_DIALOG_CODE";
    private static Bundle DELETE_SECTION_DIALOG_BUNDLE = null;

    private Course course;
    private List<Section> sections;
    private int currentSectionPosition;
    private TaskAdapter taskAdapter;
    private SectionAdapter sectionAdapter;

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
    protected void onResume() {
        super.onResume();
        this.taskAdapter.notifyDataSetChanged();
        this.sectionAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        this.taskAdapter.removeCheckedTasks();
        super.onPause();
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Task> tasks = this.course.getTasks();
        this.taskAdapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(taskAdapter);
        EditText taskEnterField = findViewById(R.id.edit_text_add_task);
        ImageButton confirmationButton = findViewById(R.id.add_task_button);
        new TaskController(this, taskEnterField, confirmationButton, this.course, taskAdapter);
    }

    private void initSectionView() {
        RecyclerView recyclerView = findViewById(R.id.rv_section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.sectionAdapter = new SectionAdapter(this, this.sections, this);
        recyclerView.setAdapter(this.sectionAdapter);
    }

    public void onAddSectionClicked(View view) {
        TwoEditTextDialogFragment dialog = new TwoEditTextDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(TwoEditTextDialogFragment.DIALOG_MESSAGE_EXTRA, "Add section");
        bundle.putString(TwoEditTextDialogFragment.FIRST_EDIT_TEXT_HINT, "*section name");
        bundle.putString(TwoEditTextDialogFragment.SECOND_EDIT_TEXT_HINT, "lecturer");
        bundle.putString(TwoEditTextDialogFragment.DIALOG_CONFIRMING_EXTRA, "add");
        bundle.putString(TwoEditTextDialogFragment.DIALOG_DECLINING_EXTRA, "cancel");

        dialog.setArguments(bundle);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), ADD_SECTION_DIALOG_CODE);
    }

    @Override
    public void onRecyclerViewButtonClicked(View view, int position) {
        this.currentSectionPosition = position;
        String buttonContext = (String) view.getTag();
        switch (buttonContext){
            case SectionAdapter.BTN_ADD_LECTURE_TAG:
                this.onAddLectureClicked(position);
                break;
            case SectionAdapter.BTN_REMOVE_SECTION_TAG:
                this.onRemoveSectionClicked(position);
                break;
            default:
                IllegalArgumentException e = new IllegalArgumentException("button desriptor unknown");
                Log.e(TAG, "unknown button descriptor", e);
                throw e;
        }
    }

    private void onAddLectureClicked(int position){
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

    public void onRemoveSectionClicked(int position){
        SimpleConfirmationDialogFragment dialog = new SimpleConfirmationDialogFragment();

        if (DELETE_SECTION_DIALOG_BUNDLE == null) {
            Bundle bundle = new Bundle();
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_MESSAGE_EXTRA,
                    "Do your really want to delete this section?");
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_CONFIRMING_EXTRA, "Delete");
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_DECLINING_EXTRA, "Cancel");
//            bundle.putInt(SECTION_POSITION_DIALOG_CODE, position);
            DELETE_SECTION_DIALOG_BUNDLE = bundle;
        }
        dialog.setArguments(DELETE_SECTION_DIALOG_BUNDLE);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), DELETE_SECTION_DIALOG_CODE);
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
            case DELETE_SECTION_DIALOG_CODE:
                this.onDeleteSectionDialogConfirmation();
                break;
            default:
                NullPointerException e = new NullPointerException("dialog code unknown");
                Log.e(TAG, e.getMessage(), e);
                throw e;
        }
    }

    private void onDeleteSectionDialogConfirmation() {
        Section currentSection = this.sections.get(currentSectionPosition);
        this.course.removeSection(currentSection);
            this.sectionAdapter.notifyDataSetChanged();
    }

    private void onAddLectureDialogConfirmation(Bundle resultBundle) {
        Section currentSection = this.sections.get(this.currentSectionPosition);
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
        String roomNr = resultBundle.getString(LectureSetupDialogFragment.ROOM_NR_EDIT_TEXT_CONTENT);
        Lecture lecture = new Lecture(lectureNr, date, roomNr);

        currentSection.addLecture(lecture);
        this.sectionAdapter.notifyDataSetChanged();
    }

    private void onAddSectionDialogConfirmation(Bundle resultBundle) {

        String sectionName = resultBundle.getString(TwoEditTextDialogFragment.FIRST_EDIT_TEXT_CONTENT);

        if (sectionName==null||!sectionName.isEmpty()) {
            Section section = new Section(
                    sectionName,
                    resultBundle.getString(TwoEditTextDialogFragment.SECOND_EDIT_TEXT_CONTENT)
            );
            this.course.addSection(section);
        } else Toast.makeText(this,"Please give sections a name!", Toast.LENGTH_LONG).show();
    }
}