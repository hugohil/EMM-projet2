package com.example.clement.emm_project2.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.AppData;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Clement on 12/08/15.
 */
public class SharedPrefUtil {

    public static void registerDataInCache(AppData data, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(data.getClass().getSimpleName(), null);
        if(loadedIds == null) {
            loadedIds = new HashSet<String>();
        } else if(loadedIds.contains(data.getMongoID())) {
            throw new RuntimeException("Data id is already present in cache !!!");
        }
        loadedIds.add(data.getMongoID());

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(data.getClass().getSimpleName(), loadedIds);
    }

    public static boolean isDataInCache(AppData data, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(data.getClass().getSimpleName(), null);
        if(loadedIds == null || !loadedIds.contains(data.getMongoID())) {
            return false;
        }
        return true;
    }

    public static boolean areCategoriesInCache(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(context.getString(R.string.category), null);
        if(loadedIds == null) {
            return false;
        }
        return true;
    }

    public static boolean isSubCategoryInCache(Context context, String id) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(context.getString(R.string.subCategory), null);
        if(loadedIds == null || !loadedIds.contains(id)) {
            return false;
        }
        return true;
    }

}
