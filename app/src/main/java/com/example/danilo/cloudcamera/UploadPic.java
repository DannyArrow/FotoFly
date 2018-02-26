package com.example.danilo.cloudcamera;



import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Danilo on 9/1/17.
 */
public class UploadPic extends Fragment {

    private Button logout;
    private FirebaseAuth mAuth;
    public UploadPic(){

    }

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_CAMERA = 1888;
    public static final int RESULT_OK = -1;


    Uri outPutfileUri;
    Button b1;
    Button camera;
    String stamp;
    ImageView imageView;
    Controller img;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload, container, false);

        user = mAuth.getInstance().getCurrentUser();
        img = new Controller();
        b1 = (Button) view.findViewById(R.id.gallery);
        camera = (Button) view.findViewById(R.id.camera);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        database = FirebaseDatabase.getInstance();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        logout = (Button) activity.findViewById(R.id.button5);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    mAuth.signOut();
                    //getActivity().startActivity(myIntent);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.start, new loginregister()).commit();
                }

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());


                stamp = String.valueOf(currentTimestamp);
                //Camera permission required for Marshmallow version

                // permission has been granted, continue as usual
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());


                stamp = String.valueOf(currentTimestamp);
                Log.e("hey", stamp);

                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        return view;
    }

    private void uploadtocloud(final String filee)  {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(filee));
       final StorageReference riversRef = mStorageRef.child(filee);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("sucessfully", "good job");
                        //StorageReference riversRef = mStorageRef.child(filee);

//                        Glide.with(getContext())
//                                .using(new FirebaseImageLoader())
//                                .load(riversRef)
//                                .into(imageView);

                        myRef = database.getReference();
                        myRef.push().setValue(taskSnapshot.getDownloadUrl().toString());



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
















    /*public void download(String filee) throws IOException {
        StorageReference riversRef = mStorageRef.child(filee);
        Glide.with(this )
                .using(new FirebaseImageLoader())
                .load(riversRef)
                .into(imageView);
        img.imgagelist.add(imageView);

    }*/


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        final File mypath=new File(directory,"profile.jpg"+stamp);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            uploadtocloud(String.valueOf(mypath));



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_CAMERA) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveToInternalStorage(photo);
           // imageView.setImageBitmap(photo);
            //saveToInternalStorage(photo);




        }

        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                saveToInternalStorage(bitmap);

                // Log.d(TAG, String.valueOf(bitmap));

                // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}




