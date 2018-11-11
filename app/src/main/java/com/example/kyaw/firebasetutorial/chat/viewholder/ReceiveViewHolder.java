package com.example.kyaw.firebasetutorial.chat.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.model.Message;
import com.ocpsoft.pretty.time.PrettyTime;

import java.util.Date;



public class ReceiveViewHolder extends RecyclerView.ViewHolder {
    TextView my_message;
    TextView showTime;
    public ReceiveViewHolder(@NonNull View itemView) {
        super(itemView);
        my_message=(TextView)itemView.findViewById(R.id.their_message_body);
        showTime=(TextView)itemView.findViewById(R.id.time_show);
        my_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showTime.getVisibility()==View.GONE) {
                    showTime.setVisibility(View.VISIBLE);
                }
                else{
                    showTime.setVisibility(View.GONE);
                }
            }
        });
    }
    public void bindToMessage(Message msg){
        PrettyTime p = new PrettyTime();
        showTime.setText(p.format(new Date((long)msg.getTime())));
        my_message.setText(msg.getText());
    }
}
