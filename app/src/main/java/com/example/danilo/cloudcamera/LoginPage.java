package com.example.danilo.cloudcamera;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * Created by Danilo on 8/28/17.
 */

public class LoginPage extends Fragment {
    FirebaseAuth mAuth;
    EditText email1;
    EditText pass1;
    Button   login;
    TextView signuppage;
    FirebaseUser user;
    Dao dao;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.login, container, false);

        email1 = (EditText) view.findViewById(R.id.email);
        pass1 = (EditText) view.findViewById(R.id.editText2);
        login = (Button) view.findViewById(R.id.button2);
        signuppage = (TextView) view.findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();






        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final  String email = email1.getText().toString().trim();
               final String pass = pass1.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Require Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Require pass", Toast.LENGTH_SHORT).show();
                    return;
                }


                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "incorrect input, Sign in Failed" + ""+ email+"" +""+ pass + "", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Signing in Succesfully", Toast.LENGTH_SHORT).show();
                                        dao = new Dao();
                                        dao.userb = true;
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.start, new Controller()).commit();


                                    }

                                }
                            });


            }
        });


        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.start, new SignupPage()).commit();
            }
        });

        return view;
    }
}

