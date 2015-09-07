package com.example.clement.emm_project2.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clement.emm_project2.app.App;
import com.example.clement.emm_project2.app.server.ResponseHandler;
import com.example.clement.emm_project2.app.server.ServerHandler;
import com.example.clement.emm_project2.data.DataAccess;
import com.example.clement.emm_project2.data.database.DatabaseHelper;
import com.example.clement.emm_project2.model.Category;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.SubCategory;
import com.example.clement.emm_project2.util.JsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 19/08/15.
 * SyncAdapter encapsulates all the data synchronization logic
 * Using this components allows us to run sync periodically, manually, .. etc (usefull for the preferences screen!)
 * The user can disable it via the device configuration, under the account section
 * It uses backoff algorithm in case of failure and manages the synchro in the most efficient way
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = SyncAdapter.class.getSimpleName();
    public static final String SYNC_FINISHED = "doneSync";

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        // Here we need to put all the code related to synchronization
        // We need to clear all datas before synchronizing...
        DatabaseHelper dbHelper = new DatabaseHelper(App.getAppContext());
        dbHelper.dropAllDatas();

        dbHelper.withTransaction(new Runnable() {
                @Override
                public void run() {
                Log.wtf(TAG, "Running Data Synchronization");
                final ServerHandler server = new ServerHandler(App.getAppContext());
                server.getCategories(new ResponseHandler() {
                    @Override
                    public void onSuccess(Object datas) {
                        List<Category> categories = JsonUtil.parseJsonDatas((JSONArray) datas, Category.class);
                        handleCategories(categories);
                    }

                    private void handleCategories(List<Category> categories) {
                        DataAccess dataAccess = new DataAccess(App.getAppContext());
                        dataAccess.open();
                        for (Category category : categories) {
                            dataAccess.createData(category);
                            List<SubCategory> subCategories = category.getSubCategoriesAsList();
                            for (SubCategory subCategory : subCategories) {
                                subCategory.setCatId(category.getMongoID());
                                dataAccess.createData(subCategory);
                            }
                        }
                        dataAccess.close();
                        Log.d(TAG, "Reached last category, launching activity");
                        Log.i(TAG, "App initialized !");
                        Intent i = new Intent(SYNC_FINISHED);
                        App.getAppContext().sendBroadcast(i);
                    }

                    @Override
                    public void onError(String error) {
                        Log.wtf(TAG, error);
                    }
                });
            }
        });

    }
}
