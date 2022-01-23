package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CancelPopup extends AppCompatActivity implements View.OnClickListener {

    ImageView okcro,imageView11;
    Button okcross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_popup);

        imageView11=(ImageView) findViewById(R.id.check);
//        okcross=(Button) findViewById(R.id.okcross);
        okcro=(ImageView) findViewById(R.id.okcro);
        okcro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            CancelPopup.this.finish();
    }
}