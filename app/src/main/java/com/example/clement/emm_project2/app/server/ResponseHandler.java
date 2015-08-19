package com.example.clement.emm_project2.app.server;

/**
 * Created by perso on 10/08/15.
 */
public interface ResponseHandler {
    void onSuccess(Object datas);
    void onError(String error);
}
