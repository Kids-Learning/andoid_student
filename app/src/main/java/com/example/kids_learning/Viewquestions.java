package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Viewquestions extends AppCompatActivity {
    TextView question;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    Button prev;
    Button nxt;
    Button ext;
    CameraKitView cameraKitView;

    String[] qn, op_a, op_b, op_c, op_d, ans;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        cameraKitView.stopVideo();
        cameraKitView.onStop();

        Intent ln = new Intent(getApplicationContext(), HomePage.class);
        startActivity(ln);
    }
    Handler hndlk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewquestions);

        hndlk=new Handler();
        hndlk.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Runnable start", Toast.LENGTH_SHORT).show();
                sfg();
            }
        }, 5000);
        question = (TextView) findViewById(R.id.questionq);
        radioButton = (RadioButton) findViewById(R.id.radioButtonq);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2q);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3q);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4q);
        prev = (Button) findViewById(R.id.button7);
        nxt = (Button) findViewById(R.id.button6);
        ext = (Button) findViewById(R.id.button5);


        cameraKitView = findViewById(R.id.camera);
        cameraKitView.setFacing(CameraKit.FACING_FRONT);
        try {
            cameraKitView.onStart();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Marks  " + sh.getString("mark", ""), Toast.LENGTH_SHORT).show();
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/loadquestions_student";

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

                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("qn_cnt", jsonObj.getString("ln"));
                                ed.commit();

                                JSONArray js = jsonObj.getJSONArray("users");
                                qn = new String[js.length()];
                                op_a = new String[js.length()];
                                op_b = new String[js.length()];
                                op_c = new String[js.length()];
                                op_d = new String[js.length()];
                                ans = new String[js.length()];

//
                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    qn[i] = u.getString("question");
                                    op_a[i] = u.getString("optiona");
                                    op_b[i] = u.getString("optionb");
                                    op_c[i] = u.getString("optionc");
                                    op_d[i] = u.getString("optiond");
                                    ans[i] = u.getString("answer");

                                }

                                int pos = Integer.parseInt(sh.getString("count", ""));
                                question.setText(qn[pos]);
                                radioButton.setText(op_a[pos]);
                                radioButton2.setText(op_b[pos]);
                                radioButton3.setText(op_c[pos]);
                                radioButton4.setText(op_d[pos]);


//

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
//                                viewexam.setAdapter(new custom_exam(getApplicationContext(),ex_id,subject,date,time,t_name));
                                // l1.setAdapter(new Custom(getApplicationContext(),gamecode,name,type,discription,image,status));
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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

                String id = sh.getString("examid", "");
                params.put("ex_id", id);
//                params.put("mac",maclis);

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
        SharedPreferences.Editor ed = sh.edit();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                cameraKitView.stopVideo();
