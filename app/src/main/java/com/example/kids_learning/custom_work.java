package com.example.kids_learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_work extends BaseAdapter {
    private final Context context;
    String [] w_id,work,lastdate;
    public custom_work(Context applicationContext,String[] w_id,String[] work,String[] lastdate){
        this.context=applicationContext;
        this.w_id=w_id;
        this.work=work;
        this.lastdate=lastdate;
    }
    @Override
    public int getCount() {
        return work.length;
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
            //gridView=inflator.inflate(R.layout.customviewword, null);
            gridView=inflator.inflate(R.layout.custom_work,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv_work=(TextView)gridView.findViewById(R.id.textView9);
        TextView tv_lastdate=(TextView)gridView.findViewById(R.id.textView10);
        Button submitwork=(Button)gridView.findViewById(R.id.button10);
        submitwork.setTag(i);
        submitwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("w_id", w_id[pos]);
                ed.commit();

                Intent intent=new Intent(context, UploadWork.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        tv_work.setTextColor(Color.BLACK);
        tv_lastdate.setTextColor(Color.BLACK);



        tv_work.setText(work[i]);
        tv_lastdate.setText(lastdate[i]);

        return gridView;




    }
}


