package com.example.danilo.cloudcamera;

import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Danilo on 9/8/17.
 */

public class Email extends Fragment {
    FirebaseUser user;
Button Verify;
    Button home;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.email, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.hide();
        home = (Button) view.findViewById(R.id.button4);
        Verify = (Button) view.findViewById(R.id.button3);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("email", "Email sent.");
                                    }
                                }
                            });
                }
            }
        });
        if(user.isEmailVerified()){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.start, new Controller()).commit();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.start, new loginregister()).commit();
            }
        });

return view;
    }
}