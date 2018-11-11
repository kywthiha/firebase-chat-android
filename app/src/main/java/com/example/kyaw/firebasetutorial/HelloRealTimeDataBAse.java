package com.example.kyaw.firebasetutorial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HelloRealTimeDataBAse extends AppCompatActivity {

    EditText name;
    EditText email;
    TextView showname;
    TextView showeamil;
    DatabaseReference dr=FirebaseDatabase.getInstance().getReference();
    DatabaseReference mdb=dr.child("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_real_time_data_base);
        showname=(TextView)findViewById(R.id.showname);
        showeamil=(TextView)findViewById(R.id.showemail);
        name= (EditText)findViewById(R.id.txtName);
        email=(EditText)findViewById(R.id.txtEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //ArrayList<User> ulist=new ArrayList<>();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                //   User user = singleSnapshot.getValue(User.class);
                    //ulist.add(user);
                }
               // showname.setText(ulist.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /*private void addNewUser(String username,String email){
        User user=new User(username,email);
        mdb.child(mdb.push().getKey()).setValue(user);

    }*/
    public void Add(View view) {
        String n=name.getText().toString();
        String e=email.getText().toString();
        //addNewUser(n,e);
    }
}
