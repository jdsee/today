package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;

public class DatabaseConnectionActivity extends AppCompatActivity {

    private OrganizerDataProvider organizerDataProvider = OrganizerDataProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDBConntectionIfNeccessary();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.organizerDataProvider.closeDbConnection();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        this.organizerDataProvider.closeDbConnection();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        openDBConntectionIfNeccessary();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        openDBConntectionIfNeccessary();
    }

    private void openDBConntectionIfNeccessary(){
        if (!this.organizerDataProvider.isOpen())
            this.organizerDataProvider.openDbConnection(this);
    }
}
