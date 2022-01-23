package com.example.kids_learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class lwritewrite extends AppCompatActivity implements View.OnClickListener {


    public Button b1, b2, b3, b4,b5;
    private WritingViewnew wv;
    public static TextView tv;
//    public static int displaycode =0;
    public static int mark =0;
    public static int i=0;

    public static int unicode[] = {3333,3334,3335,3337,3342,3343,3346,3349,3350,3351,3352,3353,3354,3355,3356,3357,3358,3359,3360,3361,3362,3363,3364,3365,3366,3367,3368,3370,3371,3372,3373,3374,3375,3376,3377,3378,3379,3380,3381,3382,3383,3384,3385,3450,3451,3452,3453,3454};
    int scount=0,mcount=0;
    TextView t;
    Handler h;
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
        setContentView(R.layout.activity_lwritewrite);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1001);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1002);
        i=0;
        mark =0;
        t =(TextView)findViewById(R.id.textView16);
        b1 = (Button)findViewById(R.id.prev);
        b2 = (Button)findViewById(R.id.nex);
        b3 = (Button)findViewById(R.id.writeDone);
        b4 = (Button)findViewById(R.id.button5);
        b5 = (Button)findViewById(R.id.button6);
        tv = (TextView)findViewById(R.id.result_txt2) ;
//        Typeface typeFace = Typeface.createFromAsset(getAssets(),"ThooliUc.TTF");
//        tv.setTypeface(typeFace);
        wv = (WritingViewnew) findViewById(R.id.writingViewnew);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);


        try {

            printchara(unicode[i]); //exiting with an exception

        }
        catch (Exception e)
        {
            Log.d("===errorr===", e.getMessage()+"");
        }


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wv.testNN(lwritewrite.this);
               // Toast.makeText(getApplicationContext(), mark+"....."+unicode[i]+"...."+charcode, Toast.LENGTH_SHORT).show();


                if (i == unicode.length) {
                    i = 0;
                } else {
                    i = i + 1;
                }
                printchara(unicode[i]);
                wv.invalidate();
                wv.clear();
                wv.invalidate();
                //Toast.makeText(getApplicationContext(), mark+"", Toast.LENGTH_SHORT).show();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.clear();
                wv.invalidate();

            }
        });
        h=new Handler();

        h.post(timer);



    }
    @Override
    public void onClick(View v) {
        if (v == b1) {
            if (i == 0) {
                i = 0;
            } else {
                i = i - 1;
            }
            printchara(unicode[i]);
        } else if (v == b2) {
            if (v == b2) {
                if (i == unicode.length) {

                    Intent i = new Intent(getApplicationContext(), RESULT.class);
                    startActivity(i);
                } else {
                    i = i + 1;
                }
                printchara(unicode[i]);
            }
        }
        else if (v == b3) {


            String type ="write";
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("Utype",type);
            String time = t.getText().toString();
            ed.putString("time",time);
            ed.putString("result",mark+"");
            ed.commit();
                Intent i = new Intent(getApplicationContext(), RESULT.class);
                startActivity(i);

        }
//        else if (v == b4) {
//            wv.clear();
//            wv.testNN(lwritewrite.this);
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String o = sh.getString("output","");
//            if(o.equalsIgnoreCase(tv.getText().toString())){
//                mark = mark + 10;
//            }
//            if (i == unicode.length) {
//                i = 0;
//            } else {
//                i = i + 1;
//            }
//            printchara(unicode[i]);
//            wv.invalidate();
//        }
//        else if(v == b5){
//            wv.clear();
//            wv.invalidate();
//        }
    }



    public void printchara(int x){


        switch (x)
        {

            case 3333: tv.setText("അ");break;
            case 3334:tv.setText("ആ");  break;
            case 3335:tv.setText("ഇ");   break;
            case 3337:tv.setText("ഉ");   break;
            case 3342:tv.setText("എ");  break;
            case 3343:tv.setText("ഏ");  break;
            case 3346:tv.setText("ഒ");   break;
            case 3349:tv.setText("ക"); break;
            case 3350:tv.setText("ഖ");  break;
            case 3351:tv.setText("ഗ"); break;
            case 3352:tv.setText("ഘ"); break;
            case 3353:tv.setText("ങ"); break;
            case 3354:tv.setText("ച"); break;
            case 3355:tv.setText("ഛ"); break;
            case 3356:tv.setText("ജ"); break;
            case 3357:tv.setText("ഝ"); break;
            case 3358:tv.setText("ഞ"); break;
            case 3359:tv.setText("ട"); break;
            case 3360:tv.setText("ഠ"); break;
            case 3361:tv.setText("ഡ"); break;
            case 3362:tv.setText("ഢ"); break;
            case 3363:tv.setText("ണ"); break;
            case 3364:tv.setText("ത"); break;
            case 3365:tv.setText("ഥ"); break;
            case 3366:tv.setText("ദ"); break;
            case 3367:tv.setText("ധ"); break;
            case 3368:tv.setText("ന"); break;
            case 3370:tv.setText("പ"); break;
            case 3371:tv.setText("ഫ"); break;
            case 3372:tv.setText("ഭ"); break;
            case 3374:tv.setText("മ"); break;
            case 3375:tv.setText("യ"); break;
            case 3376:tv.setText("ര"); break;
            case 3377:tv.setText("ല"); break;
            case 3379:tv.setText("ള"); break;
            case 3380:tv.setText("ഴ"); break;
            case 3381:tv.setText("വ"); break;
            case 3382:tv.setText("ശ"); break;
            case 3383:tv.setText("ഷ"); break;
            case 3384:tv.setText("സ"); break;
            case 3385:tv.setText("ഹ"); break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1001:
                // BEGIN_INCLUDE(permission_result)
                // Received permission result for camera permission.
                Log.i(TAG, "Received response for SDCARD permission request.");

                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has been granted, preview can be displayed
                    Log.i(TAG, "SDCARD permission has now been granted. Showing preview.");

                } else {
                    Log.i(TAG, "SDCARD permission was NOT granted.");

                }
                // END_INCLUDE(permission_result)

                break;
            case 1002:
                // BEGIN_INCLUDE(permission_result)
                // Received permission result for camera permission.
                Log.i(TAG, "Received response for SDCARD permission request.");

                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has been granted, preview can be displayed
                    Log.i(TAG, "SDCARD permission has now been granted. Showing preview.");

                } else {
                    Log.i(TAG, "SDCARD permission was NOT granted.");

                }
                // END_INCLUDE(permission_result)

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

}

