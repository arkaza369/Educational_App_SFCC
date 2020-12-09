package com.example.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "MainActivity";
    private CardView coursesCard,testCard,tutorsCard,modelSetCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        coursesCard = findViewById(R.id.courses_card);
        testCard = findViewById(R.id.test_yourself_card);
        tutorsCard = findViewById(R.id.tutors_card);
        modelSetCard = findViewById(R.id.model_set_card);

        coursesCard.setOnClickListener(this);
        testCard.setOnClickListener(this);
        tutorsCard.setOnClickListener(this);
        modelSetCard.setOnClickListener(this);

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: home");
                        break;
                    case R.id.courses:
                        Toast.makeText(MainActivity.this, "Courses", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Courses");
                        break;
                    case R.id.tutors:
                        Toast.makeText(MainActivity.this, "Tutors", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Tutors");
                        break;
                    case R.id.test_yourself:
                        Toast.makeText(MainActivity.this, "Test Yourself", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Test Yourself");
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Settings");
                        break;
                    case R.id.contact_us:
                        Toast.makeText(MainActivity.this, "Contact us", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Contact us");
                        break;
                    case R.id.model_set:
                        Toast.makeText(MainActivity.this, "Model Sets", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Log out");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //To access nav_header views i.e. username
        View view = navigationView.getHeaderView(0);
        TextView username = view.findViewById(R.id.name);
        username.setText("Welcome Arkaza");
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.courses_card:
                intent = new Intent(this,CoursesActivity.class);
                startActivity(intent);
                break;
            case R.id.tutors_card:
                intent = new Intent(this,TutorsActivity.class);
                startActivity(intent);
                break;
            case R.id.model_set_card:
                intent = new Intent(this,ModelSetActivity.class);
                startActivity(intent);
                break;
            case R.id.test_yourself_card:
                intent = new Intent(this,TestYourselfActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }
}
