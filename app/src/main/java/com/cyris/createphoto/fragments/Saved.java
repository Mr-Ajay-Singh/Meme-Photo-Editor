package com.cyris.createphoto.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyris.createphoto.Adapters.SavedAdapter;
import com.cyris.createphoto.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Saved#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Saved extends Fragment {

    View view;
    RecyclerView recyclerView;
    CardView noSavedFile;
    public Saved() {
        // Required empty public constructor
    }


    public static Saved newInstance(String param1, String param2) {
        Saved fragment = new Saved();
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
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewInSaved);
        noSavedFile = view.findViewById(R.id.noSavedFile);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(new SavedAdapter(getContext(),noSavedFile));

    }
}
