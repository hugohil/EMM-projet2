package com.example.clement.emm_project2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.FormationsActivity;
import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.model.AppData;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.example.clement.emm_project2.model.SubCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private static List<Formation> favoritesFormations = new ArrayList<Formation>();
    private DataAccess da = new DataAccess(App.getAppContext());

    public static void registerDataIdInCache(AppData data) {
        if(data != null) {
            Context context = App.getAppContext();
            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                    Context.MODE_PRIVATE);
            Set<String> loadedIds = sharedPref.getStringSet(data.getClass().getSimpleName(), null);
            if(loadedIds == null) {
                loadedIds = new HashSet<String>();
            } else if(loadedIds.contains(data.getMongoID())) {
                Log.d(TAG, data.getClass().getSimpleName() + " with id "+ data.getMongoID()  +"("+ loadedIds.toString()+")");
                throw new RuntimeException("Data id is already present in cache !!!");
            }
            loadedIds.add(data.getMongoID());

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(data.getClass().getSimpleName(), loadedIds);
            editor.commit();
        } else {
            Log.wtf(TAG, "DATA IS NULL !!!");
        }
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

    public void initFavoriteFormation(){
        favoritesFormations.clear();
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoritesIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        if(favoritesIDs.size() < 1) {
            Log.d(TAG, "No favorite formation present in cache, cannot register.");
            return;
        }

        for(String id : favoritesIDs){
            Log.d(TAG, "id: "+id);
            List<Formation> fav = da.findDataWhere(Formation.class, "ean", id);
            if(fav.size() > 0){
                Log.d(TAG, "title: "+fav.get(0).getTitle());
                favoritesFormations.add(fav.get(0));
            }
        }
        Log.d(TAG, "all formations: " + favoritesFormations.toString());
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

    public static void addFavoriteFormation(String formationID){
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoritesIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        if(favoritesIDs == null) {
            favoritesIDs = new HashSet<String>();
        } else if(favoritesIDs.contains(formationID)) {
            Log.d(TAG, "Data id is already present in cache !!!");
            return;
        }
        favoritesIDs.add(formationID);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.favoritesFormations), favoritesIDs);
        editor.apply();
        Log.d(TAG, favoritesIDs.toString());
    }

    public void removeFavoriteFormation(String formationID) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoritesIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        if(favoritesIDs.size() < 1) {
            Log.d(TAG, "Data id is not present in cache, cannot remove.");
            return;
        }
        if(favoritesIDs.contains(formationID)) {
            favoritesIDs.remove(formationID);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.favoritesFormations), favoritesIDs);
        editor.apply();
        Log.d(TAG, favoritesIDs.toString());
    }

    public void addStartedVideo(String IDs){
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.started_videos),
                Context.MODE_PRIVATE);
        HashSet<String> itemIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.started_videos), new HashSet<String>()));
        if(itemIDs == null) {
            itemIDs = new HashSet<String>();
        } else if(itemIDs.contains(IDs)) {
            Log.d(TAG, "Data id is already present in cache !!!");
            return;
        }
        itemIDs.add(IDs);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.started_videos), itemIDs);
        editor.apply();
        Log.d(TAG, itemIDs.toString());
    }

    public void removeStartedID(String IDs) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.started_videos),
                Context.MODE_PRIVATE);
        HashSet<String> itemIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.started_videos), new HashSet<String>()));
        if(itemIDs.size() < 1) {
            Log.d(TAG, "Data id is not present in cache, cannot remove.");
            return;
        }
        if(itemIDs.contains(IDs)) {
            itemIDs.remove(IDs);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.started_videos), itemIDs);
        editor.apply();
        Log.d(TAG, itemIDs.toString());
    }

    public List<Item> getStartedVideos(){
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.started_videos),
                Context.MODE_PRIVATE);
        List<Item> startedVideos = new ArrayList<Item>();
        HashSet<String> itemIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.started_videos), new HashSet<String>()));
        if(itemIDs.size() < 1) {
            Log.d(TAG, "No started videos present in cache, cannot register.");
            return startedVideos;
        }

        for(String id : itemIDs){
            Log.d(TAG, "id: "+id);
            String[] ids = id.split(",");
            Log.d(TAG, "ids: "+ids.toString());
            String formationID = ids[0];
            String itemID = ids[1];
            List<Formation> formz = da.findDataWhere(Formation.class, "ean", formationID);
            Log.d(TAG, "formz: "+formz.toString());
            if(formz.size() > 0){
                for (Item i : formz.get(0).getItems()){
                    if(i.getMongoID() == itemID){
                        Log.d(TAG, "title: "+i.getTitle());
                        startedVideos.add(i);
                    }
                }
            }
        }
        Log.d(TAG, "all videos: " + startedVideos.toString());
        return startedVideos;
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
        favoritesFormations.clear();
    }

    public static boolean areFavoriteFormations() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoriteIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        return favoriteIDs.size() > 0;
    }

    public List<Formation> getFavoritesFormations() {
        Log.d(TAG, favoritesFormations.toString());
        return favoritesFormations;
    }

    public boolean isFormationFavorited(String formationID) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoriteIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        return favoriteIDs.contains(formationID);
    }
}
