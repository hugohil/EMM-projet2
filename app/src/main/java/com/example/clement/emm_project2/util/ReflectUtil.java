package com.example.clement.emm_project2.util;

import android.util.Log;

import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.Formation;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
            m = o.getClass().getDeclaredMethod("get" + StringUtil.capitalize(f.getName()));
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
        boolean isFieldList = false;
        boolean isFieldMap = false;
        try {
            if(o.getClass().equals(Formation.class)) {
                m = o.getClass().getDeclaredMethod("set" + StringUtil.capitalize(field.getName()), field.getType());
            } else {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    // Field type is a List<T>, we need to look for the specific getter
                    m = o.getClass().getDeclaredMethod("set" + StringUtil.capitalize(field.getName()), TypeReference.class, String.class);
                    isFieldList = true;
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    // Field type is a Map<T>, we need to look for the specific getter
                    m = o.getClass().getDeclaredMethod("set" + StringUtil.capitalize(field.getName()), TypeReference.class, String.class);
                    isFieldMap = true;
                } else {
                    m = o.getClass().getDeclaredMethod("set" + StringUtil.capitalize(field.getName()), field.getType());
                }
            }
        } catch(NoSuchMethodException e) {
           Log.e(TAG, "error while trying to get the property '"
                    + field.getName()
                    + "' setter, maybe the setter name is invalid... \n => "
                    + e.getMessage());
        }
        if(m != null) {
            if(!m.isAccessible()) {
                m.setAccessible(true);
            }
            try {
                if(m.getParameterTypes()[0].equals(boolean.class)) {
                    fieldValue = fieldValue == "true" ? true : false;
                } else if (m.getParameterTypes()[0].equals(Float.class)) {
                    fieldValue = (Float)fieldValue;
                }

                if(o.getClass().equals(Formation.class)) {
                    m.invoke(o, fieldValue);
                } else {
                    if(isFieldList) {
                        m.invoke(o, new TypeReference<List<Object>> () {},fieldValue);
                    } else if(isFieldMap) {
                        m.invoke(o, new TypeReference<Map<Object, Object>>() {}, fieldValue);
                    } else {
                        m.invoke(o, fieldValue);
                    }
                }
            } catch(Exception e) {
                Log.e(TAG, "error while trying to invoke setter of property '"
                        + field.getName()
                        +"'\n=> "
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
