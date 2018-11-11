package com.example.kyaw.firebasetutorial.chat.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.adapter.UserListAdapter;
import com.example.kyaw.firebasetutorial.chat.model.Chat;
import com.example.kyaw.firebasetutorial.chat.model.Message;
import com.example.kyaw.firebasetutorial.chat.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.LinkedList;

public class UserListActivity extends AppCompatActivity {
    DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("admins");
    DatabaseReference messagefb=FirebaseDatabase.getInstance().getReference().child("messages");
    DatabaseReference chatdb=database.child("Pakokku");
    String lm;
    private LinkedList<String> chatsId=new LinkedList<>();
    RecyclerView userlist;
    LinkedList<Chat> chats=new LinkedList<>();
    UserListAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        userlist=(RecyclerView)findViewById(R.id.user_list);
        mAdapter=new UserListAdapter(chats,getApplicationContext());
        userlist.setAdapter(mAdapter);
        final LinearLayoutManager ly=new LinearLayoutManager(this);
        userlist.setLayoutManager(ly);
        userlist.setHasFixedSize(true);
   ChildEventListener childEventListener=new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
       User user=dataSnapshot.getValue(User.class);
       messagefb.child(user.getId());
       Chat chat=new Chat(user.getId(),"Pakokku",user.getId(),"hello","33");
       chats.add(chat);
       chatsId.add(dataSnapshot.getKey());
       mAdapter.notifyItemInserted(chats.size()-1);
      }

      @Override
      public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

      }

      @Override
      public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {




      }

      @Override
      public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
     };
     chatdb.addChildEventListener(childEventListener);


    }

}
