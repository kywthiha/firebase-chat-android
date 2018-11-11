package com.example.kyaw.firebasetutorial;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;

public class AdapterView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  final LinkedList<Message> mg;
    private final LayoutInflater context;

    public AdapterView(Context conext, LinkedList<Message> mg) {
        this.mg = mg;
        this.context = LayoutInflater.from(conext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sendView=context.inflate(R.layout.my_message,parent,false);
        View receiveView=context.inflate(R.layout.their_message,parent,false);
        if(viewType==1) {
            SendViewHolder sendViewHolder = new SendViewHolder(sendView);
            return sendViewHolder;
        }
            ReceiveViewHolder receiveViewHolder=new ReceiveViewHolder(receiveView);
            return receiveViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message m=mg.get(i);
        switch (viewHolder.getItemViewType()){
            case 1:
                final SendViewHolder sendViewHolder=(SendViewHolder)viewHolder;
                sendViewHolder.bindToMessage(m);
                break;
            case 2:
                final ReceiveViewHolder receiveViewHolder=(ReceiveViewHolder) viewHolder;
                receiveViewHolder.bindToMessage(m);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Message m=mg.get(position);
        if(m.userid.equals(getUserId())){
            return 1;
        }
        return 2;
    }

    public String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
    @Override
    public int getItemCount() {
        return mg.size();
    }
}
