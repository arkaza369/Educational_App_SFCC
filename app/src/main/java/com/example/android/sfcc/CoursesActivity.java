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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class CoursesActivity extends AppCompatActivity  implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "CoursesActivity";
    private CardView class_8, class_9, class_10, hindi_grammer, eng_grammer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        class_8 = findViewById(R.id.class_8);
        class_9 = findViewById(R.id.class_9);
        class_10 = findViewById(R.id.class_10);
        hindi_grammer = findViewById(R.id.hindi_grammer);
        eng_grammer = findViewById(R.id.eng_grammer);

        class_8.setOnClickListener(this);
        class_9.setOnClickListener(this);
        class_10.setOnClickListener(this);
        hindi_grammer.setOnClickListener(this);
        eng_grammer.setOnClickListener(this);


        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: home");
                        break;
                    case R.id.courses:
                        intent = new Intent(getApplicationContext(), CoursesActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Courses");
                        break;
                    case R.id.tutors:
                        intent = new Intent(getApplicationContext(), TutorsActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Tutors");
                        break;
                    case R.id.test_yourself:
                        intent = new Intent(getApplicationContext(), TestYourselfActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Test Yourself");
                        break;
                    case R.id.settings:
                        Toast.makeText(CoursesActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Settings");
                        break;
                    case R.id.contact_us:
                        Toast.makeText(CoursesActivity.this, "Contact us", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Contact us");
                        break;
                    case R.id.model_set:
                        intent = new Intent(getApplicationContext(), ModelSetActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
                        break;
                    case R.id.logout:
                        Toast.makeText(CoursesActivity.this, "Log out", Toast.LENGTH_SHORT).show();
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.class_8:
                Toast.makeText(getBaseContext(), "Class Eight", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: class 8");
                break;
            case R.id.class_9:
                Toast.makeText(CoursesActivity.this, "Class Nine", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: class 9");
                break;
            case R.id.class_10:
                Log.d(TAG, "onClick: class 10");
                intent = new Intent(this, ClassTen.class);
                startActivity(intent);
                break;
            case R.id.hindi_grammer:
                Log.d(TAG, "onClick: hindi_grammer");
                Toast.makeText(CoursesActivity.this, "Hindi Grammer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.eng_grammer:
                Log.d(TAG, "onClick: eng_grammer");
                Toast.makeText(CoursesActivity.this, "English Grammer", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

