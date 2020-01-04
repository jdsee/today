package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class SectionTable {

    public static final String TABLE_NAME = "sections";
    public static final String COLUMN_ID = "sectionID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LECTURER = "lecturer";
    public static final String COLUMN_RELATED_TO = "relatedTo";

    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_TITLE, COLUMN_LECTURER, COLUMN_RELATED_TO};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_LECTURER + " TEXT," +
                    COLUMN_RELATED_TO + " TEXT REFERENCES " + CourseTable.TABLE_NAME + " ON DELETE CASCADE);";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;
}
