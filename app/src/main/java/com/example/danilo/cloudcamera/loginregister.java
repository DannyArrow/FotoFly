package com.example.danilo.cloudcamera;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Danilo on 9/4/17.
 */

public class loginregister extends Fragment {
    Button login;
    Button signup;
    Button logout;
    FirebaseAuth mAuth;
Dao dao;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.main, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        dao = new Dao();




        if(user != null){
            //user is sign in
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.start, new Controller()).commit();
        }


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.hide();


        login = (Button) view.findViewById(R.id.gallery);
        signup = (Button) view.findViewById(R.id.signup);





            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.start, new LoginPage()).commit();

                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment signupFragment = new SignupPage();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.start, signupFragment);
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();

                }
            });

            return view;
        }

    }


