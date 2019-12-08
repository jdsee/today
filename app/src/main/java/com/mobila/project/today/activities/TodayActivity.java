package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobila.project.today.R;
import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.SemesterImpl;
import com.mobila.project.today.model.implementations.TaskImpl;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.activities.adapters.CourseHolderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

public class TodayActivity extends AppCompatActivity {

    private List<Task> tasks = new ArrayList<>();
    private List<CourseMock> courses = new ArrayList<>();
    private List<Semester> semesters = new ArrayList<>();

    TextView semesterView;
    int currentSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        getWindow().setNavigationBarColor(Color.WHITE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        this.semesterView = findViewById(R.id.semester_view);

        courses.add(new CourseMock(1, "Mobile Anwendungen",
                "Prof. Dr.-Ing Schwotzer", "WHC 442"));
        courses.add(new CourseMock(2, "Software Engeneering",
                "Dr. Peter Habdichlieb", "TAC 832"));
        courses.add(new CourseMock(3, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));
        courses.add(new CourseMock(4, "Mobile Anwendungen",
                "Prof. Dr.-Ing Schwotzer", "WHC 442"));
        courses.add(new CourseMock(5, "Software Engeneering",
                "Dr. Peter Habdichlieb", "TAC 832"));
        courses.add(new CourseMock(6, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));
        courses.add(new CourseMock(7, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));

        tasks.add(new TaskImpl(8, "some Content", new java.util.Date()));
        tasks.add(new TaskImpl(9, "some Random", new java.util.Date()));
        tasks.add(new TaskImpl(10, "some mor Random", new Date()));

        semesters.add(new SemesterImpl(11, 1));
        semesters.add(new SemesterImpl(11, 2));
        semesters.add(new SemesterImpl(11, 3));

        initCourseView();
        initTaskView();
        initSemsterView();
        setTimeDisplayed();
    }

    private void setTimeDisplayed() {
        TextView dateDisplay = findViewById(R.id.day_display);
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat day =
                new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat date =
                new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        builder.append(day.format(calender.getTime()));
        builder.append(System.getProperty("line.separator"));
        builder.append(date.format(calender.getTime()));
        dateDisplay.setText(builder);
    }

    private void initSemsterView() {
        try {
            currentSemester = semesters.get(semesters.size() - 1).getSemesterNr();
            setSemester(currentSemester);
        } catch (UncheckedTodayException e) {
            e.printStackTrace();
        }
        showAppropiateSemesterButtons();
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(this, tasks));
    }

    private void initCourseView() {
        RecyclerView courseRecyclerView = findViewById(R.id.recycler_view_courses);
        courseRecyclerView.setAdapter(new CourseHolderAdapter(courses));
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goBackSemester(View view) {
        if (currentSemester>1){
            try {
                currentSemester = semesters.get(currentSemester - 2).getSemesterNr();
            } catch (UncheckedTodayException ignored) { }
        }
        showAppropiateSemesterButtons();
        setSemester(currentSemester);
    }

    public void goForwardSemester(View view) {
        if (currentSemester<semesters.size()){
            try {
                currentSemester = semesters.get(currentSemester).getSemesterNr();
            } catch (UncheckedTodayException ignored) { }
        }else if (currentSemester==semesters.size()){
            //TODO add Semester
        }
        showAppropiateSemesterButtons();
        setSemester(currentSemester);
    }

    private void showAppropiateSemesterButtons(){
        ImageButton goForewardButton = findViewById(R.id.go_foreward_semester);
        ImageButton goBackwardsButton = findViewById(R.id.go_back_semester);
        if (currentSemester==semesters.size()){
            goForewardButton.setImageResource(R.drawable.baseline_add_24);
        } else {
            goForewardButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
        if (currentSemester==1){
            goBackwardsButton.setImageResource(R.drawable.transparent_placeholder);
        }
        else{
            goBackwardsButton.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
    }

    private void setSemester(int number){
        semesterView.setText(String.format(Locale.getDefault(),
                "Semester %d", number));
    }
}
