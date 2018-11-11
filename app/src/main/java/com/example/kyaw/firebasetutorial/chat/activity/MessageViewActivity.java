package com.example.kyaw.firebasetutorial.chat.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.kyaw.firebasetutorial.R;
import com.example.kyaw.firebasetutorial.chat.adapter.MessageAdapter;
import com.example.kyaw.firebasetutorial.chat.model.Chat;
import com.example.kyaw.firebasetutorial.chat.model.Message;
import com.example.kyaw.firebasetutorial.chat.model.MessageType;
import com.example.kyaw.firebasetutorial.chat.model.Type;
import com.example.kyaw.firebasetutorial.chat.model.User;
import com.example.kyaw.firebasetutorial.chat.utility.FileUtil;
import com.example.kyaw.firebasetutorial.chat.utility.ImageUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class MessageViewActivity extends AppCompatActivity implements View.OnClickListener

{
    private RecyclerView recy;
    LinearLayoutManager ly;
    EditText editText;
    private static final int showItem=5;
    int itempos=0,item=0;
    SwipeRefreshLayout swipeRefreshLayout;
    private static String lastitem="";
    private MessageAdapter mAdapter;
    ImageButton choose_iamge_or_camera;
    LinearLayoutCompat show;
    boolean moreItem=true;
    User user;
    Chat chat;
    private static final String TAG = "Storage#MainActivity";
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final int RC_TAKE_PICTURE = 101;
    private static final int CAMERA_REQUEST = 893;
    private static final int CAMERA_PER_REQUEST=342;
    private static final int STORAGE_PER_REQUEST=3478;
    private LinkedList<Message> messages = new LinkedList<>();
    private LinkedList<String> messagesId = new LinkedList<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    File actualImage = FileUtil.from(this, data.getData());
                    ImageUtil imgu = new ImageUtil(actualImage, this, user.getId(), "Pakokku");
                    imgu.uploadImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {

                File actualImage = null;
                try {
                    actualImage = FileUtil.from(this,((Bitmap)data.getExtras().get("data")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageUtil imgu = new ImageUtil(actualImage, this, user.getId(), "Pakokku");
                imgu.uploadImage();

            } else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void launchCamera() {
        Log.d(TAG, "launchCamera");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_TAKE_PICTURE);
    }
    private void loadMoreData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("messages").child(user.getId());
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(messagesId.indexOf(dataSnapshot.getKey())==-1) {
                    if (lastitem.equals(dataSnapshot.getKey())) {
                        moreItem=false;
                        lastitem="";
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        Message mg = dataSnapshot.getValue(Message.class);
                        messagesId.add(itempos,dataSnapshot.getKey());
                        messages.add(itempos++, mg);
                        moreItem=false;
                        if (itempos == 1) {
                            lastitem = dataSnapshot.getKey();
                        }
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                if(!moreItem){
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        Query query=database.orderByKey().endAt(lastitem).limitToLast(showItem);
        query.addChildEventListener(childEventListener);
    }




    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat_message_view);
            recy=(RecyclerView) findViewById(R.id.messages_view);
            editText=(EditText)findViewById(R.id.footer_bar).findViewById(R.id.editText);
            show = (LinearLayoutCompat) findViewById(R.id.chooe_image_oe_camera);
            choose_iamge_or_camera = (ImageButton) findViewById(R.id.add_btn);
            choose_iamge_or_camera.setOnClickListener(this);
            swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
           final LinearLayoutManager  ly=new LinearLayoutManager(this);
            mAdapter=new MessageAdapter(this,messages);
            recy.setAdapter(mAdapter);
            recy.setLayoutManager(ly);
            recy.setHasFixedSize(true);
            recy.scrollToPosition(messages.size()-1);
            if(savedInstanceState==null){
                Intent intent = this.getIntent();
                Bundle bundle = intent.getExtras();
                chat=(Chat)bundle.getSerializable("chat");
                user=new User();
                user.setId(chat.getUserid());
                getSupportActionBar().setTitle(chat.getUserid());
                Toast.makeText(this,chat.getAdminid(),Toast.LENGTH_SHORT).show();
            }
        loadData();
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() != null && !editText.getText().toString().trim().equals("")) {
                    SendMessage(v);

                }

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itempos=0;
                loadMoreData();

            }
        });


        }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_btn) {
            if (show.getVisibility() == View.GONE) {
                show.setVisibility(View.VISIBLE);
                choose_iamge_or_camera.setImageResource(R.drawable.ic_clear);

            } else if (show.getVisibility() == View.VISIBLE) {
                show.setVisibility(View.GONE);
                choose_iamge_or_camera.setImageResource(R.drawable.ic_add_black);
            }
        }
    }
    private void loadData(){
        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("messages").child(user.getId());
        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message mg=dataSnapshot.getValue(Message.class);
                messages.add(mg);
                messagesId.add(dataSnapshot.getKey());
                itempos++;
                if(itempos==1){
                    lastitem=dataSnapshot.getKey();
                }

                mAdapter.notifyItemInserted(messages.size()-1);
                recy.scrollToPosition(messages.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {



            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query=database.orderByKey().limitToLast(showItem);
        query.addChildEventListener(childEventListener);
    }


    public void SendMessage(View view) {
        if (show.getVisibility() == View.VISIBLE) {
            show.setVisibility(View.GONE);
            choose_iamge_or_camera.setImageResource(R.drawable.ic_add_black);
        }
        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("messages").child(user.getId());

        Message mg = new Message(user.getId(), user.getId(), Type.admin, editText.getText().toString(),ServerValue.TIMESTAMP,"",MessageType.text, false);
        String key = database.push().getKey();
        database.child(key).setValue(mg);
        editText.setText("");
        recy.scrollToPosition(messages.size() - 1);
        Toast.makeText(this, "Send success", Toast.LENGTH_SHORT);

    }

    public void btnAddImage(View view) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

            // Permission is granted
            launchCamera();
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {
               Toast.makeText(this,"Storage permension is needed to post image",Toast.LENGTH_SHORT).show();

            }

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PER_REQUEST);



        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PER_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case CAMERA_PER_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED ) {
                    // Permission is granted
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public void openCamera(View view) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

            // Permission is granted
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,"Storage permension amd Camera permension is needed to post image",Toast.LENGTH_SHORT).show();

            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PER_REQUEST);



        }
        Log.d(TAG, "launchCamera");


    }
}
