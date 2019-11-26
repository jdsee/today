package com.mobila.project.today.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.dataStructureView.CourseListActivity;
import com.mobila.project.today.activities.editorActivity.EditorActivity;
import com.mobila.project.today.activities.taskView.CourseTasksActivity;
import com.mobila.project.today.modelMock.NoteMock;

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
}