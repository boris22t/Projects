package com.example.boris.extralettuce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.boris.extralettuce.support.Typefaces;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends Activity {
    protected EditText userName;
    protected EditText userPassword;
    protected Button registerButton;
    protected TextView registerTitle;
    protected TextView registerInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.usernameRegisterEditText);
        userPassword = (EditText) findViewById(R.id.passwordRegisterEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerTitle = (TextView) findViewById(R.id.registrationTitleTextView);
        registerInfo = (TextView) findViewById(R.id.registrationInfoTextView);

        registerTitle.setTypeface(Typefaces.yeahPapa(this));
        //registerInfo.setTypeface(Typefaces.yeahPapa(this));
        //Listen to register button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Success", "CLICKED!");
                final String username = userName.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                //TO DO: implement Verify Passwords


                new AsyncTask<Void, Void, Void>(){

                    class JsonObjectErrorListener implements Response.ErrorListener {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("success", error.getMessage());
                        }
                    }

                    class JsonObjectResponseListener implements Response.Listener<JSONObject> {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(!response.has("errors")) {
                                    SharedPreferences preferences = RegisterActivity.this.getSharedPreferences(Preferences.PREF_NAME, Context.MODE_PRIVATE);
                                    preferences.edit().putString(Preferences.TOKEN, response.getString("token"));
                                    Log.d("success", response.getString("token"));
                                } else {
                                    Log.d("success", "status code: " + response.getString("errors"));
                                }
                            } catch(Exception e) {
                                Log.d("success", e.getMessage());
                            }
                        }
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        Intent returnHome = new Intent(RegisterActivity.this, LinkActivity.class);
                        RegisterActivity.this.startActivity(returnHome);
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        Log.d("Succes", "In Background...");
                        HashMap<String, String> payload = new HashMap<String, String>();
                        payload.put("username", username);
                        payload.put("password", password);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Endpoints.BASE_URL+"/create/", new JSONObject(payload),
                                new JsonObjectResponseListener(), new JsonObjectErrorListener());
                        Volley.newRequestQueue(RegisterActivity.this).add(request);
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}