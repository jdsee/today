package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class AttachmentTable {
    public static final String TABLE_NAME = "attachments";

    public static final String COLUMN_ID = "attachmentID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_RELATED_TO = "relatedTo";

    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_URI, COLUMN_RELATED_TO};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_URI + " TEXT, " +
                    COLUMN_RELATED_TO + " TEXT REFERENCES " + LectureTable.TABLE_NAME + ");";
    //Todo decide if it should cascade on delete because file in contentprovider could get to be an
    //orphant

    static final String SQL_DELETE =
            "DROP TABLE "+ TABLE_NAME;
}
