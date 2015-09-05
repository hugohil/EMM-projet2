package com.example.clement.emm_project2.util;

import android.util.Log;

import com.example.clement.emm_project2.activities.SplashScreenActivity;
import com.example.clement.emm_project2.model.AppData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 13/08/15.
 */
public class JsonUtil {
    private final static String TAG = SplashScreenActivity.class.getSimpleName();

    public static <T extends AppData> List<T> parseJsonDatas(JSONArray json, Class c) {
        ObjectMapper mapper = new ObjectMapper();
        List<T> datas = new ArrayList<T>();
        try {
            for (int i = 0; i < json.length(); i++) {
                T data = (T)c.newInstance();
                data = (T) mapper.readValue(json.getJSONObject(i).toString(), data.getClass());
                datas.add(data);
            }
        } catch(Exception e) {
            Log.e(TAG, e.toString());
        }

        return datas;
    }

    public static <T extends AppData> T parseJsonData(JSONObject json, Class c) {
        ObjectMapper mapper = new ObjectMapper();
        T data = null;
        try {
            Log.wtf(TAG, "JSON =>"+json.toString());
            data = (T) mapper.readValue(json.toString(), c);
        } catch(Exception e) {
            Log.e(TAG, e.toString());
        }

        return data;
    }


}
