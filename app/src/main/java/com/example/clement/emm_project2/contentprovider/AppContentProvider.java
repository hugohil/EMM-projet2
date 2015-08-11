package com.example.clement.emm_project2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.clement.emm_project2.database.AuthorDatabaseHelper;
import com.example.clement.emm_project2.database.CategoryDatabaseHelper;
import com.example.clement.emm_project2.database.DatabaseHelper;
import com.example.clement.emm_project2.model.Author;

import org.apache.http.auth.AUTH;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Clement on 11/08/15.
 */
public class AppContentProvider extends ContentProvider {

    private final String TAG = AppContentProvider.class.getSimpleName();

    // Database
    private DatabaseHelper database;

    // Authority
    private static final String AUTHORITY = "com.exmaple.clement.emm_project2.contentprovider";

    // Content URIS
    private static final String PATH_AUTHORS = "authors";
    private static final String PATH_CATEGORIES = "categories";

    public static final Uri CONTENT_URI_AUTHORS = Uri.parse("content://" + AUTHORITY
                + "/" + PATH_AUTHORS);
    public static final Uri CONTENT_URI_CATEGORIES = Uri.parse("content://" + AUTHORITY
            + "/" + PATH_CATEGORIES);

    // URI Matcher setup
    private static final int AUTHORS = 10;
    private static final int AUTHOR_ID = 20;
    private static final int CATEGORIES = 30;
    private static final int CATEGORY_ID = 40;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH_AUTHORS, AUTHORS);
        sURIMatcher.addURI(AUTHORITY, PATH_AUTHORS + "/#", AUTHOR_ID);
        sURIMatcher.addURI(AUTHORITY, PATH_CATEGORIES, CATEGORIES);
        sURIMatcher.addURI(AUTHORITY, PATH_CATEGORIES + "/#", CATEGORY_ID);
    }

    @Override
    public boolean onCreate() {
        this.database = new DatabaseHelper(getContext());
        return false;
    }

    //@Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(uri, projection);


        int uriType = sURIMatcher.match(uri);
        switch (uriType) {

            case AUTHOR_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(AuthorDatabaseHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case AUTHORS:
                queryBuilder.setTables(AuthorDatabaseHelper.TABLE_NAME);
                break;

            case CATEGORY_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(CategoryDatabaseHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case CATEGORIES:
                queryBuilder.setTables(CategoryDatabaseHelper.TABLE_NAME);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        Uri resultingUri;

        switch (uriType) {

            case AUTHORS:
                id = sqlDB.insert(AuthorDatabaseHelper.TABLE_NAME, null, values);
                resultingUri = Uri.parse(PATH_AUTHORS + "/" + id);
                break;

            case CATEGORIES:
                id = sqlDB.insert(CategoryDatabaseHelper.TABLE_NAME, null, values);
                resultingUri = Uri.parse(PATH_CATEGORIES + "/" + id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return resultingUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id;

        switch (uriType) {
            case AUTHORS:
                rowsDeleted = sqlDB.delete(AuthorDatabaseHelper.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case AUTHOR_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(AuthorDatabaseHelper.TABLE_NAME,
                            AuthorDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(AuthorDatabaseHelper.TABLE_NAME,
                            AuthorDatabaseHelper.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case CATEGORIES:
                rowsDeleted = sqlDB.delete(CategoryDatabaseHelper.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case CATEGORY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(CategoryDatabaseHelper.TABLE_NAME,
                            CategoryDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(CategoryDatabaseHelper.TABLE_NAME,
                            CategoryDatabaseHelper.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id;

        switch (uriType) {
            case AUTHORS:
                rowsUpdated = sqlDB.update(AuthorDatabaseHelper.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case AUTHOR_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(AuthorDatabaseHelper.TABLE_NAME,
                            values,
                            AuthorDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(AuthorDatabaseHelper.TABLE_NAME,
                            values,
                            AuthorDatabaseHelper.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case CATEGORIES:
                rowsUpdated = sqlDB.update(CategoryDatabaseHelper.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CATEGORY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(CategoryDatabaseHelper.TABLE_NAME,
                            values,
                            CategoryDatabaseHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(CategoryDatabaseHelper.TABLE_NAME,
                            values,
                            CategoryDatabaseHelper.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(Uri uri, String[] projection) {

        int uriType = sURIMatcher.match(uri);
        String[] available;
        switch (uriType) {
            case AUTHORS:
                available = AuthorDatabaseHelper.ALL_COLUMNS;
                break;
            case CATEGORIES:
                available = CategoryDatabaseHelper.ALL_COLUMNS;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
