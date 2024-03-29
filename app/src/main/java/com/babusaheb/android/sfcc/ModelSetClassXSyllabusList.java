package com.babusaheb.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

public class ModelSetClassXSyllabusList extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String TAG = "ModelSetClassXSyllabusList";
    private TextView chapter_title,chapter_descp;

    FirebaseAuth mAuth;
    DatabaseReference reference_header, reference_model_set_classX_syllabus_list, reference;
    FirebaseUser user;
    RecyclerView mRecyclerView;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_model_set_class_xsyllabus_list);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SFCC");

        chapter_title = findViewById(R.id.model_set_class_x_pdf_title);
        chapter_descp = findViewById(R.id.model_set_class_x_pdf_descp);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
        // startVideo(startChapter);
        String id = mAuth.getCurrentUser().getUid();
        reference_header = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("users/"+id);
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();

        reference_header.addValueEventListener(new ValueEventListener() {
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
        mRecyclerView = findViewById(R.id.model_set_recyclerview_classx_syllabus_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference_model_set_classX_syllabus_list = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference("model_set/class_10");


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ModelSetClassXViewModelClass, ModelSetViewModel> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelSetClassXViewModelClass, ModelSetViewModel>(
                        ModelSetClassXViewModelClass.class,
                        R.layout.model_set_class_x_syllabus_list,
                        ModelSetViewModel.class,
                        reference_model_set_classX_syllabus_list
                ) {
                    @Override
                    protected void populateViewHolder(ModelSetViewModel viewModel, ModelSetClassXViewModelClass viewModelClass, int i) {
                        viewModel.classXSetPdf(getApplication(),viewModelClass.getName(),viewModelClass.getDescription());
                    }


                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }



}
