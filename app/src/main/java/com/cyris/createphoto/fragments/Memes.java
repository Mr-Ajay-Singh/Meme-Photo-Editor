package com.cyris.createphoto.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cyris.createphoto.Adapters.MemesAdapter;
import com.cyris.createphoto.CheckConnection;
import com.cyris.createphoto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Memes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Memes extends Fragment  {


    View view;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> memesList;
    ProgressBar progressBar;
    MemesAdapter adapter;
    CardView networkErrorInMemes;
    public Memes() {
        // Required empty public constructor
    }


    public static Memes newInstance(String param1, String param2) {
        Memes fragment = new Memes();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_memes, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewInFragmentMemes);
        networkErrorInMemes = view.findViewById(R.id.networkErrorInMemes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        progressBar = view.findViewById(R.id.progressBarInFragmentMeme);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Memes");
        memesList = new ArrayList<>();


        if(CheckConnection.checkInternet(getContext())) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.i("check", String.valueOf(dataSnapshot.getChildrenCount()));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        memesList.add(ds.getValue().toString());
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new MemesAdapter(getContext(), memesList);
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            networkErrorInMemes.setVisibility(View.VISIBLE);
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

}
