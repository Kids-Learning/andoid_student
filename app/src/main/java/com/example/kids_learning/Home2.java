package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home2 extends AppCompatActivity implements View.OnClickListener {
    Button viewprofile;
    Button button1;
    Button button2;
    Button bt_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        viewprofile=(Button)findViewById(R.id.viewprofile);
        button1=(Button)findViewById(R.id.button1);
        bt_num=(Button)findViewById(R.id.button2);
        button1.setOnClickListener(this);
        viewprofile.setOnClickListener(this);
        bt_num.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view==viewprofile){
        Intent vp=new Intent(getApplicationContext(),ViewStudent.class);
        startActivity(vp);}
        else if(view==bt_num){
            Intent vp=new Intent(getApplicationContext(),ViewNumericalProblem.class);
            startActivity(vp);}
        else {
            Intent vp=new Intent(getApplicationContext(),ViewMaterials.class);
            startActivity(vp);}
    }
}