package com.example.kids_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home2 extends AppCompatActivity implements View.OnClickListener {
    Button viewprofile;
    Button button1;
    Button button2;
    Button bt_num;
    Button imagepuzzle;
    Button exams;
    Button words;
    Button quiz;
    Button work;
    Button rhymes;


boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        viewprofile=(Button)findViewById(R.id.viewprofile);
        button1=(Button)findViewById(R.id.button1);
        bt_num=(Button)findViewById(R.id.button2);
        imagepuzzle=(Button)findViewById(R.id.imagepuzzle);
        words=(Button)findViewById(R.id.button8);
        quiz=(Button)findViewById(R.id.quiz);
        work=(Button)findViewById(R.id.work);
        rhymes=(Button)findViewById(R.id.Rhymes);


        exams=(Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);
        viewprofile.setOnClickListener(this);
        bt_num.setOnClickListener(this);
        imagepuzzle.setOnClickListener(this);
        exams.setOnClickListener(this);
        words.setOnClickListener(this);
        quiz.setOnClickListener(this);
        work.setOnClickListener(this);
        rhymes.setOnClickListener(this);


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
        else if(view==quiz){
            Intent vp=new Intent(getApplicationContext(),quiz.class);
            startActivity(vp);}
        else if(view==work){
            Intent vp=new Intent(getApplicationContext(),Work.class);
            startActivity(vp);}
        else if(view==rhymes){
            Intent vp=new Intent(getApplicationContext(),Rhymes.class);
            startActivity(vp);}
        else {
            Intent vp=new Intent(getApplicationContext(),ViewMaterials.class);
            startActivity(vp);}
    }
}