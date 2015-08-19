package com.example.clement.emm_project2.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.SharedPrefUtil;

/**
 * Created by Clement on 10/08/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 62;

    // Database Name
    private static final String DATABASE_NAME = "elephormDB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AuthorDatabaseHelper.CREATE_TABLE_STATEMENT);
        db.execSQL(CategoryDatabaseHelper.CREATE_TABLE_STATEMENT);
        db.execSQL(SubCategoryDatabaseHelper.CREATE_TABLE_STATEMENT);
        db.execSQL(FormationDatabaseHelper.CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + AuthorDatabaseHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryDatabaseHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubCategoryDatabaseHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FormationDatabaseHelper.TABLE_NAME);


        SharedPrefUtil.clearAllDataInCache();
        onCreate(db);
    }

    public void upgradeDbIfNecessary() {
        SQLiteDatabase db = getWritableDatabase();
        int versionNb = db.getVersion();
        if(versionNb < DATABASE_VERSION) {
            onUpgrade(db, versionNb, DATABASE_VERSION);
        }
    }
}
