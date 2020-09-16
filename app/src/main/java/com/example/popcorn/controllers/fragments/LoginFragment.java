package com.example.popcorn.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popcorn.R;
import com.example.popcorn.controllers.activities.AuthActivity;
import com.example.popcorn.controllers.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private EditText mLogin_email, mLogin_password;
    private Button mBtnSignin, mbtnSubmitIntent;


    private FirebaseAuth mAuth;
    private static final String TAG ="LoginFragment";

    public LoginFragment() {

    }


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_login, container, false);

        mLogin_email = (EditText)result.findViewById(R.id.edt_login_email);
        mLogin_password = (EditText) result.findViewById(R.id.edt_login_password);
        mbtnSubmitIntent = (Button) result.findViewById(R.id.btn_submit_intent);
        mBtnSignin =(Button) result.findViewById(R.id.btn_login);

        //SignUp
        mbtnSubmitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                RegisterFragment llf = new RegisterFragment();
                ft.replace(R.id.fragment_login, llf);
                ft.commit();
            }
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLogin_email.getText().toString();
                String password= mLogin_password.getText().toString();
                if (TextUtils.isEmpty((email))){
                    Toast.makeText(getActivity(),R.string.invalid_email , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((password))){
                    Toast.makeText(getActivity(), R.string.invalid_pass, Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
        return result;

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }

    }

    public void updateUI(FirebaseUser currentUser){
        Intent homeIntent  = new Intent(getContext(), HomeActivity.class);
        homeIntent.putExtra("email", currentUser.getEmail());
        startActivity(homeIntent);
        //azerty1234
    }



}