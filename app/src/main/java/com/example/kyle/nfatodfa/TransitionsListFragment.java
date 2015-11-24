package com.example.kyle.nfatodfa;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kyle on 11/24/15.
 */
public class TransitionsListFragment extends Fragment {
    private RecyclerView transitionsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transitions_list, container, false);

        transitionsRecycler = (RecyclerView) view.findViewById(R.id.transRecyclerView);
        transitionsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
