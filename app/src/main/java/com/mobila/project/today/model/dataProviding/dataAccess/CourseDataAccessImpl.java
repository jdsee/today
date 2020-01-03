package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import java.util.List;

public class CourseDataAccessImpl implements CourseDataAccess {
    public static final String TAG = CourseDataAccessImpl.class.getName();
    private static CourseDataAccessImpl instance;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    static CourseDataAccess getInstance(){
        if(instance==null){
            instance = new CourseDataAccessImpl();
        }
        return instance;
    }

    @Override
    public void open(Context context) {
        this.dbHelper=new DBHelper(context);
        this.database=this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        this.dbHelper.close();
    }

    @Override
    public Semester getSemester(Identifiable course) throws DataKeyNotFoundException {
        Log.d(TAG, "requesting courses from data base");
        return null;
    }

    @Override
    public List<Section> getSections(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addSection(Identifiable course, Section section) throws DataKeyNotFoundException {

    }

    @Override
    public List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addTask(Identifiable course, Task task) throws DataKeyNotFoundException {

    }

    @Override
    public String getTitle(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setTitle(Identifiable course, String title) throws DataKeyNotFoundException {

    }
}
