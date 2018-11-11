package com.example.kyaw.firebasetutorial.ImageTest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kyaw.firebasetutorial.R;

public class ImageTestActivite extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    private static final String TAG=ImageTestActivite.class.getName();
    private static final int RC_TAKE_PICTURE = 101;
    String  mFileUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.image_uploat_test);
        img=(ImageView)findViewById(R.id.image_moe);
        img.setOnClickListener(this);
        findViewById(R.id.btn_upload).setOnClickListener(this);
        if (savedInstanceState != null) {
            mFileUri=savedInstanceState.getString("urll");
        }else
        {
            mFileUri="moe";
        }




    }
    private void showImagePreview(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance(mFileUri);
        newFragment.show(ft, "slideshow");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                 mFileUri=data.getData().toString();

                if (mFileUri != null) {
                    uploadFromUri(mFileUri);
                } else {
                    Log.w(TAG, "File URI is null");
                }
            } else {
                Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCamera() {
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "launchCamera");

        // Pick an image from storage
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_TAKE_PICTURE);
    }
    private void uploadFromUri(String uri){
        Glide.with(this).load(uri).into(img);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_upload){
            launchCamera();

        }
        else
        if(v.getId()==R.id.image_moe){
            showImagePreview();
        }

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        if(savedInstanceState.containsKey("urll")){
            Glide.with(this).load(savedInstanceState.getString("urll")).into(img);

        }

        //mTextView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
        // outState.putString(GAME_STATE_KEY, mGameState);
            outState.putString("urll",mFileUri.toString());

    }
}
