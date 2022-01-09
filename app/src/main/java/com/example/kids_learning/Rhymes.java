package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rhymes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;

    String [] rscid,filename,path;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ln=new Intent(getApplicationContext(),HomePage.class);
        startActivity(ln);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhymes);

        lv =(ListView)findViewById(R.id.jiglistv);

        lv.setOnItemClickListener(this);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip","");

        String url="http://"+ip+":5000/AndStoryRetrieve";

        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {



                try {

                    JSONObject js=new JSONObject(s);

                    String status=js.getString("status");
                    if(status.equalsIgnoreCase("ok"))
                    {
                        JSONArray jsonarr= js.getJSONArray("res");

                        rscid=new String[jsonarr.length()];
                        filename=new String[jsonarr.length()];
                        path=new String[jsonarr.length()];
//                        rdate=new String[jsonarr.length()];


                        for(int i=0;i<jsonarr.length();i++)
                        {

                            JSONObject jso= jsonarr.getJSONObject(i);

                            rscid[i]= jso.getString("r_id");
                            filename[i]= jso.getString("name");
                            path[i]= jso.getString("file");
//                            rdate[i]= jso.getString("rdate");

                        }

                        ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,filename);

                        lv.setAdapter(adpt);




                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getApplicationContext(), "Error" + volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                return (params);

            }
        };

        requestqueue.add(postrequest);




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


       final String selpos= rscid[position];
       final String name = filename[position];


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip","");

        String url="http://"+ip+":5000//AndRetrieveFileName";

        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {



                try {

                    JSONObject js=new JSONObject(s);

                    String status=js.getString("status");
                    if(status.equalsIgnoreCase("ok"))
                    {
                        String docum = js.getString("doc");

                        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor ed = sh.edit();
                        ed.putString("docum",docum);
                        ed.putString("name",name);
                        ed.commit();

                        Intent i = new Intent(getApplicationContext(), Read_rhymes.class);
                        startActivity(i);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(getApplicationContext(), "Error" + volleyError.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("fid",selpos);


                return (params);

            }
        };

        requestqueue.add(postrequest);


    }
}
