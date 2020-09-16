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
import android.widget.Toast;

import com.example.popcorn.R;
import com.example.popcorn.controllers.activities.HomeActivity;
import com.example.popcorn.models.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterFragment extends Fragment {

    private EditText mRegister_email, mRegister_pass, mRegister_userName;
    private Button mBtnSignUp;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER ="user";
    private static final String TAG ="RegisterFragment";
    private User user;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_register, container, false);

        mRegister_userName = (EditText)result.findViewById(R.id.register_userName);
        mRegister_email = (EditText)result.findViewById(R.id.register_email);
        mRegister_pass = (EditText) result.findViewById(R.id.register_pass);
        mBtnSignUp =(Button) result.findViewById(R.id.btn_submit);

        database= FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  mRegister_email.getText().toString();
                String password = mRegister_pass.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Enter email or pass", Toast.LENGTH_LONG).show();
                    return;
                }
                String userName = mRegister_userName.getText().toString();
                user = new User(email, password, userName);
                registerUser(email,password);
            }
        });

        return result;
    }

    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void updateUI(FirebaseUser currentUser){
        String keyId= mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);

        FragmentManager fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment lf = new LoginFragment();
        ft.replace(R.id.fragment_login, lf);
        ft.commit();


    }


}