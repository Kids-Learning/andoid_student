package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setip extends AppCompatActivity implements View.OnClickListener {
    EditText setip;
    Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip);
        setip=(EditText)findViewById(R.id.setip);
        go=(Button)findViewById(R.id.go);
        go.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String ip=setip.getText().toString();
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("ip",ip);
        ed.commit();
        Intent ii=new Intent(getApplicationContext(),Login.class);
        startActivity(ii);
    }
}