package com.mobila.project.today.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.activitys.editorActivity.EditorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Opens EditorActivity
     * @param v
     */
    public void openEditor(View v) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Opens CourseListActivity
     * @param v
     */
    public void openCourseList(View v) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}