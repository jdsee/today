package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class NoteTable {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "noteID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_RELATED_TO="relatedTo";

    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_RELATED_TO};

    static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + "TEXT PRIMARY KEY, "+
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_RELATED_TO + " TEXT REFERENCES " + SectionTable.TABLE_NAME + " ON DELETE CASCADE);";

    static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;
}
