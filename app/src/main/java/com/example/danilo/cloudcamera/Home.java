package com.example.danilo.cloudcamera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Danilo on 9/7/17.
 */

public class Home extends Fragment{
    FirebaseUser user;
    TextView welcome;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.start, container, false);

        welcome = (TextView) view.findViewById(R.id.textView3);


        user = FirebaseAuth.getInstance().getCurrentUser();

        Dao obj = new Dao();


        // Find the view pager that will allow the user to swipe between fragments

                    return view;

    }
}
