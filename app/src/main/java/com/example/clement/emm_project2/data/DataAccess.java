package com.example.clement.emm_project2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.UserDictionary;
import android.util.Log;

import com.example.clement.emm_project2.contentprovider.AppContentProvider;
import com.example.clement.emm_project2.database.AuthorDatabaseHelper;
import com.example.clement.emm_project2.database.CategoryDatabaseHelper;
import com.example.clement.emm_project2.database.DatabaseHelper;
import com.example.clement.emm_project2.model.AppData;
import com.example.clement.emm_project2.model.Author;
import com.example.clement.emm_project2.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 10/08/15.
 */
public class DataAccess {

    private final String TAG = DataAccess.class.getSimpleName();

    private Context context;

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataAccess(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Category createCategory(Category category) {
        // TODO Refactor with contentProvider.insert
        ContentValues values = new ContentValues();

        values.put(CategoryDatabaseHelper.COLUMN_TITLE, category.getTitle());
        values.put(CategoryDatabaseHelper.COLUMN_DESCRIPTION, category.getDescription());
        values.put(CategoryDatabaseHelper.COLUMN_ACTIVE, category.getActive());
        values.put(CategoryDatabaseHelper.COLUMN_IMAGEURL, category.getImageURL());
        /*values.put(CategoryDatabaseHelper.COLUMN_SUBCATEGORIES, category.getSubCategories());*/

        long insertId = database.insert(CategoryDatabaseHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(CategoryDatabaseHelper.TABLE_NAME,
                CategoryDatabaseHelper.ALL_COLUMNS, CategoryDatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();
        return newCategory;
    }

    public Author createAuthor(Author author) {
        ContentValues values = new ContentValues();

        values.put(AuthorDatabaseHelper.COLUMN_MONGOID, author.getMongoID());
        values.put(AuthorDatabaseHelper.COLUMN_FULLNAME, author.getFullname());
        values.put(AuthorDatabaseHelper.COLUMN_LINK, author.getLink());

        Uri uri = context.getContentResolver().insert(
                AppContentProvider.CONTENT_URI_AUTHORS,
                values
        );

        Cursor cursor = context.getContentResolver().query(
            Uri.parse(AppContentProvider.CONTENT_URI_AUTHORS + "/#" + uri.getLastPathSegment()),
            AuthorDatabaseHelper.ALL_COLUMNS,
            null,
            null,
            null
        );

        cursor.moveToFirst();
        Author newAuthor = cursorToAuthor(cursor);
        cursor.close();
        return newAuthor;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<Author>();

        // Getting datas from contentProvider
        Cursor cursor = context.getContentResolver().query(
                AppContentProvider.CONTENT_URI_AUTHORS,   // The content URI of the words table
                AuthorDatabaseHelper.ALL_COLUMNS,                        // The columns to return for each row
                null,                     // Selection criteria
                null,                     // Selection criteria
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Author author = cursorToAuthor(cursor);
            authors.add(author);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return authors;
    }


    private Author cursorToAuthor(Cursor cursor) {
        Author author = new Author();
        author.setId(cursor.getLong(0));
        author.setFullname(cursor.getString(1));
        author.setLink(cursor.getString(2));
        return author;
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setTid(cursor.getInt(1));
        category.setTitle(cursor.getString(2));
        category.setDescription(cursor.getString(3));
        category.setImageURL(cursor.getString(4));
        category.setActive(cursor.getInt(5) == 0 ? false : true);
        // TODO subCategories

        return category;
    }
}
