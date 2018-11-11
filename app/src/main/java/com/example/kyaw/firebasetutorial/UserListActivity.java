package com.example.kyaw.firebasetutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class UserListActivity extends AppCompatActivity {
    RecyclerView userlist;
    LinkedList<User> users=new LinkedList<>();
    UserListAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        User u1=new User("kyaw","kya@gmail.com","moesetwine.png","1 minutes","hello");
       users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);
        users.add(u1);users.add(u1);
        users.add(u1);

        users.add(u1);
        users.add(u1);
        users.add(u1);
        userlist=(RecyclerView)findViewById(R.id.user_list);
        mAdapter=new UserListAdapter(users,getApplicationContext());
        userlist.setAdapter(mAdapter);
        final LinearLayoutManager ly=new LinearLayoutManager(this);
        userlist.setLayoutManager(ly);
        userlist.setHasFixedSize(true);


    }
}
