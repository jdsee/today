package com.mobila.project.today.model.dataProviding.dataAccess.databank;

public class LectureTable {
    public static final String TABLE_NAME = "lectures";

    public static final String COLUMN_ID = "lectureID";
    public static final String COLUMN_NR = "lectureNr";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ROOM_NR = "roomNr";
    public static final String COLUMN_RELATED_TO = "relatedTo";

    public static final String[] ALL_COLUMNS =
            new String[]{COLUMN_ID, COLUMN_NR, COLUMN_DATE, COLUMN_ROOM_NR, COLUMN_RELATED_TO};

    static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("+
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_NR + " INTEGER, " +
                    COLUMN_DATE + " INTEGER, " +
                    COLUMN_ROOM_NR + " STRING, " +
                    COLUMN_RELATED_TO + " TEXT REFERENCES " + SectionTable.TABLE_NAME + " ON DELETE CASCADE);";

    static final String SQL_DELETE =
            "DROP TABLE " + TABLE_NAME;

}
