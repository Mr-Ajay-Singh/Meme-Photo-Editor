package com.cyris.createphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainTemplate extends AppCompatActivity {

    // Write a message to the database
    ImageView imageView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> dataCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_template);
        dataCheck = new ArrayList<>();
        imageView = findViewById(R.id.imageInMainTemplate);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Popular");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("check", String.valueOf(dataSnapshot.getChildrenCount()));
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    dataCheck.add(ds.getValue().toString());
                }
                Log.i("check2", String.valueOf(dataCheck.size()));
                    Picasso.get().load(dataCheck.get(1)).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
