package com.example.danilo.cloudcamera;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private LinearLayoutManager grid;
    private Gallery.MyAdapter mAdapter;
    private ImageView input;


    @Override
    public void onBackPressed() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);







/*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("val", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("vall", "Failed to read value.", error.toException());
            }
        });
*/




//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.start, new loginregister()).commit();

        Fragment loginregister = new loginregister();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.start, loginregister);
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();

//        super.onBackPressed();

    }

}










