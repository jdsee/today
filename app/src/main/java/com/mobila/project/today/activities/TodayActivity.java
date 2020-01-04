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
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.activities.fragments.OneEditTextDialogFragment;
import com.mobila.project.today.activities.fragments.SimpleConfirmationDialogFragment;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.activities.adapters.CourseAdapter;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.SampleDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SemesterDataAccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodayActivity extends AppCompatActivity
        implements SimpleConfirmationDialogFragment.SimpleDialogListener,
        OneEditTextDialogFragment.OneEditTextDialogListener {

    private static Bundle DELETE_SEMESTER_DIALOG_BUNDLE;

    private List<Task> tasks;
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

        this.tasks = SampleDataProvider.getExampleTasks();

        this.rootDataAccess = OrganizerDataProvider.getInstance().getRootDataAccess();
        SemesterDataAccess semesterDataAccess = OrganizerDataProvider.getInstance().getSemesterDataAccess();
        this.rootDataAccess.open(this);
        semesterDataAccess.open(this);
        this.semesters = rootDataAccess.getAllSemesters();

        initTaskView();
        initSemesterView();
        initCourseView();
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

    private void initSemesterView() {
        currentSemester = semesters.size() != 0 ? semesters.size() - 1 : -1;
        this.setSemester();
        showAppropriateSemesterButtons();
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(this, this.tasks));
    }

    private void initCourseView() {
        RecyclerView courseRecyclerView = findViewById(R.id.recycler_view_courses);
        if (this.semesters != null && this.currentSemester >= 0) {
            this.courseAdapter = new CourseAdapter(this, semesters.get(currentSemester));
            courseRecyclerView.setAdapter(courseAdapter);
            courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void goBackSemester(View view) {
        if (currentSemester > 0) {
            currentSemester--;
        }
        this.setSemester();
        showAppropriateSemesterButtons();
        initCourseView();
    }

    public void goForwardSemester(View view) {
        if (this.semesters.isEmpty()) {
            this.currentSemester = 0;
            rootDataAccess.addSemester(new Semester(getMaxSemester() + 1));
            initSemesterView();
        } else if (currentSemester == semesters.size() - 1) {
            rootDataAccess.addSemester(new Semester(getMaxSemester() + 1));
            currentSemester++;
        }
        if (currentSemester < semesters.size() - 1) {
            currentSemester++;
        }
        this.setSemester();
        showAppropriateSemesterButtons();
        initCourseView();
    }

    private void showAppropriateSemesterButtons() {
        ImageButton goForwardButton = findViewById(R.id.go_foreward_semester);
        ImageButton goBackwardsButton = findViewById(R.id.go_back_semester);
        if (currentSemester == semesters.size() - 1) {
            goForwardButton.setImageResource(R.drawable.baseline_add_24);
        } else {
            goForwardButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
        if (currentSemester == 0) {
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
        } else {
            goBackwardsButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
        if (currentSemester == 0 && semesters.size() == 0) {
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
            goForwardButton.setImageResource(R.drawable.baseline_add_24);
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
        SimpleConfirmationDialogFragment confirmationDialog
                = new SimpleConfirmationDialogFragment();
        if (DELETE_SEMESTER_DIALOG_BUNDLE == null) {
            Bundle bundle = new Bundle();
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_MESSAGE_EXTRA,
                    "Do your really want to delete this Semester?");
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_CONFIRMING_EXTRA, "Delete");
            bundle.putString(SimpleConfirmationDialogFragment.DIALOG_DECLINING_EXTRA, "Cancel");
            DELETE_SEMESTER_DIALOG_BUNDLE = bundle;
        }
        confirmationDialog.setArguments(DELETE_SEMESTER_DIALOG_BUNDLE);
        confirmationDialog.setSimpleDialogListener(this);
        confirmationDialog.show(getSupportFragmentManager(), "Confirmation Dialog");
    }

    public void addCourse(View view) {
        OneEditTextDialogFragment addCourseDialog = new OneEditTextDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(OneEditTextDialogFragment.DIALOG_MESSAGE_EXTRA,
                "Enter the name of the Course:");
        bundle.putString(OneEditTextDialogFragment.DIALOG_CONFIRMING_EXTRA, "add");
        bundle.putString(OneEditTextDialogFragment.DIALOG_DECLINING_EXTRA, "cancel");

        addCourseDialog.setArguments(bundle);
        addCourseDialog.setOneEditTextDialogListener(this);
        addCourseDialog.show(getSupportFragmentManager(), "Add Course Dialog");
    }

    @Override
    public void onSimpleDialogConfirmation(Bundle bundle,
                                           GeneralConfirmationDialogFragment dialog) {
        boolean confirmed =
                bundle.getBoolean(SimpleConfirmationDialogFragment.RESPONSE_CONFIRMED_EXTRA);
        if (!semesters.isEmpty() && confirmed) {
            this.rootDataAccess.removeSemester(semesters.get(currentSemester));
            if (currentSemester > 0) {
                currentSemester--;
            }
            this.setSemester();
            showAppropriateSemesterButtons();
        }
    }

    @Override
    public void onAddCourseConfirmation(Bundle resultBundle,
                                        GeneralConfirmationDialogFragment dialog) {
        boolean confirmed =
                resultBundle.getBoolean(OneEditTextDialogFragment.RESPONSE_CONFIRMED_EXTRA);
        if (confirmed) {
            String courseName = resultBundle.getString(OneEditTextDialogFragment.CONFIRMED_STRING);
            Course course = new Course(courseName);
            semesters.get(currentSemester).addCourse(course);
        }
        initCourseView();
    }

    private int getMaxSemester() {
        int max = 0;
        for (Semester semester : semesters) {
            if (semester.getSemesterNr() > max) max = semester.getSemesterNr();
        }
        return max;
    }
}
