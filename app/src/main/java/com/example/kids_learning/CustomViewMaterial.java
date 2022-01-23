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

public class CustomViewMaterial extends BaseAdapter {
    private final Context context;
    String []subject,file,date,name;
    public CustomViewMaterial(Context applicationContext, String[] subject, String[] file, String[] date, String[] name) {
        this.context=applicationContext;
        this.subject=subject;
        this.file=file;
        this.date=date;
        this.name=name;

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
            gridView=inflator.inflate(R.layout.viewmaterials,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView subject1=(TextView)gridView.findViewById(R.id.textView3);
        TextView file1=(TextView)gridView.findViewById(R.id.textView4);
        TextView teacher1=(TextView)gridView.findViewById(R.id.textView2);
        TextView date1=(TextView)gridView.findViewById(R.id.textView15);
        Button download=(Button)gridView.findViewById(R.id.button36);
        download.setTag(i);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                String ip=sh.getString("ip","");
                String url="http://" + ip + ":5000"+ file[pos];

                Uri uri = Uri.parse(url);
                Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Check what kind of file you are trying to open, by comparing the url with extensions.
                // When the if condition is matched, plugin sets the correct intent (mime) type,
                // so Android knew what application to use to open the file
                if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                    // Word document
                    intent.setDataAndType(uri, "application/msword");
                } else if(url.toString().contains(".pdf")) {
                    // PDF file
                    intent.setDataAndType(uri, "application/pdf");
                } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                    // Powerpoint file
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                    // Excel file
                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                    // WAV audio file
                    intent.setDataAndType(uri, "application/x-wav");
                } else if(url.toString().contains(".rtf")) {
                    // RTF file
                    intent.setDataAndType(uri, "application/rtf");
                } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                    // WAV audio file
                    intent.setDataAndType(uri, "audio/x-wav");
                } else if(url.toString().contains(".gif")) {
                    // GIF file
                    intent.setDataAndType(uri, "image/gif");
                } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                    // JPG file
                    intent.setDataAndType(uri, "image/jpeg");
                } else if(url.toString().contains(".txt")) {
                    // Text file
                    intent.setDataAndType(uri, "text/plain");
                } else {
                    //if you want you can also define the intent type for any other file

                    //additionally use else clause below, to manage other unknown extensions
                    //in this case, Android will show all applications installed on the device
                    //so you can choose which application to use
                    intent.setDataAndType(uri, "*/*");
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        subject1.setTextColor(Color.BLACK);
        file1.setTextColor(Color.BLACK);
        teacher1.setTextColor(Color.BLACK);
        date1.setTextColor(Color.BLACK);


        subject1.setText(subject[i]);
        file1.setText(file[i]);
        teacher1.setText(name[i]);
        date1.setText(date[i]);


        return gridView;




    }
}
