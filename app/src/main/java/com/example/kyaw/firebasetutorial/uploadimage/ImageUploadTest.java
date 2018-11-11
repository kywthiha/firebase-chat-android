package com.example.kyaw.firebasetutorial.uploadimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kyaw.firebasetutorial.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUploadTest extends AppCompatActivity  {
    private static final String TAG = "Storage#MainActivity";
    private static final int RC_TAKE_PICTURE = 101;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    ImageView imv_profile;
    Button btn_choose;
    Button btn_upload;
    ProgressBar image_uploade_waiting;
    boolean isImageFitToScreen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgv_test);
        imv_profile=(ImageView)findViewById(R.id.imv_test_view);
        btn_choose=(Button) findViewById(R.id.choose);
        btn_upload=(Button)findViewById(R.id.uplaod);
        image_uploade_waiting=(ProgressBar)findViewById(R.id.loading_icon);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {

                mFileUri = data.getData();

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
    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }
    private void launchCamera() {
        Log.d(TAG, "launchCamera");

        // Pick an image from storage
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_TAKE_PICTURE);
    }
    private void uploadFromUri(Uri fileUri) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        // Save the File URI
        mFileUri = fileUri;

        // Clear the last download, if any
        mDownloadUrl = null;



        Picasso.get()

                .load(mFileUri).resize(200,200)
                .centerCrop().placeholder(R.drawable.moesetwine)
                .into(imv_profile);
          //bitmap=imv_profile.getDrawingCache();
         //   ByteArrayOutputStream out=new ByteArrayOutputStream();
          //  bitmap.compress(Bitmap.CompressFormat.PNG,1,out);
           // byte [] data=out.toByteArray();
           // String path="userpro/"+UUID.randomUUID()+".png";
          /*  StorageReference storageReference=FirebaseStorage.getInstance().getReference(path);
            UploadTask uploadTask=storageReference.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image_uploade_waiting.setVisibility(View.GONE);

                }
            });*/



        image_uploade_waiting.setVisibility(View.VISIBLE);

    }

    public void imageView(View view) {
        Toast.makeText(this,"Sucess",Toast.LENGTH_SHORT).show();


    }

    public void LanchCamera(View view) {
        launchCamera();
    }
}

