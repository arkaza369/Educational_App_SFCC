package com.babusaheb.android.sfcc;

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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ModelSetActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "ModelSetActivity";
    private CardView class_8, class_9, class_10, hindi_grammer, eng_grammer;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_set);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

     /*   class_8 = findViewById(R.id.class_8_model);
        class_9 = findViewById(R.id.class_9_model);*/
        class_10 = findViewById(R.id.class_10_model);
       /* hindi_grammer = findViewById(R.id.hindi_grammer_model);
        eng_grammer = findViewById(R.id.eng_grammer_model);*/

       /* class_8.setOnClickListener(this);
        class_9.setOnClickListener(this);*/
        class_10.setOnClickListener(this);
      /*  hindi_grammer.setOnClickListener(this);
        eng_grammer.setOnClickListener(this);*/

        mAuth = FirebaseAuth.getInstance();


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
                       /* Toast.makeText(CoursesActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Settings");*/
                        intent = new Intent(getApplicationContext(), Settings.class);
                        startActivity(intent);
                        break;
                    case R.id.contact_us:
                        intent = new Intent(getApplicationContext(), TutorsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.model_set:
                        intent = new Intent(getApplicationContext(), ModelSetActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
                        break;
                    case R.id.logout:
                        mAuth.signOut();
                        intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //To access nav_header views i.e. username

        String id = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("users/"+id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    View view = navigationView.getHeaderView(0);
                    if (datas.getKey().equals("fullName")) {

                        String name = datas.getValue().toString();
                        TextView username = view.findViewById(R.id.name);
                        username.setText("Welcome " + name);
                    }

                    CircularImageView userProfilePic = view.findViewById(R.id.imageView);
                    if (datas.getKey().equals( "image")) {
                        String image = datas.getValue().toString();
                        Picasso.get().load(image).into(userProfilePic);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
            /*case R.id.class_8_model:
                Toast.makeText(getBaseContext(), "Class Eight", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: class 8");
                break;
            case R.id.class_9_model:
                Toast.makeText(ModelSetActivity.this, "Class Nine", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: class 9");
                break;*/
            case R.id.class_10_model:
                Log.d(TAG, "onClick: class 10");
                intent = new Intent(this, ModelSetClassXSyllabusList.class);
                startActivity(intent);
                break;
          /*  case R.id.hindi_grammer_model:
                Log.d(TAG, "onClick: hindi_grammer");
                Toast.makeText(ModelSetActivity.this, "Hindi Grammer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.eng_grammer_model:
                Log.d(TAG, "onClick: eng_grammer");
                Toast.makeText(ModelSetActivity.this, "English Grammer", Toast.LENGTH_SHORT).show();
                break;*/
            default:
                break;
        }
    }
}
