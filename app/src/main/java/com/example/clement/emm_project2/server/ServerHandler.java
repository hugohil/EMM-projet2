package com.example.clement.emm_project2.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.clement.emm_project2.R;
import com.example.clement.emm_project2.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
                        Log.d(TAG, error.toString());
                        handler.onError(error.toString());
                    }
                });
        requestQueue.add(jsonArrayReq);
    }
}
