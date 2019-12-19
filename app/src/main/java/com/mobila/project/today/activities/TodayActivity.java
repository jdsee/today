package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mobila.project.today.R;
import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.activities.fragments.AddCourseDialogFragment;
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.activities.adapters.CourseAdapter;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.SampleDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodayActivity extends AppCompatActivity
        implements GeneralConfirmationDialogFragment.OnConfirmationListener,
        AddCourseDialogFragment.OnEnterListener {

    private static Bundle DELETE_SEMESTER_DIALOG_BUNDLE;

    private List<Task> tasks;
    private List<Course> courses;
    private List<Semester> semesters;

    TextView semesterView;
    int currentSemester;

    CourseAdapter courseAdapter;

    private RootDataAccess rootDataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        this.semesterView = findViewById(R.id.semester_view);

        this.courses = SampleDataProvider.getExampleCourses();
        this.tasks = SampleDataProvider.getExampleTasks();
        this.semesters = SampleDataProvider.getExampleSemesters();


        this.rootDataAccess = OrganizerDataProvider.getInstance().getRootDataAccess();
        this.semesters = rootDataAccess.getAllSemesters();


        initCourseView();
        initTaskView();
        initSemsterView();
        setTimeDisplayed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.rootDataAccess.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.semesters = rootDataAccess.getAllSemesters();
    }

    private void setTimeDisplayed() {
        TextView dateDisplay = findViewById(R.id.day_display);
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat day =
                new SimpleDateFormat(DateUtils.DAY_WEEKDAY_FORMAT, Locale.getDefault());
        SimpleDateFormat date =
                new SimpleDateFormat(DateUtils.DAY_DATE_FORMAT, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        builder.append(day.format(calender.getTime()));
        builder.append(System.getProperty("line.separator"));
        builder.append(date.format(calender.getTime()));
        dateDisplay.setText(builder);
    }

    private void initSemsterView() {
        if (semesters.size() != 0) {
            currentSemester = semesters.size() - 1;
            this.setSemester();
        }
        showAppropiateSemesterButtons();
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(this, this.tasks));
    }

    private void initCourseView() {
        RecyclerView courseRecyclerView = findViewById(R.id.recycler_view_courses);
        this.courseAdapter = new CourseAdapter(this, this.courses);
        courseRecyclerView.setAdapter(courseAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goBackSemester(View view) {
        if (currentSemester > 0) {
            currentSemester--;
        }
        this.setSemester();
        showAppropiateSemesterButtons();
    }

    public void goForwardSemester(View view) {
        if (this.semesters.isEmpty()) {
            semesters = new ArrayList<>();
            this.currentSemester = 0;
            semesters.add(new Semester(
                    String.valueOf(currentSemester + 1), currentSemester + 1));
        } else if (currentSemester == semesters.size() - 1) {
            semesters.add(new Semester(
                    String.valueOf(semesters.size() + 1), semesters.size() + 1));
            currentSemester++;
        }
        if (currentSemester < semesters.size() - 1) {
            currentSemester++;
        }
        this.setSemester();
        showAppropiateSemesterButtons();
    }

    private void showAppropiateSemesterButtons() {
        ImageButton goForewardButton = findViewById(R.id.go_foreward_semester);
        ImageButton goBackwardsButton = findViewById(R.id.go_back_semester);
        if (currentSemester == semesters.size() - 1) {
            goForewardButton.setImageResource(R.drawable.baseline_add_24);
        } else {
            goForewardButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
        if (currentSemester == 0) {
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
        } else {
            goBackwardsButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
        if (currentSemester == 0 && semesters.size() == 0) {
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
            goForewardButton.setImageResource(R.drawable.baseline_add_24);
        }
    }

    private void setSemester() {
        if (!semesters.isEmpty()) {
            try {
                semesterView.setText(String.format(Locale.getDefault(),
                        "Semester %d", semesters.get(currentSemester).getSemesterNr()));
            } catch (UncheckedTodayException ignored) {
            }
        } else {
            semesterView.setText(String.format(Locale.getDefault(), "Semester"));
        }
    }

    public void onSemesterOptionsPressed(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        Menu menu = popupMenu.getMenu();
        popupMenu.getMenuInflater().inflate(R.menu.today_semester_options, menu);
        popupMenu.show();
    }

    public void onDeleteSemesterClicked(MenuItem item) {
        GeneralConfirmationDialogFragment confirmationDialog = new GeneralConfirmationDialogFragment();
        if (DELETE_SEMESTER_DIALOG_BUNDLE == null) {
            Bundle bundle = new Bundle();
            bundle.putString(GeneralConfirmationDialogFragment.DIALOG_MESSAGE_EXTRA,
                    "Do your really want to delete the Semester?");
            bundle.putString(GeneralConfirmationDialogFragment.DIALOG_CONFIRMING_EXTRA, "Delete");
            bundle.putString(GeneralConfirmationDialogFragment.DIALOG_DECLINING_EXTRA, "Cancel");
            DELETE_SEMESTER_DIALOG_BUNDLE = bundle;
        }
        confirmationDialog.setArguments(DELETE_SEMESTER_DIALOG_BUNDLE);
        confirmationDialog.setOnConfirmationListener(this);
        confirmationDialog.show(getSupportFragmentManager(), "Confirmation Dialog");
    }

    @Override
    public void onConfirmation(Bundle bundle) {
        boolean confirmed = bundle.getBoolean(GeneralConfirmationDialogFragment.RESPONSE_CONFIRMED_EXTRA);
        if (!semesters.isEmpty() && confirmed) {
            //Todo semester needs to be removed from db
            semesters.remove(currentSemester);
            if (currentSemester > 0) {
                currentSemester--;
            }
            this.setSemester();
            showAppropiateSemesterButtons();
        }
    }

    public void addCourse(View view) {
        AddCourseDialogFragment addCourseDialog = new AddCourseDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(AddCourseDialogFragment.DIALOG_MESSAGE,
                "Enter the name of the Course:");
        bundle.putString(AddCourseDialogFragment.DIALOG_CONFIRMING, "add");
        bundle.putString(AddCourseDialogFragment.DIALOG_DECLINING, "cancel");

        addCourseDialog.setArguments(bundle);
        addCourseDialog.setOnEnterListener(this);
        addCourseDialog.show(getSupportFragmentManager(), "Add Course Dialog");
    }

    @Override
    public void onConfirmation(Boolean confirmation, Course course) {
        if (confirmation) {
            this.courses.add(course);
            this.courseAdapter.notifyDataSetChanged();
        }
    }
}
