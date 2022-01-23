package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

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

public class ObjectView extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listobject;
    String[] ob_id,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_view);
        listobject=(ListView)findViewById(R.id.listobject) ;


        listobject.setOnItemClickListener(this);


        //From "and" File
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/viewobject_student";



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

                                JSONArray js= jsonObj.getJSONArray("data");
                                ob_id=new String[js.length()];

                                photo=new String[js.length()];


//
                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    ob_id[i]=u.getString("ob_id");

                                    photo[i]=u.getString("photo");




                                }
//

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                                listobject.setAdapter(new CustomViewObject(getApplicationContext(),photo));
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();

        ed.putString("path",photo[i]);
        ed.putString("ob_id",ob_id[i]);
        ed.commit();

    }


}