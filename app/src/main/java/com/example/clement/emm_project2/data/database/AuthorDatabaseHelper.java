package com.example.clement.emm_project2.data.database;

/**
 * Created by Clement on 10/08/15.
 */
public class AuthorDatabaseHelper {

    public static final String TABLE_NAME = "author";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONGOID = "mongoid";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_PICTURE = "picture";

    public static final String[] ALL_COLUMNS = {
            AuthorDatabaseHelper.COLUMN_ID,
            AuthorDatabaseHelper.COLUMN_MONGOID,
            AuthorDatabaseHelper.COLUMN_FULLNAME,
            AuthorDatabaseHelper.COLUMN_LINK
    };

    public static final String CREATE_TABLE_STATEMENT = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MONGOID + " text not null, "
            + COLUMN_FULLNAME +" text not null, "
            + COLUMN_LINK +" text,"
            + COLUMN_PICTURE+ " blob"
            +");";


}
