package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class ViewExamResult extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ListView examresult;
    String [] subject,date,ex_id;

    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_result);
        examresult=(ListView) findViewById(R.id.examresult);
        examresult.setOnItemClickListener(this);


        //
         sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/viewexam_resultt_student";



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

                                JSONArray js= jsonObj.getJSONArray("users");
                                subject=new String[js.length()];
                                date=new String[js.length()];
                                ex_id=new String[js.length()];


//
                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    subject[i]=u.getString("subject");
                                    date[i]=u.getString("date");
                                    ex_id[i]=u.getString("ex_id");




                                }
//

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                                examresult.setAdapter(new custom_examresult(getApplicationContext(),subject,date));
                                // l1.setAdapter(new Custom(getApplicationContext(),gamecode,name,type,discription,image,status));
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

//                String id=sh.getString("uid","");
//                params.put("uid",id);
                params.put("lid",sh.getString("lid",""));

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

//    @Override
//    public void onClick(View view) {
//
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences.Editor ed=sh.edit();
        ed.putString("eid",ex_id[i]);
        ed.commit();

        Intent ln=new Intent(getApplicationContext(),ViewResult.class);


        startActivity(ln);
    }
}