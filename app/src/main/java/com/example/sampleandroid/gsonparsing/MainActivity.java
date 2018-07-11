package com.example.sampleandroid.gsonparsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    Button l_login, l_signup;
    EditText l_u_name, l_password;
    String data = "", parsedData = "";
    public static final String url = "https://api.mlab.com/api/1/databases/pms/collections/Login?apiKey=arV9Nv7Yf97VFl6q5OCAV7qCy_rNarxy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView mTextView = (TextView)findViewById(R.id.textViewResult);

        l_login = (Button)findViewById(R.id.l_login);
        l_u_name = (EditText)findViewById(R.id.l_uname);
        l_password = (EditText)findViewById(R.id.l_password);
        l_signup = (Button)findViewById(R.id.l_signup);

        final RequestQueue queue = Volley.newRequestQueue(this);

        l_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONArray jsonArray = new JSONArray(response);

                                    for(int i = 0; i<jsonArray.length(); i++) {
                                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
//                                        data =  "First Name: " +  jsonObject.get("first_name") + "\n" +
//                                                "Second Name: " + jsonObject.get("last_name") + "\n" +
//                                                "Password: " + jsonObject.get("password") + "\n";
//                                        parsedData = parsedData + data + "\n";

                                        if(((jsonObject.get("first_name").toString()).equals(l_u_name.getText().toString())) &&
                                                ((jsonObject.get("password").toString()).equals(l_password.getText().toString()))){

                                            parsedData = "Login Success!!!";
                                            Log.i(TAG, "Response: " + response.substring(0));
                                        }else{
                                            Log.i(TAG, "Invalid Login!!!");
                                        }


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mTextView.setText(parsedData);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That did not work!!!");
                    }
                });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
            }
        });
        l_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(next);
            }
        });
    }
}
