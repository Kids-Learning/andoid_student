package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
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

public class quiz extends AppCompatActivity {
    TextView question;
    RadioButton radioButtonq;
    RadioButton radioButton2q;
    RadioButton radioButton3q;
    RadioButton radioButton4q;
    Button prev;
    Button nxt;
    Button ext;

    String[] q_id,qn,op_a,op_b,op_c,op_d,ans;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ln=new Intent(getApplicationContext(),HomePage.class);
        startActivity(ln);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question=(TextView) findViewById(R.id.questionq);
        radioButtonq=(RadioButton) findViewById(R.id.radioButtonq);
        radioButton2q=(RadioButton) findViewById(R.id.radioButton2q);
        radioButton3q=(RadioButton) findViewById(R.id.radioButton3q);
        radioButton4q=(RadioButton) findViewById(R.id.radioButton4q);
        prev=(Button) findViewById(R.id.button7);
        nxt=(Button) findViewById(R.id.button6);
        ext=(Button) findViewById(R.id.button5);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Marks  "+sh.getString("mark",""), Toast.LENGTH_SHORT).show();
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/loadquiz_student";

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

                                SharedPreferences.Editor ed=sh.edit();
                                ed.putString("qn_cnt", jsonObj.getString("ln"));
                                ed.putString("mark", "0");

                                ed.commit();

                                JSONArray js= jsonObj.getJSONArray("users");
                                q_id=new String[js.length()];
                                qn=new String[js.length()];
                                op_a=new String[js.length()];
                                op_b=new String[js.length()];
                                op_c=new String[js.length()];
                                op_d=new String[js.length()];
                                ans=new String[js.length()];

//
                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    qn[i]=u.getString("question");
                                    op_a[i]=u.getString("optiona");
                                    op_b[i]=u.getString("optionb");
                                    op_c[i]=u.getString("optionc");
                                    op_d[i]=u.getString("optiond");
                                    ans[i]=u.getString("answer");
                                    q_id[i]=u.getString("q_id");
                                }

                                int pos=Integer.parseInt(sh.getString("count",""));
                                question.setText(qn[pos]);
                                radioButtonq.setText(op_a[pos]);
                                radioButton2q.setText(op_b[pos]);
                                radioButton3q.setText(op_c[pos]);
                                radioButton4q.setText(op_d[pos]);

                                SharedPreferences.Editor eed=sh.edit();
                                eed.putString("q_id", q_id[pos]);

                                eed.commit();

//

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
//                                viewexam.setAdapter(new custom_exam(getApplicationContext(),ex_id,subject,date,time,t_name));
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

//                String id=sh.getString("examid","");
//                params.put("ex_id",id);
////                params.put("mac",maclis);

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
        SharedPreferences.Editor ed=sh.edit();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc=sh.getString("count","");
                int cnt=Integer.parseInt(cc);
                if(cnt==0)
                {
                    ed.putString("count", cnt+"");
                    ed.commit();
                    Intent ij=new Intent(getApplicationContext(), HomePage.class);
                    startActivity(ij);
                }
                else{
                    cnt=cnt-1;
                    ed.putString("count", cnt+"");
                    ed.commit();
                    Intent ij=new Intent(getApplicationContext(), quiz.class);
                    startActivity(ij);
                }
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc=sh.getString("count","");
                int cnt=Integer.parseInt(cc);
                int ln=Integer.parseInt(sh.getString("qn_cnt",""));
                String answer="";
                if(radioButtonq.isChecked()){
                    answer=radioButtonq.getText().toString();
                }
                if(radioButton2q.isChecked()){
                    answer=radioButton2q.getText().toString();
                }
                if(radioButton3q.isChecked()){
                    answer=radioButton3q.getText().toString();
                }
                if(radioButton4q.isChecked()){
                    answer=radioButton4q.getText().toString();
                }

                if(answer.equalsIgnoreCase(ans[cnt])){
                    int mark=Integer.parseInt(sh.getString("mark",""));
                    mark=mark+1;

                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("mark", mark+"");
                    ed.commit();

                    insert_mark();
                }
                Toast.makeText(getApplicationContext(), answer+"  -   "+ans[cnt], Toast.LENGTH_SHORT).show();

                if(cnt==ln-2)
                {
                    nxt.setText("SUBMIT");
                }
                if(cnt==ln-1)
                {
                    //

                    //
//                    Toast.makeText(getApplicationContext(), "Over", Toast.LENGTH_SHORT).show();
//                    Intent ij=new Intent(getApplicationContext(), Home2.class);
//                    startActivity(ij);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("count", "0");
                    ed.commit();
                    Toast.makeText(getApplicationContext(), "Marks : "+ sh.getString("mark", ""), Toast.LENGTH_SHORT).show();
                    Intent ij= new Intent(getApplicationContext(), HomePage.class);
                    startActivity(ij);

                }
                else{
                    cnt=cnt+1;
                    ed.putString("count", cnt+"");
                    ed.commit();
                    Intent ij=new Intent(getApplicationContext(), quiz.class);
                    startActivity(ij);
                }
            }
        });


        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("count", "0");
                ed.commit();
                Toast.makeText(getApplicationContext(), "Exitting", Toast.LENGTH_SHORT).show();
                Intent ij=new Intent(getApplicationContext(),HomePage.class);
                startActivity(ij);

            }
        });

    }

    public void insert_mark()
    {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/markquiz_student";



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

                params.put("mark", sh.getString("mark",""));
                params.put("q_id",sh.getString("q_id",""));
                params.put("lid",sh.getString("lid", ""));

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
}

