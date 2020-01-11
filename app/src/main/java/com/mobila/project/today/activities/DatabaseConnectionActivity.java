package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;

public class DatabaseConnectionActivity extends AppCompatActivity {

    private OrganizerDataProvider organizerDataProvider = OrganizerDataProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openDBConnectionIfNeccessary();
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
        openDBConnectionIfNeccessary();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        openDBConnectionIfNeccessary();
    }

    private void openDBConnectionIfNeccessary() {
        if (!this.organizerDataProvider.isOpen())
            this.organizerDataProvider.openDbConnection(this);
    }
}
