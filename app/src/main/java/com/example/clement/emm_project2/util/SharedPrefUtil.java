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
import java.util.Iterator;
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
//    private static List<Formation> favoritesFormations = new ArrayList<Formation>();

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

    public static void initFavoriteFormation(){

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

    public static void removeFavoriteFormation(String formationID) {
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

    public static void addStartedVideo(Formation formation, Item item){
        SharedPrefUtil.registerPendingFormation(formation);
        SharedPrefUtil.registerSeenItem(item);
    }

    public static void registerPendingFormation(Formation formation) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.travel),
                Context.MODE_PRIVATE);
        Set<String> formationIds = sharedPref.getStringSet(context.getString(R.string.pending_videos), new HashSet<String>());
        if(!formationIds.contains(formation.getMongoID())) {
            formationIds.add(formation.getMongoID());
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.pending_videos), formationIds);
        editor.commit();

    }

    public static void registerSeenItem(Item item) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.travel),
                Context.MODE_PRIVATE);
        Set<String> itemIds = sharedPref.getStringSet(context.getString(R.string.seen_items), new HashSet<String>());
        if(!itemIds.contains(item.getMongoID())) {
            itemIds.add(item.getMongoID());
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.seen_items), itemIds);
        editor.commit();
    }

    public static void unregisterSeenItem(Item item) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.travel),
                Context.MODE_PRIVATE);
        Set<String> itemIds = sharedPref.getStringSet(context.getString(R.string.seen_items), new HashSet<String>());
        if(itemIds.contains(item.getMongoID())) {
            Iterator<String> iterator = itemIds.iterator();
            while (iterator.hasNext()) {
                String element = iterator.next();
                if (element.equals(item.getMongoID())) {
                    iterator.remove();
                }
            }
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.seen_items), itemIds);
        editor.commit();
    }

    public static Set<String> getSeenItemIds() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.travel),
                Context.MODE_PRIVATE);
        return sharedPref.getStringSet(context.getString(R.string.seen_items), new HashSet<String>());
    }

    public static Set<String> getPendingFormationIds() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.travel),
                Context.MODE_PRIVATE);
        return sharedPref.getStringSet(context.getString(R.string.pending_videos), new HashSet<String>());
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

    public static boolean areFavoriteFormations() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoriteIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        return favoriteIDs.size() > 0;
    }

    public static List<Formation> getFavoritesFormations() {
        DataAccess da = new DataAccess(App.getAppContext());
        List<Formation> favoritesFormations = new ArrayList<Formation>();
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoritesIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        if(favoritesIDs.size() < 1) {
            Log.d(TAG, "No favorite formation present in cache, cannot register.");
            return null;
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
        return favoritesFormations;
    }

    public static boolean isFormationFavorited(String formationID) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE);
        HashSet<String> favoriteIDs = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.favoritesFormations), new HashSet<String>()));
        return favoriteIDs.contains(formationID);
    }
}
