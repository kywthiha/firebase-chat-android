package com.example.kyaw.firebasetutorial;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelloImageViewFirebase extends AppCompatActivity {
    CircleImageView imageHello;
    StorageReference fb=FirebaseStorage.getInstance().getReference();
    StorageReference image=fb.child("moesetwine.png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_image_view_firebase);
        imageHello=(CircleImageView)findViewById(R.id.image_hello);
        fb.child("moesetwine.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(getApplicationContext()).load(uri).into(imageHello);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        // Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/kyawthi725.appspot.com/o/moesetwine.png?alt=media&token=aea7b368-16a9-4354-9beb-f1ecba599f58").into(imageHello);
    }
}
