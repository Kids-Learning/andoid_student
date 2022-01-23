package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityPopup extends AppCompatActivity implements View.OnClickListener {
    ImageView popupimage,okimage;
    Button okpopup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        popupimage=(ImageView) findViewById(R.id.check);
        okimage=(ImageView) findViewById(R.id.okimage);
//        okpopup=(Button) findViewById(R.id.okcross);

        okimage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
ActivityPopup.this.finish();
    }
}