package com.example.clement.emm_project2.data.database;

/**
 * Created by Clement on 10/08/15.
 */
public class CategoryDatabaseHelper {
    public static final String TABLE_NAME = "category";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONGOID = "mongoid";
    public static final String COLUMN_TID = "tid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGEURL = "imageURL";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_SUBCATEGORIES = "subcategories";
    public static final String COLUMN_V = "v";

    public static final String CREATE_TABLE_STATEMENT = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MONGOID + " text not null, "
            + COLUMN_TID + " integer, "
            + COLUMN_TITLE +" text not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_IMAGEURL + " text, "
            + COLUMN_SUBCATEGORIES + " blob, "
            + COLUMN_ACTIVE + " integer, "
            + COLUMN_V +  " text"
             +");";
}
