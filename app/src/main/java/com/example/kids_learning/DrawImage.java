package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DrawImage extends AppCompatActivity implements View.OnClickListener {

    ImageView button11;
    ImageView button13;
    ImageView savedraw;
    DrawingViewnew drawingViewnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_image);
        button11=(ImageView) findViewById(R.id.button11);
        button13=(ImageView) findViewById(R.id.button13);
        savedraw=(ImageView) findViewById(R.id.savedraw);
        drawingViewnew=(DrawingViewnew) findViewById(R.id.writingViewnew);
        button11.setOnClickListener(this);
        button13.setOnClickListener(this);
        savedraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==button11){
            Intent i = new Intent(getApplicationContext(),HomePage.class);
            startActivity(i);
        }
        else if (view==button13){
            drawingViewnew.clear();
            Toast.makeText(getApplicationContext(), "Screen Cleared", Toast.LENGTH_LONG).show();


        }
        else if(view==savedraw){

            drawingViewnew.testNN(DrawImage.this);
            Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();

//            String intStorageDirectory = getApplicationContext().getFilesDir().toString();
//
//            //Toast.makeText(con,intStorageDirectory,Toast.LENGTH_SHORT).show();;
//            File dir = new File(intStorageDirectory, "testsample");
//            File file = new File(dir, "HandWriting.bmp");
//
//            try {
//                if(file.exists())
//                {
//                    Toast.makeText(getApplicationContext(), " Exist", Toast.LENGTH_LONG).show();
//
//                    try {
//
//                        byte[] b = new byte[8192];
//                        Log.d("bytes read", "bytes read");
//
//                        InputStream inputStream = new FileInputStream(file);
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//                        int bytesRead = 0;
//
//                        while ((bytesRead = inputStream.read(b)) != -1) {
//                            bos.write(b, 0, bytesRead);
//                        }
//                        byte [] byteArray = bos.toByteArray();
//
//                        String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
//                        String attach = str;
//
//                        Toast.makeText(getApplicationContext(), attach, Toast.LENGTH_LONG).show();
//
//
//
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        String ip = sh.getString("ip","");
//                        final String url = "http://"+ip+":5000/savedrawing_student";
//
//
//                        RequestQueue requestqueue = Volley.newRequestQueue(getApplicationContext());
//                        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//                            @Override
//                            public void onResponse(String s) {
//
//
//
//                                try {
//                                    JSONObject json = new JSONObject(s);
//                                    String res = json.getString("status");
//
//                                    if (res.equals("ok") == true) {
//
//
//
//                                        //Toast.makeText(con, "......"+unicode[lwritewrite.i-1]+"....."+code+"....", Toast.LENGTH_SHORT).show();
//
//
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//
//                                Toast.makeText(getApplicationContext(), "Error........" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }) {
//
//                            @Override
//                            public Map<String, String> getParams() {
//                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("image", attach);
//                                String id=sh.getString("lid","");
//                                params.put("lid",id);
////                                params.put("charcode", lwritewrite.unicode[lwritewrite.i-1]+"");
//
//                                return (params);
//
//                            }
//                        };
//
//                        requestqueue.add(postrequest);



//                    }
//
//                    catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "No result found"+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Not Exist", Toast.LENGTH_LONG).show();

                }






    }
}