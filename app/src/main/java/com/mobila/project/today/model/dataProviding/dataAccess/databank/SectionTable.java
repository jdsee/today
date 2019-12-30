package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class SectionTable {

    public static final String TABLE_NAME = "sections";
    public static final String COLUMN_ID = "sectionID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LECTURER = "lecturer";
    public static final String COLUMN_RELATED_TO = "relatedTo";
    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_TITLE, COLUMN_RELATED_TO};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " VARCHAR§ PRIMARY KEY," +
                    COLUMN_TITLE + " VARCHAR," +
                    COLUMN_LECTURER + " VARCHAR" +
                    COLUMN_RELATED_TO + "INTEGER);";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;
}
