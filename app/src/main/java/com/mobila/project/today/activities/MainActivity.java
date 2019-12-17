package com.mobila.project.today.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.courseContentView.CourseContentActivity;
import com.mobila.project.today.activities.editorView.EditorActivity;
import com.mobila.project.today.activities.taskView.CourseTasksActivity;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.dataProviding.SampleDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterDataSource;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private SemesterDataSource semesterDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.semesterDataSource = new SemesterDataSource(this);
        this.semesterDataSource.open();
        Log.d(TAG, "Database aquired");
    }

    public void loadSampleSemesters(View view){
        this.semesterDataSource.seedDB(SampleDataProvider.getExampleSemesters());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.semesterDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.semesterDataSource.open();
    }

    /**
     * Opens EditorActivity
     *
     * @param v View that performs this action
     */
    public void openEditor(View v) {
        Intent intent = new Intent(this, EditorActivity.class);
        //temporary inits
        intent.putExtra(Lecture.INTENT_EXTRA_CODE, SampleDataProvider.getExampleLecture());
        startActivity(intent);
    }

    /**
     * Opens CourseContentActivity
     *
     * @param v View that performs this action
     */
    public void openCourseContent(View v) {
        Intent intent = new Intent(this, CourseContentActivity.class);
        Course course = new Course("1234", "Mobile Anwendungen");
        intent.putExtra(Course.INTENT_EXTRA_CODE, course);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Opens CourseTasksActivity
     *
     * @param v View that performs this action
     */
    public void openCourseTasks(View v) {
        Intent intent = new Intent(this, CourseTasksActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openToday(View view) {
        Intent intent = new Intent(this, TodayActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}