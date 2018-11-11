package com.example.kyaw.firebasetutorial;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListViewHolder extends RecyclerView.ViewHolder {
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private final UserListAdapter mAdapter;
    CircleImageView profileimage;
    TextView name;
    TextView time;
    TextView message;


    public UserListViewHolder(UserListAdapter mAdapter, @NonNull View itemView) {
        super(itemView);
        this.mAdapter = mAdapter;
        profileimage=(CircleImageView)itemView.findViewById(R.id.icon_avata);
        time=(TextView)itemView.findViewById(R.id.txtTime);
        name=(TextView)itemView.findViewById(R.id.txtName);
        message=(TextView)itemView.findViewById(R.id.txtMessage);
    }
    public void bindToData(String url, String name, String time, String message, final Context context){
        Log.w("url", url);
        storageReference.child(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context).load(uri).into(profileimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        this.name.setText(name);
        this.time.setText(time);
        this.message.setText(message);


    }
}
