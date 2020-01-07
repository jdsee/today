package com.mobila.project.today.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mobila.project.today.R;
import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.activities.fragments.GeneralConfirmationDialogFragment;
import com.mobila.project.today.activities.fragments.OneEditTextDialogFragment;
import com.mobila.project.today.activities.fragments.SimpleConfirmationDialogFragment;
import com.mobila.project.today.control.TaskController;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.activities.adapters.CourseAdapter;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

//TODO show appropriate courses if semester is deleted

public class TodayActivity extends DatabaseConnectionActivity
        implements GeneralConfirmationDialogFragment.DialogListener {
    private static final String TAG = TodayActivity.class.getName();

    private static final String ADD_COURSE_DIALOG_CODE = "ADD_COURSE_DIALOG";
    public static final String DELETE_SEMESTER_DIALOG_CODE = "DELETE_COURSE_DIALOG";

    private static Bundle DELETE_SEMESTER_DIALOG_BUNDLE;

    private List<Semester> semesters;

    TextView semesterView;
    int currentSemester;

    CourseAdapter courseAdapter;

    private RootDataAccess rootDataAccess;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        this.semesterView = findViewById(R.id.semester_view);

        this.rootDataAccess = OrganizerDataProvider.getInstance().getRootDataAccess();
        this.semesters = rootDataAccess.getAllSemesters();

        initTaskView();
        initSemesterView();
        initCourseView();
        setTimeDisplayed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.semesters = rootDataAccess.getAllSemesters();
        this.taskAdapter.notifyDataSetChanged();
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
        this.taskAdapter = new TaskAdapter(this, rootDataAccess.getAllTasks());
        recyclerView.setAdapter(taskAdapter);
        EditText taskEnterField = findViewById(R.id.edit_text_add_task);
        ImageButton confirmationButton = findViewById(R.id.add_task_button);
        taskEnterField.setVisibility(View.GONE);
        confirmationButton.setVisibility(View.GONE);
    }

    private void initCourseView() {
        RecyclerView courseRecyclerView = findViewById(R.id.recycler_view_courses);
        if (!semesters.isEmpty() && this.currentSemester >= 0) {
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
            showAddCourseButton(true);
        } else if (semesters.isEmpty()) {
            goForwardButton.setImageResource(R.drawable.baseline_add_24);
            showAddCourseButton(false);
        }
        else {
            goForwardButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
            showAddCourseButton(true);
        }
        if (currentSemester <= 0) {
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
            if (currentSemester<0)showAddCourseButton(false);
        } else {
            goBackwardsButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
            showAddCourseButton(true);
        }
    }

    private void showAddCourseButton(boolean visible) {
        ImageButton addCourseButton = findViewById(R.id.add_courseButton);
        if (visible) addCourseButton.setVisibility(View.VISIBLE);
        else addCourseButton.setVisibility(View.GONE);
    }

    private void setSemester() {
        if (!semesters.isEmpty()) {
            try {
                semesterView.setText(String.format(Locale.getDefault(),
                        "Semester %d", semesters.get(currentSemester).getSemesterNr()));
            } catch (UncheckedTodayException ignored) {
            }
        } else {
            semesterView.setText(R.string.semester);
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
        confirmationDialog.setDialogListener(this);
        confirmationDialog.show(getSupportFragmentManager(), DELETE_SEMESTER_DIALOG_CODE);
    }

    public void addCourse(View view) {
        OneEditTextDialogFragment addCourseDialog = new OneEditTextDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(OneEditTextDialogFragment.DIALOG_MESSAGE_EXTRA,
                "Enter the name of the Course:");
        bundle.putString(OneEditTextDialogFragment.DIALOG_CONFIRMING_EXTRA, "add");
        bundle.putString(OneEditTextDialogFragment.DIALOG_DECLINING_EXTRA, "cancel");

        addCourseDialog.setArguments(bundle);
        addCourseDialog.setDialogListener(this);
        addCourseDialog.show(getSupportFragmentManager(), ADD_COURSE_DIALOG_CODE);
    }

    @Override
    public void onDialogConfirmation(Bundle resultBundle, GeneralConfirmationDialogFragment dialog) {
        String dialogType = dialog.getTag();
        Objects.requireNonNull(dialogType, "dialog tag missing");
        switch (dialogType) {
            case ADD_COURSE_DIALOG_CODE:
                this.onAddCourseDialogConfirmation(resultBundle);
                break;
            case DELETE_SEMESTER_DIALOG_CODE:
                this.onDeleteSemesterDialogConfirmation(resultBundle);
                break;
            default:
                NullPointerException e = new NullPointerException("dialog code unknown");
                Log.e(TAG, e.getMessage(), e);
                throw e;
        }
    }

    private void onDeleteSemesterDialogConfirmation(Bundle resultBundle) {
        boolean confirmed =
                resultBundle.getBoolean(SimpleConfirmationDialogFragment.RESPONSE_CONFIRMED_EXTRA);
        if (!semesters.isEmpty() && confirmed) {
            this.rootDataAccess.removeSemester(semesters.get(currentSemester));
            if (currentSemester > 0) {
                currentSemester--;
            }
            this.setSemester();
            showAppropriateSemesterButtons();
        }
    }

    private void onAddCourseDialogConfirmation(Bundle resultBundle) {
        String courseName = resultBundle.getString(OneEditTextDialogFragment.CONFIRMED_STRING);
        Course course = new Course(courseName);
        semesters.get(currentSemester).addCourse(course);
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
