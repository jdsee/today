package com.mobila.project.today.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.courseListView.CourseListActivity;
import com.mobila.project.today.activities.editorActivity.EditorActivity;
import com.mobila.project.today.activities.taskView.CourseTasksActivity;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;
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
        tasks.add(new TaskImpl(1, "some Content", new Date()));
        tasks.add(new TaskImpl(2, "some Random", new Date()));
        tasks.add(new TaskImpl(3, "some mor Random", new Date()));

        intent.putParcelableArrayListExtra(Task.INTENT_EXTRA_CODE, tasks);
        intent.putExtra(NoteMock.INTENT_EXTRA_CODE,  note);
        startActivity(intent);
    }

    /**
     * Opens CourseListActivity
     * @param v View that performs this action
     */
    public void openCourseList(View v) {
        Intent intent = new Intent(this, CourseListActivity.class);
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