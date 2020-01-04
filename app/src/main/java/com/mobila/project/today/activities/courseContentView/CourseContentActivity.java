package com.mobila.project.today.activities.courseContentView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.DatabaseConnectionActivity;
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.activities.fragments.TwoEditTextDialogFragment;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.activities.adapters.SectionAdapter;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Task;

import java.util.List;
import java.util.Objects;

public class CourseContentActivity extends DatabaseConnectionActivity
        implements GeneralConfirmationDialogFragment.DialogListener {
    private static final String TAG = CourseContentActivity.class.getName();

    private static final String ADD_SECTION_DIALOG_CODE = "ADD_SECTION_DIALOG";

    private Course course;
    private List<Task> tasks;
    private List<Section> sections;
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

        OrganizerDataProvider.getInstance().getCourseDataAccess().open(this);

        this.tasks = this.course.getTasks();
        this.initTaskView();

        this.sections = this.course.getSections();
        this.initSectionView();

    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.taskAdapter = new TaskAdapter(this, this.tasks);
        recyclerView.setAdapter(this.taskAdapter);
    }

    private void initSectionView() {
        RecyclerView recyclerView = findViewById(R.id.rv_section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.sectionAdapter = new SectionAdapter(this, this.sections);
        recyclerView.setAdapter(this.sectionAdapter);
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
    public void onDialogConfirmation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog) {
        String dialogType = dialog.getTag();
        Objects.requireNonNull(dialogType, "dialog tag missing");
        switch (dialogType) {
            case ADD_SECTION_DIALOG_CODE:
                this.onAddSectionDialogConfirmation(resultBundle);
                break;
            default:
                NullPointerException e = new NullPointerException("dialog code unknown");
                Log.e(TAG, e.getMessage(), e);
                throw e;
        }
    }

    private void onAddSectionDialogConfirmation(Bundle resultBundle) {
        Section section = new Section(
                resultBundle.getString(TwoEditTextDialogFragment.FIRST_EDIT_TEXT_CONTENT),
                resultBundle.getString(TwoEditTextDialogFragment.SECOND_EDIT_TEXT_CONTENT)
        );
        this.course.addSection(section);
    }
}