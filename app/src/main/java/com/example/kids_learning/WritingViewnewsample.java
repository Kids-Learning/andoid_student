package com.example.kids_learning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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


/**
 * Created by amalmohann on 26/02/2020.
 */


public class WritingViewnewsample extends View {


    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;
    String attach;
    byte[] byteArray = null;
    String output = "";
    int unicode[] = {3333,3334,3335,3337,3342,3343,3346,3349,3350,3351,3352,3353,3354,3355,3356,3357,3358,3359,3360,3361,3362,3363,3364,3365,3366,3367,3368,3370,3371,3372,3373,3374,3375,3376,3377,3378,3379,3380,3381,3382,3383,3384,3385,3450,3451,3452,3453,3454};




    public WritingViewnewsample(Context context) {
        super(context);
        init();
    }

    public WritingViewnewsample(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Init Neural Network instance
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        mPath = new Path();
        mCanvas = new Canvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void clear() {
        mPath.reset();
        invalidate();
    }

    @SuppressLint("WrongThread")
    public String testNN(Context con) {



        this.setDrawingCacheEnabled(true);

        Bitmap b2 = Bitmap.createScaledBitmap(BitmapUtils.cropCharacterArea(getDrawingCache()), 200, 200, false);
        Bitmap newBitmap = Bitmap.createBitmap(b2.getWidth(), b2.getHeight(), b2.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(b2, 0, 0, null);



        String intStorageDirectory = con.getFilesDir().toString();

        //Toast.makeText(con,intStorageDirectory,Toast.LENGTH_SHORT).show();;
        File dir = new File(intStorageDirectory, "testsample");
        dir.mkdirs();


        if (!dir.exists()) {

            dir.mkdirs();
            Toast.makeText(con, "Temp folder Created", Toast.LENGTH_SHORT).show();
        }
        else{

//            Toast.makeText(con,"Failed",Toast.LENGTH_SHORT).show();
        }

        File file = new File(dir, "HandWriting.bmp");

        try {

            FileOutputStream fOut = new FileOutputStream(file);

            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();


        } catch (Exception e) {



            e.printStackTrace();
        }
        this.setDrawingCacheEnabled(false);
        b2.recycle();
        newBitmap.recycle();


        return characterVerify(con, file);
    }

    private String characterVerify(final Context con, File image) {

        try {

            byte[] b = new byte[8192];
            Log.d("bytes read", "bytes read");

            InputStream inputStream = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byteArray = bos.toByteArray();

            String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            attach = str;

        }

        catch (Exception e) {
            Toast.makeText(con, "No result found"+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(con);
        String ip = sh.getString("ip","");
        final String url = "http://"+ip+":5000/addhandwriting_student";


        RequestQueue requestqueue = Volley.newRequestQueue(con);
        StringRequest postrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {



                try {
                    JSONObject json = new JSONObject(s);
                    String res = json.getString("status");

                    if (res.equals("ok") == true) {

                        output = json.getString("output");
                        int code = Integer.parseInt(json.getString("code"));


                        if (code==(unicode[lwritewrite.i-1])) {

                            Toast.makeText(con, "Success", Toast.LENGTH_LONG).show();

                            lwritewrite.mark = lwritewrite.mark + 10;
                            //Toast.makeText(con, lwritewrite.mark+"_-", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(con, "Fail", Toast.LENGTH_LONG).show();

                        }

                        //Toast.makeText(con, "......"+unicode[lwritewrite.i-1]+"....."+code+"....", Toast.LENGTH_SHORT).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(con, "Error........" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(con);
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", attach);
                String id=sh.getString("lid","");
                params.put("lid",id);

                return (params);

            }
        };

                requestqueue.add(postrequest);

        return output;
    }


}