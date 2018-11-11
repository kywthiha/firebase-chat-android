package com.example.kyaw.firebasetutorial.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.model.Chat;
import com.example.kyaw.firebasetutorial.chat.viewholder.UserListViewHolder;
import java.util.LinkedList;
public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {
    private final LinkedList<Chat> userList;
    private final Context context;

    public UserListAdapter(LinkedList<Chat> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View userItem=LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_friend,parent,false);
        return new UserListViewHolder(this,userItem);
    }


    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder userListViewHolder, int pos) {
        Chat chat=userList.get(pos);
        userListViewHolder.bindToData(chat,context);

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
