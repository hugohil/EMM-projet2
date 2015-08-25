package com.example.clement.emm_project2.data.database;

/**
 * Created by Clement on 23/08/15.
 */
public class ItemDatabaseHelper {

    public static final String TABLE_NAME = "item";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONGOID = "mongoid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FIELD_VIDEO = "fieldVideo";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_FIELD_POSTER = "fieldPoster";
    public static final String COLUMN_FIELD_VIGNETTE = "fieldVignette";
    public static final String COLUMN_FIELD_FICHIER = "fieldFichier";
    public static final String COLUMN_FREE = "free";
    public static final String COLUMN_CHILDREN = "children";

    public static final String CREATE_TABLE_STATEMENT = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MONGOID + " text not null, "
            + COLUMN_TITLE +" text not null, "
            + COLUMN_TYPE + " text, "
            + COLUMN_FIELD_VIDEO + " text,"
            + COLUMN_DURATION + " real,"
            + COLUMN_FIELD_POSTER + " text,"
            + COLUMN_FIELD_VIGNETTE + " text,"
            + COLUMN_FIELD_FICHIER + " text,"
            + COLUMN_FREE + " integer,"
            + COLUMN_CHILDREN + " text"
            +");";
}
