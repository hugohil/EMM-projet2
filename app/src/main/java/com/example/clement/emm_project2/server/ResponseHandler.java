package com.example.clement.emm_project2.server;

/**
 * Created by perso on 10/08/15.
 */
public interface ResponseHandler {
    void onSuccess(String datas);
    void onError(String error);
}
