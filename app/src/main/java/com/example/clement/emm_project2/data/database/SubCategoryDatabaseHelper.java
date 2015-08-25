package com.example.clement.emm_project2.data.database;

/**
 * Created by perso on 11/08/15.
 */
public class SubCategoryDatabaseHelper {
    public static final String TABLE_NAME = "subcategory";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONGOID = "mongoid";
    public static final String COLUMN_TID = "tid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGEURL = "imageURL";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_CATID = "catID";
    public static final String COLUMN_V = "v";

    public static final String CREATE_TABLE_STATEMENT = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MONGOID + " text not null, "
            + COLUMN_TID + " integer, "
            + COLUMN_TITLE +" text not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_IMAGEURL + " text, "
            + COLUMN_ACTIVE + " integer, "
            + COLUMN_CATID + " text not null, "
            + COLUMN_V + " text"
            +");";
}
