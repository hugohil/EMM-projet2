package com.example.clement.emm_project2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.clement.emm_project2.data.database.DatabaseHelper;
import com.example.clement.emm_project2.model.AppData;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.util.ReflectUtil;
import com.example.clement.emm_project2.util.SharedPrefUtil;
import com.example.clement.emm_project2.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class DataAccess {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private final String TAG = DataAccess.class.getSimpleName();
    private Context context;

    public DataAccess(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static byte[] serializeObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();

            return buf;
        } catch (IOException ioe) {
            Log.e("serializeObject", "error", ioe);

            return null;
        }
    }

    public static Object deserializeObject(byte[] b) {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            Object object = in.readObject();
            in.close();

            return object;
        } catch (ClassNotFoundException cnfe) {
            Log.e("deserializeObject", "class not found error", cnfe);
            return null;
        } catch (IOException ioe) {
            Log.e("deserializeObject", "io error", ioe);
            return null;
        }
    }

    public <T extends AppData> T createData(AppData data) {
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
                if((Collection.class.isAssignableFrom(fieldValue.getClass()) || Map.class.isAssignableFrom(fieldValue.getClass()))) {
                    values.put(fieldName, serializeObject(fieldValue));
                } else {
                    values.put(fieldName, fieldValue.toString());
                }
            }
        }

        // Formations do not have a mongoId : suuuucks ! We loose generic typing
        if(data.getClass().equals(Formation.class)) {
            values.put("mongoid", ((Formation)data).getEan());
        } else {
            values.put("mongoid", data.getMongoID());
        }
//        Log.d(TAG, values.toString());

        // 2. Insert in DB
        String tableName = ReflectUtil.getDatabaseTableName(data);
        long insertId = database.insert(tableName, null,
                values);
        // Log.d(TAG, "Inserted data "+data.toString()+ " id= "+insertId);


        // 3. Get & return created data
        Cursor cursor = database.query(tableName,
                fieldNames, "id = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        if(cursor != null) {
            data = cursorToData(cursor, data.getClass());
        }
        return (T)data;
    }

    public <T extends AppData> ArrayList<T> getAllDatas(Class c) {
        this.open();
        ArrayList<T> datas = new ArrayList<T>();
        T data = null;
        try {
           data = (T)c.newInstance();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        String[] fieldNames = ReflectUtil.getObjectFieldNames(data);

        Cursor cursor = database.query(ReflectUtil.getDatabaseTableName(data),
                fieldNames, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data = cursorToData(cursor, c);
            datas.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return datas;

    }

    private <T extends AppData> T cursorToData(Cursor cursor, Class c) {
        AppData data = null;
        try {
           data = (AppData)c.newInstance();
        } catch( Exception e) {
            Log.d(TAG, "Cannot instanciate class " + c.getSimpleName().toString());
        }
        Field[] fields = ReflectUtil.getObjectFields(data);
        data.setId(cursor.getInt(0));
        data.setMongoID(cursor.getString(1));
        for(int i = 0; i< fields.length; i++) {
            Field f = fields[i];
            Object fieldValue = null;
            switch(cursor.getType(i + 2)) {
                case Cursor.FIELD_TYPE_BLOB:
                    fieldValue = deserializeObject(cursor.getBlob(i + 2));
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
        return (T)data;
    }

    public <T extends AppData> T getDataById(Class c, Long id) {
        this.open();
        T data = null;
        try {
            data = (T)c.newInstance();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        String[] fieldNames = ReflectUtil.getObjectFieldNames(data);
        String tableName = ReflectUtil.getDatabaseTableName(data);
        Cursor cursor = database.query(tableName,
                fieldNames, "id = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        if(cursor != null) {
            data = cursorToData(cursor, data.getClass());
        }
        this.close();
        return (T)data;
    }

    public <T extends AppData> List<T> findDataWhere(Class c, String... args) {
        if(args.length % 2 != 0) {
            throw new IllegalArgumentException("Arguments of findDataWhere must be a pair number ! (property, value)");
        } else {
            this.open();
            List<T> datas = new ArrayList<T>();
            T data = null;
            try {
                data = (T)c.newInstance();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ReflectUtil.getDatabaseTableName(data));

            for(int i = 0; i < args.length; i = i+2) {
                queryBuilder.appendWhere(args[i]+ " = \""+args[i+1] + "\"");
            }
            String[] fieldNames = ReflectUtil.getObjectFieldNames(data);
            Cursor cursor = queryBuilder.query(database, fieldNames,null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                data = cursorToData(cursor, c);
                datas.add(data);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            this.close();
            return datas;
        }
    }
}
