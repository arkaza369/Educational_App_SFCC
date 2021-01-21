package com.example.android.sfcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class Settings extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private CircularImageView userProfilePic;
    private TextView changeProfile,user_name,user_phone_no,user_email_id;
    private ImageView phoneImage,emailImage,profileImage;
    private Button save;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userProfilePic = findViewById(R.id.profile_image);
        changeProfile = findViewById(R.id.change_profile_btn);
        user_name = findViewById(R.id.user_name);
        user_phone_no = findViewById(R.id.phone_no);
        user_email_id = findViewById(R.id.email_id);
        profileImage = findViewById(R.id.user_pic);
        emailImage = findViewById(R.id.email_img);
        phoneImage = findViewById(R.id.phone);
        save = findViewById(R.id.save_btn);




        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("profileImages");
        mAuth = FirebaseAuth.getInstance();

        String id = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String keys=datas.getKey();
                    user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    String userName=datas.child(uid+"/fullName").getValue().toString();
                    String phone=datas.child(uid+"/phoneNo").getValue().toString();
                    String email=datas.child(uid+"/email").getValue().toString();
                    user_name.setText(userName);
                    user_phone_no.setText(phone);
                    user_email_id.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(Settings.this);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });
        getUserInfo();

    }
    private void getUserInfo() {
        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
                {
                   /* String name = dataSnapshot.child("name").getValue().toString();
                    String  phone = dataSnapshot.child("phone").getValue().toString();*/


                    if (dataSnapshot.hasChild("image"))
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(userProfilePic);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode  == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            userProfilePic.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadProfileImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait, while we are setting your data ");
        progressDialog.show();

        if (imageUri != null)
        {
            mAuth = FirebaseAuth.getInstance();

            String id = mAuth.getCurrentUser().getUid();
            final StorageReference fileRef = storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid()+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);


            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUrl =task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);
                        reference = FirebaseDatabase.getInstance("https://sfcc-29ece-default-rtdb.firebaseio.com/").
                                getReference("users/"+mAuth.getCurrentUser().getUid());

                        reference.updateChildren(userMap);

                        progressDialog.dismiss();


                    }

                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "Data update", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}
