package com.example.clement.emm_project2.app.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by perso on 10/08/15.
 */
public class ServerHandler {
    private Context context;
    private static ServerHandler instance;
    private final String TAG = ServerHandler.class.getSimpleName();

    public static final String API_BASE_URL = "http://eas.elephorm.com/api/v1/";

    public ServerHandler(Context context){
        this.context = context;
    }

    public static synchronized ServerHandler getInstance(Context context){
        if(instance == null){
            instance = new ServerHandler(context);
        }
        return instance;
    }

    public void getCategories(final ResponseHandler handler){
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET,
                API_BASE_URL + "categories",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        handler.onError(error.toString());
                    }
                });
        requestQueue.add(jsonArrayReq);
    }

    public void getFormations(final String subCatId, final ResponseHandler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET,
                API_BASE_URL + "subcategories/" + subCatId + "/trainings",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf(TAG, "Error getting formations for " +subCatId);
                        Log.d(TAG, error.toString());
                        handler.onError(error.toString());
                    }
                });
        // Here we can have TimeOut error so let's set the timeout manually
        jsonArrayReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayReq);
    }

    public void getFormation(final String eanCode, final ResponseHandler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_BASE_URL + "trainings/" + eanCode,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf(TAG, "Error getting formation for ean code" +eanCode);
                        Log.d(TAG, error.toString());
                        handler.onError(error.toString());
                    }
                });
        // Here we can have TimeOut error so let's set the timeout manually
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}
