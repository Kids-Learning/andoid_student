package com.example.kids_learning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Puzzle_attend extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnClickListener {
    GridView gv;
    Handler h;
    String aa[];
    CustomGrid customGrid;
    Button b1;
    String cmr_rst="1,5,9,13,2,6,10,14,3,7,11,15,4,8,12,16,";
    String res_chk="";
    TextView t;
    ImageView i8;

    int scount=0,mcount=0;




    Runnable timer = new Runnable() {
        @Override
        public void run() {




            if(scount < 60){
                scount=scount+1;

            }
            else{
                scount = 00;
                mcount = mcount+1;
            }

            t.setText(mcount+" : "+scount);


            h.postDelayed(timer,1000);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_attend);

       t =(TextView)findViewById(R.id.textView5);
        h=new Handler();

        h.post(timer);


        i8 =(ImageView)findViewById(R.id.imageView8);
        b1 =(Button)findViewById(R.id.button3);
        b1.setOnClickListener(this);

        //Toast.makeText(getApplicationContext(),"name999",Toast.LENGTH_LONG).show();
        gv=(GridView)findViewById(R.id.gd1);
        gv.setOnItemClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip=sh.getString("ip", "");
        String url = "http://" + ip + ":5000/view_acc_img";
        String url1="http://" + ip + ":5000"+ sh.getString("path","");
        Picasso.with(getApplicationContext()).load(url1).into(i8);

        //Toast.makeText(getApplicationContext(),"url="+url,Toast.LENGTH_LONG).show();

        try {

            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }catch (Exception e){}

        gv.setOnItemLongClickListener(this);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //    Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs=   jsonObj.getString("status");
                            if(sucs.equalsIgnoreCase("ok"))
                            {

                                String jsa=jsonObj.getString("fname");
                                aa=jsa.split(",");
                                gv.setAdapter(new CustomGrid(getApplicationContext(),aa));
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"error!"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(),"error!"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String>  params = new HashMap<String, String>();


               // params.put("pname", sh.getString("uid",""));


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(postRequest);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
       // Toast.makeText(this, "clicked @ "+i, Toast.LENGTH_SHORT).show();
        return false;
    }
    int cnt=0;
    int ps1=0,ps2=0;
    String va1="",va2="";


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LinearLayout ll=(LinearLayout)view;

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(1, Color.BLACK);
        drawable.setCornerRadius(2);
        drawable.setColor(Color.BLUE);
        ll.setBackgroundDrawable(drawable);


        String ve=aa[i];
        String temp="";


        cnt=cnt+1;


       // Toast.makeText(getApplicationContext(),cmr_rst+"-"+res_chk,Toast.LENGTH_LONG).show();


        if("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,".equalsIgnoreCase(res_chk))
        {
            Toast.makeText(getApplicationContext(),"Puzzle Completed.. Press Submit..",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(cnt==1) {
                va1 = ve;
                ps1 = i;
            }
            if(cnt==2) {
                va2 = ve;
                ps2 = i;

                temp = va1;
                va1 = va2;
                va2 = temp;

                aa[ps2] = va2;
                aa[ps1] = va1;
                res_chk="";

                for (int im = 0; im < aa.length; im++) {
                    res_chk += aa[im] + ",";
                }
                String jsa = res_chk;
                aa = jsa.split(",");
                gv.setAdapter(new CustomGrid(getApplicationContext(), aa));

                cnt = 0;
                va1 = "";
                va2 = "";
                ps1 = 0;
                ps2 = 0;
                temp = "";
            }
        }




    }

    @Override
    public void onClick(View v) {




        if("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,".equalsIgnoreCase(res_chk))
        {
            String type ="jigsaw";
            String time = t.getText().toString();

            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ip= sh.getString("ip","");

            String url="http://"+ip+":5000/and_puzzleresults";

            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {



                    try {

                        JSONObject js=new JSONObject(s);

                        String status=js.getString("status");
                        if(status.equalsIgnoreCase("ok"))
                        {
                            Intent ijj=new Intent(getApplicationContext(), viewimagepuzzle.class);
                            startActivity(ijj);

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

                    String id=sh.getString("lid","");
                    params.put("lid",id);
                    params.put("puzzleid", sh.getString("puzzle_id", ""));
                    params.put("time", time);
                    params.put("result","pass");
                    return (params);

                }
            };

            requestqueue.add(postrequest);

        }
        else{
            Toast.makeText(getApplicationContext(),"THE PUZZLE IS INCOMPLETE",Toast.LENGTH_SHORT).show();
            String type ="jigsaw";
            String time = t.getText().toString();

            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ip= sh.getString("ip","");

            String url="http://"+ip+":5000/and_puzzleresults";

            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {



                    try {

                        JSONObject js=new JSONObject(s);

                        String status=js.getString("status");
                        if(status.equalsIgnoreCase("ok"))
                        {
                            Intent ijj=new Intent(getApplicationContext(), viewimagepuzzle.class);
                            startActivity(ijj);

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

                    String id=sh.getString("lid","");
                    params.put("lid",id);
                    params.put("puzzleid", sh.getString("puzzle_id", ""));
                    params.put("time", time);
                    params.put("result","fail");
                    return (params);

                }
            };

            requestqueue.add(postrequest);
            Intent i = new Intent( getApplicationContext(),Home2.class);
            startActivity(i);
        }
    }
}

