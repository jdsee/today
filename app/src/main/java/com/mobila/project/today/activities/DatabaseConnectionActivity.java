package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobila.project.today.R;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;

public class DatabaseConnectionActivity extends AppCompatActivity {

    private OrganizerDataProvider organizerDataProvider = OrganizerDataProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openDBConnectionIfNotOpenedYet();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        if (organizerDataProvider.isOpen()) {
            this.organizerDataProvider.closeDbConnection();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        this.openDBConnectionIfNotOpenedYet();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        openDBConnectionIfNotOpenedYet();
    }

    private void openDBConnectionIfNotOpenedYet() {
        if (!this.organizerDataProvider.isOpen())
            this.organizerDataProvider.openDbConnection(this);
    }
}
