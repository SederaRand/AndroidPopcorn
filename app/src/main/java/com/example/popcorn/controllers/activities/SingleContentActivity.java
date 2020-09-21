package com.example.popcorn.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popcorn.R;
import com.example.popcorn.models.Content;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleContentActivity extends AppCompatActivity implements View.OnClickListener{


    private DatabaseReference ref;

    private ImageView mSingleViewImage;
    private TextView mSingleViewTitle, mSingleViewDetails;
    private Button mSingleViewBtn;
    private Content mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_content);

        ref = FirebaseDatabase.getInstance().getReference().child("content");

        mSingleViewTitle = (TextView) findViewById(R.id.post_single_title);
        mSingleViewDetails = (TextView) findViewById(R.id.post_single_details);
        mSingleViewImage = (ImageView) findViewById(R.id.post_single_image);
        mSingleViewBtn = (Button) findViewById(R.id.return_button);

        mSingleViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), HomeActivity.class);
                startActivity(intent);
            }
        });

        mContent = new Content();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mContent.setTitle(getIntent().getStringExtra("ITEM_TITLE"));
        mContent.setDetails(getIntent().getStringExtra("ITEM_DETAILS"));
        mContent.setImage(getIntent().getStringExtra("ITEM_IMAGE"));

        mSingleViewTitle.setText(mContent.getTitle());
        mSingleViewDetails.setText(mContent.getDetails());
        Picasso.with(this).load(mContent.getImage()).into(mSingleViewImage);

    }

    @Override
    public void onClick(View view) {

    }
}