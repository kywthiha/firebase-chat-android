package com.example.kyaw.firebasetutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {
    private final LinkedList<User> userList;
    private final Context context;

    public UserListAdapter(LinkedList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View userItem=LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_friend,parent,false);
        return new UserListViewHolder(this, userItem);
    }


    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder userListViewHolder, int pos) {
        User u=userList.get(pos);
        userListViewHolder.bindToData(u.profileimageurl,u.name,u.time,u.message,context);

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
