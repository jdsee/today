package com.mobila.project.today.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.courseContentView.CourseContentActivity;
import com.mobila.project.today.activities.editorView.EditorActivity;
import com.mobila.project.today.activities.taskView.CourseTasksActivity;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.modelMock.NoteMock;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Opens EditorActivity
     * @param v View that performs this action
     */
    public void openEditor(View v) {
        Intent intent = new Intent(this, EditorActivity.class);
        //temporary inits
        NoteMock note = new NoteMock(3, "Headline", new SpannableString("Inhalt"),
                2, "Mobile Anwendungen", "Übung",
                "Veranstalltung 3", "07.05.18");

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "some Content", new Date()));
        tasks.add(new Task(2, "some Random", new Date()));
        tasks.add(new Task(3, "some mor Random", new Date()));

        intent.putParcelableArrayListExtra(Task.INTENT_EXTRA_CODE, tasks);
        intent.putExtra(NoteMock.INTENT_EXTRA_CODE,  note);
        startActivity(intent);
    }

    /**
     * Opens CourseContentActivity
     * @param v View that performs this action
     */
    public void openCourseContent(View v) {
        Intent intent = new Intent(this, CourseContentActivity.class);
        Course course = new Course(1234, "Mobile Anwendungen");
        intent.putExtra(Course.INTENT_EXTRA_CODE, course);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Opens CourseTasksActivity
     * @param v View that performs this action
     */
    public void openCourseTasks(View v) {
        Intent intent = new Intent(this, CourseTasksActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openToday(View view){
        Intent intent = new Intent(this, TodayActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}