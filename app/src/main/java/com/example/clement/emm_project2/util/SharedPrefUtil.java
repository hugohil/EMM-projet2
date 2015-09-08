package com.example.clement.emm_project2.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.activities.FormationsActivity;
import com.example.clement.emm_project2.activities.MainActivity;
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

    public static void registerSyncDone() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.didSychroRunOnce), true);
        editor.commit();
    }

    public static boolean didSynchroRunOnce() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE);
        boolean result = sharedPref.getBoolean(context.getString(R.string.didSychroRunOnce), false);
        return result;
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
        editor.commit();
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
        if(favoritesIDs.isEmpty()) {
            editor.remove(context.getString(R.string.favoritesFormations));
        } else {
            editor.putStringSet(context.getString(R.string.favoritesFormations), favoritesIDs);
        }
        editor.commit();
        Log.d(TAG, favoritesIDs.toString());
    }

    public static void addStartedVideo(Formation formation, Item item) {
        SharedPrefUtil.registerPendingFormation(formation);
        SharedPrefUtil.registerSeenItem(item);
    }

    public static void registerPendingFormation(Formation formation) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pending_videos),
                Context.MODE_PRIVATE);
        Set<String> formationIds = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.pending_videos), new HashSet<String>()));
        if(!formationIds.contains(formation.getMongoID())) {
            formationIds.add(formation.getMongoID());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(context.getString(R.string.pending_videos), formationIds);
            editor.commit();
        }
    }

    public static void unregisterPendingFormation(Formation formation) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pending_videos),
                Context.MODE_PRIVATE);
        Set<String> formationsIds = sharedPref.getStringSet(context.getString(R.string.pending_videos), new HashSet<String>());
        if(formationsIds.contains(formation.getMongoID())) {
            Iterator<String> iterator = formationsIds.iterator();
            while (iterator.hasNext()) {
                String element = iterator.next();
                if (element.equals(formation.getMongoID())) {
                    iterator.remove();
                }
            }
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.pending_videos), formationsIds);
        editor.commit();
    }

    public static void registerSeenItem(Item item) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.seen_items),
                Context.MODE_PRIVATE);
        Set<String> itemIds = new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.seen_items), new HashSet<String>()));
        if(!itemIds.contains(item.getMongoID())) {
            itemIds.add(item.getMongoID());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(context.getString(R.string.seen_items), itemIds);
            editor.commit();
        }
    }

    public static void unregisterSeenItem(Item item) {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.seen_items),
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
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.seen_items),
                Context.MODE_PRIVATE);
        return new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.seen_items), new HashSet<String>()));
    }

    public static Set<String> getPendingFormationIds() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pending_videos),
                Context.MODE_PRIVATE);
        return new HashSet<String>(sharedPref.getStringSet(context.getString(R.string.pending_videos), new HashSet<String>()));
    }

    public static void clearAllDataInCache() {
        Context context = App.getAppContext();
        context.getSharedPreferences(context.getString(R.string.cached_datas),
                Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static boolean areFavoriteFormations() {
        Context context = App.getAppContext();
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.cached_datas),
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

    public static void handleUserPreferencesDelete(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.warning))
                .setMessage(context.getString(R.string.ask_user_pref_delete))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteFavsAndTravel();
                        Toast.makeText(context, context.getString(R.string.successfully_deleted_prefs), Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private static void deleteFavsAndTravel() {
        Context context = App.getAppContext();
        context.getSharedPreferences(context.getString(R.string.favoritesFormations),
                Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences(context.getString(R.string.seen_items),
                Context.MODE_PRIVATE).edit().clear().commit();
        context.getSharedPreferences(context.getString(R.string.pending_videos),
                Context.MODE_PRIVATE).edit().clear().commit();

    }
}
