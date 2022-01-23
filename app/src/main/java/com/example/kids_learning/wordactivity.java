package com.example.kids_learning;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class wordactivity extends AppCompatActivity implements View.OnClickListener {
    Button wordbtn;
    TextView im;
    ImageButton imbtn;
    String stat="";
    private final int REQ_CODE = 100;
    String svalue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wordactivity);
        wordbtn=findViewById(R.id.wordbtn);
        wordbtn.setOnClickListener(this);
        im = (TextView) findViewById(R.id.obimage);
        imbtn = (ImageButton)findViewById(R.id.imageButton2);
        imbtn.setOnClickListener(this);

SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
im.setText(sh.getString("word",""));

    }

    @Override
    public void onClick(View v) {
        if(v==imbtn){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
            try {
                startActivityForResult(intent, REQ_CODE);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        "Sorry your device not supported",
                        Toast.LENGTH_SHORT).show();
            }
        }




        else if(v==wordbtn){
            Intent i = new Intent(getApplicationContext(),Home2.class);
            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if ((resultCode == RESULT_OK) && (data!=null))
                {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    svalue=result.get(0);
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if(svalue.equalsIgnoreCase(sh.getString("word","")))
                    {
                        stat="yes";
                        Toast.makeText(getApplicationContext(),"Correct Answer", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        stat="no";
                        Toast.makeText(getApplicationContext(),"Wrong Answer", Toast.LENGTH_SHORT).show();
                    }


//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String ip = sh.getString("ip", "");
                    final String url = "http://" + ip + ":5000/addword_result";
                    RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String s) {


                            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                            try {
                                JSONObject json = new JSONObject(s);
                                String res = json.getString("status");

                                if (res.equals("ok") == true) {
                                    Intent i = new Intent(getApplicationContext(),viewwords.class);
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
                            String objid = sh.getString("wid", "");
                            String cid = sh.getString("lid", "");
                            params.put("status", stat);
                            params.put("wid", objid);
                            params.put("sid", cid);
//                }
                            return (params);

                        }
                    };
                    requestqueue.add(postrequest);

                }
                break;
            }
        }
    }
}
