package com.example.juliana.teste;



import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

/**
 * Created by alexdeoliveirasilva on 07/01/17.
 */

public class SingletonNetwork {
    private static SingletonNetwork mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    public static String URLServer = "http://www.wg7sistemas.com.br/alex/juliana/palmadamao/?comando=";


    private SingletonNetwork(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingletonNetwork getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonNetwork(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }
}