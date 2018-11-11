package com.example.kyaw.firebasetutorial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.LinkedList;

public class HelloRecycle extends AppCompatActivity

{
        String useremail,other_email;
        private RecyclerView recy;
        EditText editText;
        private AdapterView mAdapter;
        DatabaseReference database=FirebaseDatabase.getInstance().getReference();
        DatabaseReference messagedb=database.child("messsages");
        private LinkedList<Message> messages=new LinkedList<>();
        private LinkedList<String> messagesId=new LinkedList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    other_email = input.getText().toString();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

            setContentView(R.layout.chat_message_view);
            recy=(RecyclerView) findViewById(R.id.messages_view);
            editText=(EditText)findViewById(R.id.editText);
            useremail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            mAdapter=new AdapterView(this,messages);
            recy.setAdapter(mAdapter);
            final LinearLayoutManager ly=new LinearLayoutManager(this);
            recy.setLayoutManager(ly);
            recy.setHasFixedSize(true);
            recy.scrollToPosition(messages.size()-1);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus)
                        ly.scrollToPosition(messages.size()-1);
                }
            });
            ChildEventListener childEventListener=new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Message mg=dataSnapshot.getValue(Message.class);
                    messages.add(mg);
                    messagesId.add(dataSnapshot.getKey());
                    mAdapter.notifyItemInserted(messages.size()-1);
                    recy.scrollToPosition(messages.size()-1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Message mg=dataSnapshot.getValue(Message.class);
                    String key=dataSnapshot.getKey();
                    int index=messagesId.indexOf(key);
                    if(index>-1)
                        messages.set(index,mg);
                    mAdapter.notifyItemChanged(index);
                    recy.scrollToPosition(messages.size()-1);


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Message mg=dataSnapshot.getValue(Message.class);
                    String key=dataSnapshot.getKey();
                    int index=messagesId.indexOf(key);
                    if(index>-1) {
                        messages.remove(index);
                        messagesId.remove(index);
                        mAdapter.notifyItemRemoved(index);
                        recy.scrollToPosition(messages.size()-1);
                    }


                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            messagedb.addChildEventListener(childEventListener);

        }

    public void SendMessage(View view) {
            Message mg=new Message(useremail,editText.getText().toString(),other_email,"admin");
            String key=messagedb.push().getKey();
            messagedb.child(key).setValue(mg);
            editText.setText("");
        recy.scrollToPosition(messages.size()-1);
        Toast.makeText(this,"Send success",Toast.LENGTH_SHORT);

    }

    public void Hello(View view) {
        recy.scrollToPosition(messages.size()-1);
    }
}
