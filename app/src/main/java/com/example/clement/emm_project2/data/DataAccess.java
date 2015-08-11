package com.example.clement.emm_project2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.clement.emm_project2.database.DatabaseHelper;
import com.example.clement.emm_project2.database.SubCategoryDatabaseHelper;
import com.example.clement.emm_project2.model.AppData;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.ReflectUtil;

import java.lang.reflect.Field;

/**
 * Created by Clement on 10/08/15.
 */
public class DataAccess {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private final String TAG = DataAccess.class.getSimpleName();

    public DataAccess(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public AppData createData(AppData data) {
        // 1. Build ContentValues
        ContentValues values = new ContentValues();

        Field[] fields = ReflectUtil.getObjectFields(data);
        String[] fieldNames = new String[fields.length + 2];
        fieldNames[0] = "id";
        fieldNames[1] = "mongoid";
        for(int i = 0; i < fields.length; i++) {
            Object fieldValue = ReflectUtil.getObjectFieldValue(data, fields[i]);
            String fieldName = fields[i].getName();
            fieldNames[i + 2] = fieldName;
            if(fieldValue != null) {
                // We need to handle special case here like fieldValue type or some restricted fields
                values.put(fieldName, fieldValue.toString());
            }
        }
        values.put("mongoid", data.getMongoID());

        // 2. Insert in DB
        String tableName = ReflectUtil.getDatabaseTableName(data);
        long insertId = database.insert(tableName, null,
                values);
        Log.d(TAG, "Inserted data "+data.toString()+ " id= "+insertId);

        // 3. Get & return created data
        Cursor cursor = database.query(tableName,
                fieldNames, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        if(cursor != null) {
            data = cursorToData(cursor, data.getClass());
        }
        return data;
    }

    public void createSubCat(SubCategory sub, String catID){
        ContentValues values = new ContentValues();

        values.put(SubCategoryDatabaseHelper.COLUMN_MONGOID, sub.getMongoID());
        values.put(SubCategoryDatabaseHelper.COLUMN_TITLE, sub.getTitle());
        values.put(SubCategoryDatabaseHelper.COLUMN_DESCRIPTION, sub.getDescription());
        values.put(SubCategoryDatabaseHelper.COLUMN_ACTIVE, sub.getActive());
        values.put(SubCategoryDatabaseHelper.COLUMN_IMAGEURL, sub.getImageURL());
        values.put(SubCategoryDatabaseHelper.COLUMN_CATID, catID);

        long insertId = database.insert(SubCategoryDatabaseHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(SubCategoryDatabaseHelper.TABLE_NAME,
                SubCategoryDatabaseHelper.ALL_COLUMNS, SubCategoryDatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.close();

        Log.d(TAG, "subcat saved in DB.");
    }


    /*public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<Author>();

        Cursor cursor = database.query(AuthorDatabaseHelper.TABLE_NAME,
                AuthorDatabaseHelper.ALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Author author = cursorToData(cursor);
            authors.add(author);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return authors;
    }*/

    private AppData cursorToData(Cursor cursor, Class c) {
        AppData data = null;
        try {
           data = (AppData)c.newInstance();
        } catch( Exception e) {
            Log.d(TAG, "Cannot instanciate class " + c.getSimpleName().toString());
        }
        Log.d(TAG, "Column count -> "+cursor.getColumnCount());
        Field[] fields = ReflectUtil.getObjectFields(data);
        data.setId(cursor.getInt(0));
        data.setMongoID(cursor.getString(1));
        for(int i = 0; i< fields.length; i++) {
            Field f = fields[i];
            Object fieldValue = null;
            switch(cursor.getType(i + 2)) {
                case Cursor.FIELD_TYPE_BLOB:
                    fieldValue = cursor.getBlob(i + 2);
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    fieldValue = cursor.getFloat(i + 2);
                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    fieldValue = cursor.getInt(i + 2);
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    fieldValue = cursor.getString(i + 2);
                    break;
                case Cursor.FIELD_TYPE_NULL:
                    break;

            }
            ReflectUtil.setObjectFieldValue(data, f, fieldValue);
        }
        return data;
    }
}
