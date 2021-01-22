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
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "MainActivity";
    private CardView coursesCard,testCard,tutorsCard,modelSetCard;

    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private CircularImageView userImageview;


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

        mAuth = FirebaseAuth.getInstance();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.home:
                        //Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: home");
                        break;
                    case R.id.courses:
                        //Toast.makeText(MainActivity.this, "Courses", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(),CoursesActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Courses");
                        break;
                    case R.id.tutors:
                        //Toast.makeText(MainActivity.this, "Tutors", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(),TutorsActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Tutors");
                        break;
                    case R.id.test_yourself:
                        //Toast.makeText(MainActivity.this, "Test Yourself", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(),TestYourselfActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Test Yourself");
                        break;
                    case R.id.settings:
                        /*Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Settings");*/
                        intent = new Intent(getApplicationContext(),Settings.class);
                        startActivity(intent);
                        break;
                    case R.id.contact_us:
                        Toast.makeText(MainActivity.this, "Contact us", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Contact us");
                        break;
                    case R.id.model_set:
                        //Toast.makeText(MainActivity.this, "Model Sets", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), ModelSetActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "onNavigationItemSelected: Model Sets");
                        break;
                    case R.id.logout:
                        /*Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onNavigationItemSelected: Log out");*/
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

//Retrieving data of current user from database

        String id = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String keys=datas.getKey();
                    user = mAuth.getCurrentUser();
                    Log.d(TAG, "onDataChange: "+user);
                    String uid = user.getUid();
                    String user_name=datas.child(uid+"/username").getValue().toString();
                    View view = navigationView.getHeaderView(0);
                    TextView username = view.findViewById(R.id.name);
                    username.setText("Welcome " + user_name);
                    CircularImageView userProfilePic = view.findViewById(R.id.imageView);
                    if (datas.hasChild(uid+"/image"))
                    {
                        String image = datas.child(uid+"/image").getValue().toString();
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
