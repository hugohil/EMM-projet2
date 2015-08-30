package com.example.clement.emm_project2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.model.AppData;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Clement on 12/08/15.
 * This class help us know which data has been synchronized
 * Since we are running sync in a transaction, plenty of methods here are useless : either all datas are here
 * or nothing has been synchronized
 * TODO : refactor this class (trash useless methods) and set an unique variable indicating if datas are synced
 */
public class SharedPrefUtil {

    private final static String TAG = SharedPrefUtil.class.getSimpleName();

    public static void registerDataIdInCache(AppData data) {
        Context context = App.getAppContext();
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
        editor.commit();
    }

    public static void registerFormationsOfSubcategory(String subCatId) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedSubCatFormations = sharedPref.getStringSet(context.getString(R.string.formations_loaded_subcat), null);
        if(loadedSubCatFormations == null) {
            loadedSubCatFormations = new HashSet<String>();
        } else if(loadedSubCatFormations.contains(subCatId)) {
            throw new RuntimeException("Data id is already present in cache !!!");
        }
        loadedSubCatFormations.add(subCatId);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.formations_loaded_subcat), loadedSubCatFormations);
        editor.commit();
    }

    public static boolean isDataInCache(AppData data) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(data.getClass().getSimpleName(), null);
        if(loadedIds == null || !loadedIds.contains(data.getMongoID())) {
            return false;
        }
        return true;
    }

    public static boolean areCategoriesInCache() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(context.getString(R.string.category), null);
        if(loadedIds == null) {
            return false;
        }
        return true;
    }

    public static void addFavoriteFormation(String className, String formationID){
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        Set<String> favoritesIDs = sharedPref.getStringSet(className, null);
        if(favoritesIDs == null) {
            favoritesIDs = new HashSet<String>();
        } else if(favoritesIDs.contains(formationID)) {
            Log.d(TAG, "Data id is already present in cache !!!");
            return;
        }
        favoritesIDs.add(formationID);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.apply();
        Log.d(TAG, favoritesIDs.toString());
    }

    public void removeFavoriteFormation(String className, String formationID) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        Set<String> favoritesIDs = sharedPref.getStringSet(className, null);
        if(favoritesIDs == null) {
            Log.d(TAG, "Data id is not present in cache, cannot remove.");
            return;
        }
        if(favoritesIDs.contains(formationID)) {
            favoritesIDs.remove(formationID);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(className, favoritesIDs);
        editor.apply();
        Log.d(TAG, favoritesIDs.toString());
    }

    public static boolean areFormationsOfSubCategoryInCache(String id) {
        // TODO: check here if formations of the subcategory have been loaded...
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        Set<String> loadedIds = sharedPref.getStringSet(context.getString(R.string.formations_loaded_subcat), null);
        if(loadedIds == null || !loadedIds.contains(id)) {
            return false;
        }
        return true;
    }

    public static void clearAllDataInCache() {
        Context context = App.getAppContext();
        context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE).edit().clear().commit();
    }
}
