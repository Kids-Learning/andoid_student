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
    Button imagepuzzle;
    Button exams;
    Button words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        viewprofile=(Button)findViewById(R.id.viewprofile);
        button1=(Button)findViewById(R.id.button1);
        bt_num=(Button)findViewById(R.id.button2);
        imagepuzzle=(Button)findViewById(R.id.imagepuzzle);
        words=(Button)findViewById(R.id.button8);

        exams=(Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);
        viewprofile.setOnClickListener(this);
        bt_num.setOnClickListener(this);
        imagepuzzle.setOnClickListener(this);
        exams.setOnClickListener(this);
        words.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view==viewprofile){
        Intent vp=new Intent(getApplicationContext(),ViewStudent.class);
        startActivity(vp);}
        else if(view==bt_num){
            Intent vp=new Intent(getApplicationContext(),ViewNumericalProblem.class);
            startActivity(vp);}
        else if(view==imagepuzzle){
            Intent vp=new Intent(getApplicationContext(),viewimagepuzzle.class);
            startActivity(vp);}
        else if(view==exams){
            Intent vp=new Intent(getApplicationContext(),viewexam.class);
            startActivity(vp);}
        else if(view==words){
            Intent vp=new Intent(getApplicationContext(),viewwords.class);
            startActivity(vp);}
        else {
            Intent vp=new Intent(getApplicationContext(),ViewMaterials.class);
            startActivity(vp);}
    }
}