package com.example.kyaw.firebasetutorial.chat.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.kyaw.firebasetutorial.chat.model.Message;
import com.example.kyaw.firebasetutorial.chat.model.MessageType;
import com.example.kyaw.firebasetutorial.chat.model.Type;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUpload extends AsyncTask<Image,Void,Void> {
    Context context;
    public ImageUpload(Context context){
        this.context=context;
        mProgressDialog = new ProgressDialog(context);
    }
    private ProgressDialog mProgressDialog;
    @Override
    protected Void doInBackground(Image... images) {
        Image image=images[0];
        upload(image);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
    public void showProgressDialog() {
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Uploading...");
             mProgressDialog.show();
        Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    private void upload(final Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UUID randromid=UUID.randomUUID();
        final String path="messagesphoto/"+randromid+".jpg";
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(path);
        UploadTask uploadTask=storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference fb=FirebaseStorage.getInstance().getReference();
                fb.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("messages");
                        String downloadURI = uri.toString();
                        Message message=new Message(image.getAdminid(),image.getUserid(),Type.admin,"",ServerValue.TIMESTAMP,downloadURI,MessageType.image,false);
                        database=database.child(image.getUserid());
                        String key = database.push().getKey();
                        database.child(key).setValue(message);
                        hideProgressDialog();
                    }
                });

            }
        });


    }

}
