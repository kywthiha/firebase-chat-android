package com.example.kyaw.firebasetutorial.chat.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.activity.MessageViewActivity;
import com.example.kyaw.firebasetutorial.chat.adapter.UserListAdapter;
import com.example.kyaw.firebasetutorial.chat.model.Chat;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private final UserListAdapter mAdapter;
    CardView userItem;
    CircleImageView profileimage;
    TextView name;
    TextView time;
    TextView message;
    String userid;
    String adminid;
    Chat chat;


    public UserListViewHolder(UserListAdapter mAdapter, @NonNull View itemView) {
        super(itemView);
        this.mAdapter = mAdapter;
        profileimage=(CircleImageView)itemView.findViewById(R.id.icon_avata);
        time=(TextView)itemView.findViewById(R.id.txtTime);
        name=(TextView)itemView.findViewById(R.id.txtName);
        message=(TextView)itemView.findViewById(R.id.txtMessage);
        userItem=(CardView)itemView.findViewById(R.id.user_item);
      userItem.setOnClickListener(this);
    }
    public void bindToData(Chat chat, final Context context){
        Glide.with(context).load(R.drawable.moesetwine).into(profileimage);
        this.chat=chat;
        this.name.setText(chat.getUsername());
        this.time.setText(chat.getTime());
        this.message.setText(chat.getLastmessage());
        this.adminid=chat.getAdminid();
        this.userid=chat.getUserid();




    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(),"hi",Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putSerializable("chat",chat);
        Context context = v.getContext();
        final Intent intent = new Intent(context,MessageViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);



    }
}
