package com.example.kids_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kids_learning.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomePageBinding binding;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomePage.toolbar);
        binding.appBarHomePage.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_View_Profile) {
            Intent i = new Intent(getApplicationContext(),ViewStudent.class);
            startActivity(i);

        } else if (id == R.id.nav_View_Materials) {
            Intent i = new Intent(getApplicationContext(),ViewMaterials.class);
            startActivity(i);
        } else if (id == R.id.nav_NumericalProblem) {
            Intent i = new Intent(getApplicationContext(),ViewNumericalProblem.class);
            startActivity(i);
        } else if (id == R.id.nav_Image_Puzzle) {
            Intent i = new Intent(getApplicationContext(),viewimagepuzzle.class);
            startActivity(i);
        } else if (id == R.id.nav_Exam) {
            Intent i = new Intent(getApplicationContext(),viewexam.class);
            startActivity(i);
        } else if (id == R.id.nav_Quiz) {
            Intent i = new Intent(getApplicationContext(),quiz.class);
            startActivity(i);
        } else if (id == R.id.nav_Work){
            Intent i = new Intent(getApplicationContext(),Work.class);
            startActivity(i);
        } else if (id == R.id.nav_write){
            Intent i = new Intent(getApplicationContext(),lwritewrite.class);
            startActivity(i);
        } else if (id == R.id.nav_draw){
            Intent i = new Intent(getApplicationContext(),DrawImage.class);
            startActivity(i);
        } else if (id == R.id.nav_viewresult){
            Intent i = new Intent(getApplicationContext(),ViewExamResult.class);
            startActivity(i);
        } else if (id == R.id.nav_word){
            Intent i = new Intent(getApplicationContext(),viewwords.class);
            startActivity(i);
        } else if (id == R.id.nav_object){
            Intent i = new Intent(getApplicationContext(),ObjectView.class);
            startActivity(i);
        } else if (id == R.id.nav_rhymes){
            Intent i = new Intent(getApplicationContext(),Rhymes.class);
            startActivity(i);


    }
//        else if(id == R.id.playe){
//
//            Intent i = new Intent(getApplicationContext(),lplay.class);
//            startActivity(i);
//
//        }
//        else if (id == R.id.painty) {
//
//            Intent i = new Intent(getApplicationContext(),lpaint.class);
//            startActivity(i);
//
//        } else if (id == R.id.speaky) {
//            Intent i = new Intent(getApplicationContext(),lspeak.class);
//            startActivity(i);
//
//        } else if (id == R.id.watchey) {
//            Intent i = new Intent(getApplicationContext(),VideoList.class);
//            startActivity(i);
//
//        } else if (id == R.id.storiey) {
//            Intent i = new Intent(getApplicationContext(),storytime.class);
//            startActivity(i);
//
//        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}