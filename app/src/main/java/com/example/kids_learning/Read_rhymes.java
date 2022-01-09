package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Read_rhymes extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    TextView heading, contnt;
    Button b,bs;
    TextToSpeech spk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_rhymes);
        spk = new TextToSpeech(this, this);
        b = (Button) findViewById(R.id.playbtn);
        bs = (Button) findViewById(R.id.stpbtn);
        heading = (TextView) findViewById(R.id.heading);
        contnt = (TextView) findViewById(R.id.storycontent);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = sh.getString("name", "");
        String con = sh.getString("docum", "");
        heading.setText(name);
        contnt.setText(con);
        b.setOnClickListener(this);
        bs.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == b) {
            spk.speak(contnt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(v == bs){
            spk.stop();
        }
    }

    @Override
    public void onInit(int status) {

    }
}

