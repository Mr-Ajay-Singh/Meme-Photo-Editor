package com.cyris.createphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.cyris.createphoto.Adapters.MemesAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExtraMemeTemplate extends AppCompatActivity {

    String memeType;
    FirebaseDatabase database;
    ArrayList<String> memeList;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    CardView networkErrorInExtraMemes;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_meme_template);
        getSupportActionBar().hide();
        memeType = getIntent().getStringExtra(getString(R.string.extra_meme_template));
        Toolbar toolbar = findViewById(R.id.toolbarInExtra);
        toolbar.setTitle(memeType);
        networkErrorInExtraMemes = findViewById(R.id.networkErrorInExtraMemes);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("MemeType").child(memeType);
        progressBar = findViewById(R.id.progressBarInExtraMemeTemplate);

        memeList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewInExtraMemeTemplate);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        if(CheckConnection.checkInternet(this)) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        memeList.add(ds.getValue().toString());
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(new MemesAdapter(ExtraMemeTemplate.this, memeList));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
                networkErrorInExtraMemes.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==11&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(recyclerView,"Please Click Again",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode ==11&&grantResults[0]== PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(recyclerView,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }

        if(requestCode==12&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(recyclerView,"Please Click Again",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode ==12&&grantResults[0]== PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(recyclerView,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode==13&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(recyclerView,"Please Click Again",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode ==13&&grantResults[0]== PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(recyclerView,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }

    }
}
