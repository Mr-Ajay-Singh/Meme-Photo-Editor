package com.cyris.createphoto.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cyris.createphoto.Adapters.MemesAdapter;
import com.cyris.createphoto.Adapters.TemplateAdapter;
import com.cyris.createphoto.CheckConnection;
import com.cyris.createphoto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Templates#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Templates extends Fragment {

    View view;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TemplateAdapter adapter;
    HashMap<String ,String> templateList;
    CardView networkErrorInTemplates;
    ProgressBar progressBarInTemplates;
    public Templates() {
        // Required empty public constructor
    }


    public static Templates newInstance(String param1, String param2) {
        Templates fragment = new Templates();
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
        view =inflater.inflate(R.layout.fragment_templates, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewInFragmentTemplate);
        progressBarInTemplates = view.findViewById(R.id.progressBarInFragmentTemplate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        networkErrorInTemplates = view.findViewById(R.id.networkErrorInTemplates);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("TemplateType");
        templateList = new HashMap<>();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(CheckConnection.checkInternet(getContext())) {

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //  Log.i("check", String.valueOf(dataSnapshot.getChildrenCount()));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        templateList.put(ds.getKey(), ds.getValue().toString());
                    }
                    progressBarInTemplates.setVisibility(View.INVISIBLE);
                    adapter = new TemplateAdapter(getContext(), templateList);
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            if(templateList.size()==0)
                networkErrorInTemplates.setVisibility(View.VISIBLE);
        }
    }
}
