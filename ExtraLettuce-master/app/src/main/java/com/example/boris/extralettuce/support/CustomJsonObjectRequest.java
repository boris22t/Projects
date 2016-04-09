package com.example.boris.extralettuce.support;


import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.boris.extralettuce.Preferences;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rushi on 1/17/16.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest
{
    Context c;
    public CustomJsonObjectRequest(Context c, int method, String url, JSONObject jsonRequest,Listener listener, ErrorListener errorListener)
    {
        super(method, url, jsonRequest, listener, errorListener);
        this.c = c;
    }

    @Override
    public Map<String,String> getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        SharedPreferences pref = c.getSharedPreferences(Preferences.PREF_NAME, Context.MODE_PRIVATE);
        //headers.put("token", pref.getString(Preferences.TOKEN, ""));
        headers.put("Authorization","Token de3e5111fdcb141f925e30d5aef117f2873482c1");
        return headers;
    }

}