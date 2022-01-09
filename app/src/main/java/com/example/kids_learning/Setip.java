package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Setip extends AppCompatActivity implements View.OnClickListener {
    EditText setip;
    ImageView go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip);
        setip=(EditText)findViewById(R.id.setip);
        go=(ImageView) findViewById(R.id.imageView5);
        go.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String ip=setip.getText().toString();
        if(ip.length()==0)
        {
            setip.setError("Please Set Your IP");
        }
        else {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", ip);
            ed.commit();
            Intent ii = new Intent(getApplicationContext(), Login.class);
            startActivity(ii);
        }
    }
}