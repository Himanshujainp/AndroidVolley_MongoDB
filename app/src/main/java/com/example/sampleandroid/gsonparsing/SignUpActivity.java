package com.example.sampleandroid.gsonparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    public static final String url = "https://api.mlab.com/api/1/databases/pms/collections/Login?apiKey=arV9Nv7Yf97VFl6q5OCAV7qCy_rNarxy";

    Button signup;
    EditText s_firstName, s_lastName, s_password;
    TextView textView;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        requestQueue = Volley.newRequestQueue(this);

        s_firstName = (EditText)findViewById(R.id.s_firstName);
        s_lastName = (EditText)findViewById(R.id.s_lastName);
        s_password = (EditText)findViewById(R.id.s_password);
        signup = (Button)findViewById(R.id.signup);
        textView = (TextView)findViewById(R.id.textView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObject jsonObject = new JsonObject();

                final Map<String, String> jsonParse = new HashMap<String, String>();
                jsonParse.put("first_name", s_firstName.getText().toString());
                jsonParse.put("last_name", s_lastName.getText().toString());
                jsonParse.put("password", s_password.getText().toString());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url,

                        new JSONObject(jsonParse),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("JSONPost", response.toString());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                textView.setText("Issues Uploading!!!");
                            }
                        }) {

//                    @Override
//                    protected Map<String,String> getParams(){
//                        Map<String,String> params = new HashMap<String, String>();
//                        params.put("first_name", s_firstName.getText().toString());
//                        params.put("last_name", s_lastName.getText().toString());
//                        params.put("password", s_password.getText().toString());
//                        return params;
//                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        // Removed this line if you dont need it or Use application/json
                        //params.put("Content-Type", "application/json");
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
