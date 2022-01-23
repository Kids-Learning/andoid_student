package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewStudent extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    TextView name;
    TextView age;
    TextView gender;
    TextView std;
    TextView phone;
    TextView backhome;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ln=new Intent(getApplicationContext(),HomePage.class);
        startActivity(ln);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        img=(ImageView) findViewById(R.id.photostudent);
        name=(TextView)findViewById(R.id.name);
        age=(TextView)findViewById(R.id.age);
        gender=(TextView)findViewById(R.id.gender);
        std=(TextView)findViewById(R.id.std);
        phone=(TextView)findViewById(R.id.phone);
        backhome=(TextView)findViewById(R.id.backhome);
        backhome.setOnClickListener(this);
//  from and file
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");

        String url = "http://" + hu + ":5000/viewstudent_android";
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

                            String name22=jsonObj.getString("name");
                            name.setText(name22);
                            String phone22=jsonObj.getString("phone");
                            phone.setText(phone22);
                            String photo22= jsonObj.getString("photo");

                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip=sh.getString("ip","");

                                String url="http://" + ip + ":5000"+photo22;


                                Picasso.with(getApplicationContext()).load(url). into(img);
                            String age22= jsonObj.getString("age");
                            age.setText(age22);
                            String gender22= jsonObj.getString("gender");
                            gender.setText(gender22);
                            String standered22= jsonObj.getString("standered");
                            std.setText(standered22);

                            }

                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
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



                String id=sh.getString("lid","");
                params.put("lid",id);
//                params.put("mac",maclis);

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);



    }

    @Override
    public void onClick(View view) {
        Intent ln=new Intent(getApplicationContext(),HomePage.class);
        startActivity(ln);
    }
}