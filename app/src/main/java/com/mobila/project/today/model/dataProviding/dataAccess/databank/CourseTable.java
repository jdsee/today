package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class CourseTable {
    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_ID = "courseID";
    public static final String COLUMN_TITLE = "courseTitle";
    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_TITLE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " STRING PRIMARY KEY," +
                    COLUMN_TITLE + " VARCHAR);";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;
    public static final String COLUMN_RELATED_TO = "relatedTo";
}
