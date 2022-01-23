package com.example.kids_learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomViewImagePuazzle extends BaseAdapter {
    private final Context context;
    String []puz_id,puzzle,photo,time;
    public CustomViewImagePuazzle(Context applicationContext, String[] subject, String[] file, String[] date, String[] name) {
        this.context=applicationContext;
        this.puz_id=subject;
        this.puzzle=file;
        this.photo=date;
        this.time=name;

    }


    @Override
    public int getCount() {
        return puz_id.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.customviewpuzzle,null);

        }
        else
        {
            gridView=(View)view;

        }
        ImageView im=(ImageView)gridView.findViewById(R.id.obimage);
        TextView subject1=(TextView)gridView.findViewById(R.id.obname);
        TextView file1=(TextView)gridView.findViewById(R.id.textView6);


        subject1.setTextColor(Color.BLACK);
        file1.setTextColor(Color.BLACK);


        subject1.setText(puzzle[i]);
        file1.setText(time[i]);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000/"+photo[i];


        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);
        return gridView;




    }
}
