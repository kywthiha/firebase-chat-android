package com.example.kyaw.firebasetutorial.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.model.Message;
import com.example.kyaw.firebasetutorial.chat.model.MessageType;
import com.example.kyaw.firebasetutorial.chat.model.Type;
import com.example.kyaw.firebasetutorial.chat.viewholder.ReceiveImageViewHolder;
import com.example.kyaw.firebasetutorial.chat.viewholder.ReceiveViewHolder;
import com.example.kyaw.firebasetutorial.chat.viewholder.SendImageViewHolder;
import com.example.kyaw.firebasetutorial.chat.viewholder.SendViewHolder;

import java.util.LinkedList;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  final LinkedList<Message> mg;
    private final Context context;
    private final int SEND_VIEW=1;
    private final int RECEIVE_VIEW=2;
    private final int SEND_IMAGE_VIEW=3;
    private final int RECEIVE_IMAGE_VIEW=4;

    public MessageAdapter(Context conext, LinkedList<Message> mg) {
        this.mg = mg;
        this.context = conext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sendView=LayoutInflater.from(context).inflate(R.layout.my_message,parent,false);
        View receiveView=LayoutInflater.from(context).inflate(R.layout.their_message,parent,false);
        View sendImage=LayoutInflater.from(context).inflate(R.layout.my_image,parent,false);
        View receiveImage=LayoutInflater.from(context).inflate(R.layout.their_image_message,parent,false);
        if(viewType==SEND_VIEW) {
            SendViewHolder sendViewHolder = new SendViewHolder(sendView);
            return sendViewHolder;
        }else if(viewType==SEND_IMAGE_VIEW){
            SendImageViewHolder sendImageViewHolder=new SendImageViewHolder(sendImage,context);
            return sendImageViewHolder;
        }
        else if(viewType==RECEIVE_IMAGE_VIEW){
            ReceiveImageViewHolder receiveImageViewHolder=new ReceiveImageViewHolder(receiveImage,context);
            return receiveImageViewHolder;
        }

            ReceiveViewHolder receiveViewHolder=new ReceiveViewHolder(receiveView);
            return receiveViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Message m=mg.get(i);
        switch (viewHolder.getItemViewType()){
            case SEND_VIEW:
                final SendViewHolder sendViewHolder=(SendViewHolder)viewHolder;
                sendViewHolder.bindToMessage(m);
                break;
            case RECEIVE_VIEW:
                final ReceiveViewHolder receiveViewHolder=(ReceiveViewHolder) viewHolder;
                receiveViewHolder.bindToMessage(m);
                break;
            case SEND_IMAGE_VIEW:
                final SendImageViewHolder sendImageViewHolder=(SendImageViewHolder)viewHolder;
                sendImageViewHolder.bindToImage(m);
                break;
            case RECEIVE_IMAGE_VIEW:
                final ReceiveImageViewHolder receiveImageViewHolder=(ReceiveImageViewHolder)viewHolder;
                receiveImageViewHolder.bindToImage(m);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Message m=mg.get(position);
        if(m.getType().equals(Type.admin) && m.getMessageType().equals(MessageType.text)){
            return SEND_VIEW;
        }
        else if(m.getType().equals(Type.admin) && m.getMessageType().equals(MessageType.image)){
            return SEND_IMAGE_VIEW;
        }
        else if(m.getType().equals(Type.user) && m.getMessageType().equals(MessageType.image)){
            return RECEIVE_IMAGE_VIEW;
        }
        return RECEIVE_VIEW;
    }

    @Override
    public int getItemCount() {
        return mg.size();
    }
}
