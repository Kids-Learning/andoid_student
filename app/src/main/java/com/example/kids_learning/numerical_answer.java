package com.example.kids_learning;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class numerical_answer extends AppCompatActivity implements View.OnClickListener {

    EditText ed;
        Button b1;
        String stat="";
        String svalue="";
        TextView textView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ln=new Intent(getApplicationContext(),HomePage.class);
        startActivity(ln);
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_numerical_answer);
            textView =(TextView)findViewById(R.id.textView);

            ed = (EditText) findViewById(R.id.editTextTextPersonName8);
            b1 =(Button)findViewById(R.id.quitbtn);
            SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            textView.setTextColor(Color.BLACK);
            textView.setText(sh.getString("question",""));
        b1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==b1){
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String c_ans=sh.getString("ans","");
            String ans=ed.getText().toString();
            if(c_ans.equals(ans)){
                stat="pass";
                Toast.makeText(getApplicationContext(),"Correct Answer", Toast.LENGTH_SHORT).show();
            }
            else{
                stat="fail";
                Toast.makeText(getApplicationContext(),"Wrong Answer", Toast.LENGTH_SHORT).show();
            }
            String ip = sh.getString("ip", "");
            final String url = "http://" + ip + ":5000/addnumerical_mark";
            RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject json = new JSONObject(s);
                        String res = json.getString("status");

                        if (res.equals("ok") == true) {
                            Intent i = new Intent(getApplicationContext(),ViewNumericalProblem.class);
                            startActivity(i);
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

                    String cid = sh.getString("lid", "");
                    params.put("status", stat);
                    params.put("lid", cid);
                    params.put("num_id", sh.getString("numid", ""));
//                }
                    return (params);

                }
            };
            int MY_SOCKET_TIMEOUT_MS=100000;

            postrequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestqueue.add(postrequest);
        }
    }
}
