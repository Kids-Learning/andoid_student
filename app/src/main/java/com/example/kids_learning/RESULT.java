package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class RESULT extends AppCompatActivity implements View.OnClickListener {

    ImageView im;
    Button b;
    //TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        im = (ImageView) findViewById(R.id.imageView10);
        b = (Button)findViewById(R.id.button42) ;
        b.setOnClickListener(this);
       // tv = (TextView) findViewById(R.id.textView19);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");
        final String url = "http://" + ip + ":5000/addhandwriting_student";


        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {


                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                try {
                    JSONObject json = new JSONObject(s);
                    String res = json.getString("status");

                    if (res.equals("ok") == true) {


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getApplicationContext(), "Error........", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String type = sh.getString("Utype", "");
                String mark = sh.getString("mark", "");
                String time = sh.getString("time", "");
                String cid = sh.getString("lid", "");
//                if (type.equalsIgnoreCase("speak")) {
//                    params.put("type", type);
//                    params.put("mark", mark);
//                    params.put("cid", cid);
//                } else if (type.equalsIgnoreCase("write")) {
//                    params.put("type", type);
//                    params.put("mark", mark);
//                    params.put("time", time);
//                    params.put("cid", cid);
//                } else if (type.equalsIgnoreCase("jigsaw")) {
//                    params.put("type", type);
//                    params.put("time", time);
//                    params.put("cid", cid);
//
//                } else if (type.equalsIgnoreCase("word")) {
                    params.put("type", type);
                    params.put("time", time);
                    params.put("mark", mark);
                    params.put("lid", cid);
//                } else if (type.equalsIgnoreCase("number")) {
//                    params.put("type", type);
//                    params.put("time", time);
//                    params.put("cid", cid);
//                }
                return (params);

            }
        };
        requestqueue.add(postrequest);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}