//                cameraKitView.onStop();

                String cc = sh.getString("count", "");
                int cnt = Integer.parseInt(cc);
                if (cnt == 0) {
                    ed.putString("count", cnt + "");
                    ed.commit();
                    Intent ij = new Intent(getApplicationContext(), viewexam.class);
                    startActivity(ij);
                } else {
                    cnt = cnt - 1;
                    ed.putString("count", cnt + "");
                    ed.commit();
                    Intent ij = new Intent(getApplicationContext(), Viewquestions.class);
                    startActivity(ij);
                }
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraKitView.stopVideo();
                cameraKitView.onStop();
                String cc = sh.getString("count", "");
                int cnt = Integer.parseInt(cc);
                int ln = Integer.parseInt(sh.getString("qn_cnt", ""));
                String answer = "";
                if (radioButton.isChecked()) {
                    answer = radioButton.getText().toString();
                }
                if (radioButton2.isChecked()) {
                    answer = radioButton2.getText().toString();
                }
                if (radioButton3.isChecked()) {
                    answer = radioButton3.getText().toString();
                }
                if (radioButton4.isChecked()) {
                    answer = radioButton4.getText().toString();
                }

                if (answer.equalsIgnoreCase(ans[cnt])) {
                    int mark = Integer.parseInt(sh.getString("mark", ""));
                    mark = mark + 1;
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("mark", mark + "");
                    ed.commit();
                }
                Toast.makeText(getApplicationContext(), answer + "  -   " + ans[cnt], Toast.LENGTH_SHORT).show();

                if (cnt == ln - 2) {
                    nxt.setText("SUBMIT");
                }
                if (cnt == ln - 1) {
                    //
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String hu = sh.getString("ip", "");
                    String url = "http://" + hu + ":5000/markinsert_student";


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

                                            Toast.makeText(getApplicationContext(), "Marks : " + sh.getString("mark", ""), Toast.LENGTH_SHORT).show();
                                            Intent ij = new Intent(getApplicationContext(), viewexam.class);
                                            startActivity(ij);
                                        }


                                        // }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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

                            params.put("mark", sh.getString("mark", ""));
                            params.put("ex_id", sh.getString("examid", ""));
                            params.put("lid", sh.getString("lid", ""));

                            return params;
                        }
                    };

                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);
                    //
                    Toast.makeText(getApplicationContext(), "Over", Toast.LENGTH_SHORT).show();
                    Intent ij = new Intent(getApplicationContext(), viewexam.class);
                    startActivity(ij);
                } else {
                    cnt = cnt + 1;
                    ed.putString("count", cnt + "");
                    ed.commit();
                    Intent ij = new Intent(getApplicationContext(), Viewquestions.class);
                    startActivity(ij);
                }
            }
        });


        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Exitting", Toast.LENGTH_SHORT).show();

                cameraKitView.stopVideo();
                cameraKitView.onStop();

                Intent ij = new Intent(getApplicationContext(), viewexam.class);
                startActivity(ij);

            }
        });

    }

    public void sfg() {

        try {
            Toast.makeText(getApplicationContext(), "Enterrrrr", Toast.LENGTH_SHORT).show();


            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView view, final byte[] photo) {

                    cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                        @Override
                        public void onImage(CameraKitView view, final byte[] photo) {



                                                       Toast.makeText(getApplicationContext(), "captured", Toast.LENGTH_SHORT).show();
                                                       SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                                       String hu = sh.getString("ip", "");
                                                       String url = "http://" + hu + ":5000/emotion_student";

                                                       VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                                                               new Response.Listener<NetworkResponse>() {
                                                                   @Override
                                                                   public void onResponse(NetworkResponse response) {
                                                                       try {

                                                                           JSONObject obj = new JSONObject(new String(response.data));
                                                                           String dis = obj.getString("status");
                                                                           if (dis.equalsIgnoreCase("ok")) {


                                                                               Toast.makeText(getApplicationContext(), "emotion inserted", Toast.LENGTH_LONG).show();


                                                                           } else {
                                                                               Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();

                                                                           }
                                                                       } catch (JSONException e) {
                                                                           Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                                                           e.printStackTrace();
                                                                       }
                                                                   }
                                                               },
                                                               new Response.ErrorListener() {
                                                                   @Override
                                                                   public void onErrorResponse(VolleyError error) {
                                                                       Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                   }
                                                               }) {


                                                           @Override
                                                           protected Map<String, String> getParams() throws AuthFailureError {
                                                               Map<String, String> params = new HashMap<>();
                                                               SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                               params.put("ex_id", sh.getString("examid", ""));

                                                               params.put("lid", sh.getString("lid", ""));
                                                               return params;
                                                           }

                                                           @Override
                                                           protected Map<String, DataPart> getByteData() {
                                                               Map<String, DataPart> params = new HashMap<>();
                                                               long imagename = System.currentTimeMillis();
                                                               params.put("pic", new DataPart("a.jpg", photo));

                                                               return params;
                                                           }
                                                       };
                                                       int MY_SOCKET_TIMEOUT_MS = 6000;

                                                       volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                                                               MY_SOCKET_TIMEOUT_MS,
                                                               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                                       Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);


                        }

                    });

                                                   }

                                               });

        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "camera error=="+ex, Toast.LENGTH_SHORT).show();
        }

    }
}