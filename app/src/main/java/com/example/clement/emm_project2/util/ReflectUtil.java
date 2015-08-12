package com.example.clement.emm_project2.util;

import android.util.Log;

import com.example.clement.emm_project2.data.DataAccess;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Clement on 11/08/15.
 */
public class ReflectUtil {

    private final static String TAG = DataAccess.class.getSimpleName();

    public static Field[] getObjectFields(Object o) {
        Class c = o.getClass();
        return c.getDeclaredFields();
    }

    public static Object getObjectFieldValue(Object o, Field f) {
        Method m = null;
        Object propertyValue = null;
        try {
            m = o.getClass().getMethod("get" + StringUtil.capitalize(f.getName()));
        } catch(Exception e) {
            Log.e(TAG, "error while trying to get the property '"
                    + f.getName()
                    + "' getter, maybe the getter name is invalid... \n=> "
                    + e.getMessage().toString());
        }
        if(m != null) {
            if(!m.isAccessible()) {
                m.setAccessible(true);
            }
            try {
                propertyValue = m.invoke(o);
            } catch(Exception e) {
                Log.e(TAG, "error while trying to invoke getter of property '"
                        + f.getName()
                        +"\n=> "
                        +e.getMessage().toString());
            }
        }

        return propertyValue;
    }

    public static void setObjectFieldValue(Object o, Field field, Object fieldValue) {
        Method m = null;
        try {
            m = o.getClass().getDeclaredMethod("set" + StringUtil.capitalize(field.getName()), field.getType());
        } catch(NoSuchMethodException e) {
           Log.e(TAG, "error while trying to get the property '"
                    + field.getName()
                    + "' getter, maybe the setter name is invalid... \n => "
                    + e.getMessage());
        }
        if(m != null) {
            if(!m.isAccessible()) {
                m.setAccessible(true);
            }
            try {
                if(m.getParameterTypes()[0].equals(boolean.class)) {
                    fieldValue = fieldValue == "true" ? true : false;
                }
                m.invoke(o, fieldValue);
            } catch(Exception e) {
                Log.e(TAG, "error while trying to invoke setter of property '"
                        + field.getName()
                        +"\n=> "
                        +e.getMessage().toString());
            }
        }
    }

    public static Class getFieldClass(Field f) {
        return f.getClass();
    }

    public static String getDatabaseTableName(Object o) {
        return o.getClass().getSimpleName().toLowerCase();
    }

    public static String[] getObjectFieldNames(Object o) {
        Field[] fields = getObjectFields(o);
        String[] fieldNames = new String[fields.length + 2];
        fieldNames[0] = "id";
        fieldNames[1] = "mongoid";
        for(int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            fieldNames[i + 2] = fieldName;
        }
        return fieldNames;
    }


}
