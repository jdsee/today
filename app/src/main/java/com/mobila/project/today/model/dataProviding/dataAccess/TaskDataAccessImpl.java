package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.TaskTable;

class TaskDataAccessImpl extends ParentDataAccessImpl implements TaskDataAccess {
    private static TaskDataAccess instance;

    static TaskDataAccess getInstance() {
        if (instance == null)
            instance = new TaskDataAccessImpl();
        return instance;
    }

    public static final String TAG = TaskDataAccessImpl.class.getName();

    private TaskDataAccessImpl() {
    }

    @Override
    public Course getCourse(){
//        this.database.query()
        return null;
    }

    @Override
    public void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_DEADLINE, date.getTime());
        this.updateTaskInDB(task, values);
    }

    @Override
    public void setContent(Identifiable task, String content) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_CONTENT, content);
        this.updateTaskInDB(task, values);
    }

    @Override
    public Course getCourse(Identifiable task) {
        Cursor taskCursor = this.database.query(TaskTable.TABLE_NAME, new String[]{TaskTable.COLUMN_RELATED_TO},
                TaskTable.COLUMN_ID + "=?", new String[]{task.getID()},
                null, null, null);
        if (!taskCursor.moveToNext())
            throw new DataKeyNotFoundException("FATAL ERROR: no parent found for given task!");
        String courseID = taskCursor.getString(taskCursor.getColumnIndex(TaskTable.COLUMN_RELATED_TO));
        taskCursor.close();

        Cursor courseCursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                CourseTable.COLUMN_ID + "=?", new String[]{courseID},
                null, null, null);
        courseCursor.moveToFirst();
        Course course = new Course(
                courseCursor.getString(courseCursor.getColumnIndex(CourseTable.COLUMN_ID)),
                courseCursor.getString(courseCursor.getColumnIndex(CourseTable.COLUMN_TITLE))
        );
        courseCursor.close();

        return course;
    }

    private void updateTaskInDB(Identifiable task, ContentValues values) {
        this.database.update(TaskTable.TABLE_NAME, values,
                TaskTable.COLUMN_ID + "=?", new String[]{task.getID()});
    }
}