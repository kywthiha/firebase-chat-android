package com.example.kyaw.firebasetutorial;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ReceiveViewHolder extends RecyclerView.ViewHolder {
    TextView my_message;
    public ReceiveViewHolder(@NonNull View itemView) {
        super(itemView);
        my_message=(TextView)itemView.findViewById(R.id.their_message_body);
    }
    public void bindToMessage(Message msg){
        my_message.setText(msg.messagetext);
    }
}
