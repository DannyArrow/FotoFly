package com.example.danilo.cloudcamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danilo on 9/13/17.
 */

public class Controller extends Fragment {
    List<String> imgagelist = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    Button logout;
    Dao dao;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.setup, container, false);
        user = mAuth.getInstance().getCurrentUser();

        if (user == null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.start, new loginregister()).commit();

        }


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.show();




        logout = (Button) activity.findViewById(R.id.button5);









        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    mAuth.signOut();
                    //getActivity().startActivity(myIntent);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.start, new loginregister()).commit();
                }

            }
        });




        if(!user.isEmailVerified()){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.start, new Email()).commit();
        }

        // Add Fragments to adapter one by one

        adapter.addFragment(new UploadPic(), "Upload Picture");
        adapter.addFragment(new Gallery(), "Gallery");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);




        return view;
    }


}
