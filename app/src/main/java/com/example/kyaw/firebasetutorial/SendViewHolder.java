package com.example.kyaw.firebasetutorial;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SendViewHolder extends RecyclerView.ViewHolder {
    TextView my_message;
    public SendViewHolder(@NonNull View itemView) {
        super(itemView);
        my_message=(TextView)itemView.findViewById(R.id.my_message_body);
    }
    public void bindToMessage(Message msg){
        my_message.setText(msg.messagetext);
    }
}

