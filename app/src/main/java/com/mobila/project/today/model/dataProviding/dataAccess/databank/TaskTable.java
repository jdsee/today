package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class TaskTable {
    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "taskID";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_RELATED_TO = "relatedTo";
    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_CONTENT,COLUMN_DEADLINE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " STRING PRIMARY KEY, " +
                    COLUMN_CONTENT + " VARCHAR," +
                    COLUMN_DEADLINE + " INTEGER," +
                    COLUMN_RELATED_TO + " VARCHAAR);";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;
}
