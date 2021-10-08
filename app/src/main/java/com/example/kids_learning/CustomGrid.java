package com.example.kids_learning;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URL;


public class CustomGrid extends BaseAdapter {

    private android.content.Context Context;
    String[] c;
    String[]sp22;
    String dd;




    public CustomGrid(android.content.Context applicationContext, String[] c) {

        this.Context=applicationContext;
        this.c=c;



    }

    @Override
    public int getCount() {

        return c.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.custom_grid2, null);



        }
        else
        {
            gridView=(View)convertview;

        }

        ImageView img=(ImageView)gridView.findViewById(R.id.imageView);
//        final LinearLayout lin=(LinearLayout)gridView.findViewById(R.id.linr22);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
        String ip=sh.getString("ip","");

        dd=sh.getString("pt","");
        Log.d("=================+++",dd);
        String[] sk=dd.split("\\.");
//        String[] sk=dd.split("\\.");

                try {
            URL url = new URL("http://"+ip+":5000/static/puzzleimage/jigsaw_sliced_"+c[position]+"-"+sh.getString("puzzle","")+".jpg");
           Log.d(url.toString(),"");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            img.setImageBitmap(bmp);
//            Picasso.with(Context).load("http://"+ip+":8000/static/uploads/images/jigsaw/jigsaw_sliced/"+c[position]+"-"+sk[0]+".jpg").into(img);

        }catch (Exception e){
            Log.d("==========eeeee",e.toString());
        }
        return gridView;
    }
}




