package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SectionTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.TaskTable;

import java.util.List;
import java.util.Date;
import java.util.LinkedList;

class CourseDataAccessImpl implements CourseDataAccess {
    private static CourseDataAccess instance;

    private static final String TAG = CourseDataAccess.class.getName();

    private IdentityMapper<Section> sectionCache;
    private IdentityMapper<Task> taskCache;
    private SQLiteDatabase database;

    private CourseDataAccessImpl() {
        this.sectionCache = new IdentityMapper<>();
        this.taskCache = new IdentityMapper<>();
        this.database = OrganizerDataProvider.getInstance().getDatabase();
    }

    public static CourseDataAccess getInstance() {
        if (instance == null)
            instance = new CourseDataAccessImpl();
        return instance;
    }

    @Override
    public Semester getSemester(Identifiable course) throws DataKeyNotFoundException {
        Log.d(TAG, "requesting courses from data base");
        return null;
    }

    @Override
    public List<Section> getSections(Identifiable course) throws DataKeyNotFoundException {
        List<Section> sections = this.sectionCache.get(course);
        if (sections == null) {
            sections = this.getSectionsFromDB(course);
            this.sectionCache.add(course, sections);
        }
        return sections;
    }

    /**
     * Accesses data bank to get sections associated to the specified identifiable.
     * <p>
     * Before calling this method, the caller should check if there is a matching list of sections
     * in the sectionCache-IdentityMapper. Also the caller is required to add the list returned
     * by this method to the sectionCache.
     *
     * @param course the corresponding course
     * @return a list of sections corresponding to the specified course
     */
    private List<Section> getSectionsFromDB(Identifiable course) {
        Log.d(TAG, "requesting from database: sections related to course(id:" + course.getID() + ")");
        Cursor cursor = this.database.query(SectionTable.TABLE_NAME, SectionTable.ALL_COLUMNS,
                SectionTable.COLUMN_RELATED_TO + "=?", new String[]{course.getID()},
                null, null, null);
        List<Section> sections = new LinkedList<>();
        while (cursor.moveToNext()) {
            Section section = new Section(
                    cursor.getString(cursor.getColumnIndex(SectionTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(SectionTable.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(SectionTable.COLUMN_LECTURER))
            );
            sections.add(section);
        }
        cursor.close();
        return sections;
    }


    @Override
    public void addSection(Identifiable course, Section section) throws
            DataKeyNotFoundException {
        this.addSectionToDB(course, section);
        this.sectionCache.addElement(course, section);
    }

    /**
     * Accesses database to add the specified section to the section table.
     *
     * @param course  the course corresponding to the specified section
     * @param section the section that is to be added
     */
    private void addSectionToDB(Identifiable course, Section section) {
        ContentValues values = new ContentValues();
        values.put(SectionTable.COLUMN_ID, section.getID());
        values.put(SectionTable.COLUMN_TITLE, section.getTitle());
        values.put(SectionTable.COLUMN_LECTURER, section.getLecturer());
        values.put(SectionTable.COLUMN_RELATED_TO, course.getID());
        this.database.insert(SectionTable.TABLE_NAME, null, values);
    }

    @Override
    public List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException {
        List<Task> tasks = this.taskCache.get(course);
        if (tasks == null) {
            tasks = this.getTasksFromDB(course);
            this.taskCache.add(course, tasks);
        }
        return tasks;
    }

    private List<Task> getTasksFromDB(Identifiable course) {
        Log.d(TAG, "requesting from database: tasks related to course(id:" + course.getID() + ")");
        Cursor cursor = this.database.query(TaskTable.TABLE_NAME, TaskTable.ALL_COLUMNS,
                TaskTable.COLUMN_RELATED_TO + "=?", new String[]{course.getID()},
                null, null, null);
        List<Task> tasks = new LinkedList<>();
        while (cursor.moveToNext()) {
            Task section = new Task(
                    cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_CONTENT)),
                    new Date(cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_DEADLINE)))
                    //cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_RELATED_TO))
            );
            tasks.add(section);
        }
        cursor.close();
        return tasks;
    }

    @Override
    public void addTask(Identifiable course, Task task) throws DataKeyNotFoundException {
        this.addTaskToDB(course, task);
        this.taskCache.addElement(course, task);
    }

    private void addTaskToDB(Identifiable course, Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_ID, task.getID());
        values.put(TaskTable.COLUMN_CONTENT, task.getContent());
        values.put(TaskTable.COLUMN_DEADLINE, task.getDeadline().getTime());
        values.put(TaskTable.COLUMN_RELATED_TO, course.getID());
        this.database.insert(TaskTable.TABLE_NAME, null, values);
        Log.d(TAG, "Inserted task(id:" + task.getID() + ") to database");
    }

    @Override
    public void setTitle(Identifiable course, String title) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_TITLE, title);
        this.database.update(CourseTable.TABLE_NAME, values,
                CourseTable.COLUMN_ID + "=?", new String[]{course.getID()});
    }

    @Override
    public void removeSection(Identifiable course, Section section) {
        this.database.delete(SectionTable.TABLE_NAME,
                SectionTable.COLUMN_ID +
                        "=?", new String[]{section.getID()});
        this.sectionCache.removeElement(course, section);
    }

    @Override
    public void removeTask(Identifiable course, Task task) {
        this.database.delete(TaskTable.TABLE_NAME,
                TaskTable.COLUMN_ID +
                        "=?", new String[]{task.getID()});
        this.taskCache.removeElement(course, task);
    }
}
