package com.example.danilo.cloudcamera;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Danilo on 8/28/17.
 */

public class SignupPage extends Fragment {

    EditText Email;
    EditText Pass;
    Button register;
    TextView loginpage1;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Dao obj = new Dao();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.signup, container, false);
        mAuth = FirebaseAuth.getInstance();
        Email = (EditText) view.findViewById(R.id.email);
        Pass = (EditText) view.findViewById(R.id.editText);
        register = (Button) view.findViewById(R.id.button2);
        loginpage1 = (TextView) view.findViewById(R.id.signuptxt);
        user = FirebaseAuth.getInstance().getCurrentUser();









        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String pass = Pass.getText().toString().trim();


                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(),"Require Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getContext(),"Require pass", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("hey", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Registering Failed.. try again",
                                            Toast.LENGTH_SHORT).show();
                                }
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Registering Successfully",
                                            Toast.LENGTH_SHORT).show();


                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("email", "Email sent.");
                                                        Toast.makeText(getContext(),"Please verify your email Adress",Toast.LENGTH_LONG).show();


                                                    }
                                                }

                                            });

                                }

                            }
        });


    }

        });

        loginpage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.start, new LoginPage()).commit();
            }
        });


        return view;
    }
}


