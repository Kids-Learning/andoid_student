package com.example.kids_learning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class custom_exam extends BaseAdapter {
    private final Context context;
    String [] ex_id,subject,date,time,t_name;
    public custom_exam(Context applicationContext,String[] ex_id,String[] subject,  String[] date,String[] time, String[] t_name) {
        this.context=applicationContext;
        this.ex_id=ex_id;
        this.subject=subject;
        this.date=date;
        this.time=time;
        this.t_name=t_name;

    }

    @Override
    public int getCount() {
        return subject.length;
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
            gridView=inflator.inflate(R.layout.custom_exam,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv_subject=(TextView)gridView.findViewById(R.id.textView2);
        TextView tv_date=(TextView)gridView.findViewById(R.id.textView3);
        TextView tv_time=(TextView)gridView.findViewById(R.id.textView4);
        TextView tv_teacher=(TextView)gridView.findViewById(R.id.textView8);
        Button attend_exam=(Button)gridView.findViewById(R.id.button2);
        Button view_result=(Button)gridView.findViewById(R.id.button);
        attend_exam.setTag(i);
        attend_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("examid", ex_id[pos]);
                ed.putString("count", "0");
                ed.putString("mark", "0");
                ed.commit();

                Intent intent=new Intent(context, Viewquestions.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        tv_subject.setTextColor(Color.BLACK);
        tv_date.setTextColor(Color.BLACK);
        tv_time.setTextColor(Color.BLACK);
        tv_teacher.setTextColor(Color.BLACK);


        tv_subject.setText(subject[i]);
        tv_date.setText(date[i]);
        tv_time.setText(time[i]);
        tv_teacher.setText(t_name[i]);

        return gridView;




    }
}
