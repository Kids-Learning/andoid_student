package com.example.kids_learning;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    ImageView btn;
    EditText uname;
    EditText password;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       btn=(ImageView) findViewById(R.id.imageView4);
       uname=(EditText)findViewById(R.id.editText);
       password=(EditText)findViewById(R.id.editText2);
       btn.setOnClickListener(this);
       builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {
        String username=uname.getText().toString();
        if(uname.length()==0)
        {
            uname.setError("Please Enter your Username");
        }
        String pass=password.getText().toString();
        if (pass.length()==0)
        {
            password.setError("Please Enter Password");
        }
        else {
// From and File
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


            String hu = sh.getString("ip", "");

            String url = "http://" + hu + ":5000//login_post_student";

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            // response
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                    String type = jsonObj.getString("type");
                                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("lid", jsonObj.getString("log_id"));
                                    ed.commit();


                                    if (type.equalsIgnoreCase("student")) {
                                        Intent ln = new Intent(getApplicationContext(), HomePage.class);
                                        startActivity(ln);
                                    } else {
                                        Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                // }
                                else {
//                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();

                                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

                                    //Setting message manually and performing action on button click
                                    builder.setMessage("Please Enter Correct Username or Password")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    finish();
                                                    Toast.makeText(getApplicationContext(), "",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent ii = new Intent(getApplicationContext(), Login.class);
                                                    startActivity(ii);
                                                }
                                            });
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                //  Action for 'NO' Button
//                                                dialog.cancel();
//                                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                        Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle("Error");
                                    alert.show();

                                }

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("username", username);
                    params.put("password", pass);

                    return params;
                }
            };

            int MY_SOCKET_TIMEOUT_MS = 100000;

            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);

        }

    }
}