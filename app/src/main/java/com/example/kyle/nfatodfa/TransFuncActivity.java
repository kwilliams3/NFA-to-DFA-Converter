package com.example.kyle.nfatodfa;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kyle.nfatodfa.FiniteAutomata.Transition;
import com.example.kyle.nfatodfa.TransFuncRecyclerView.TransitionsAdapter;

public class TransFuncActivity extends FragmentActivity {
    private static final String EXTRA_TRANSITIONS = "com.example.kyle.nfatodfa.MainActivity.transitions";
    private String transitions;
    private RecyclerView recyclerView;
    private TransitionsAdapter adapter;

    /**
     * Returns an intent to transition to TransFuncActivity with the arguments used as extras
     *
     * @param packageContext    the context of the calling activity
     * @param transitions transition statements in a string array
     * @return the intent that transitions to TransFuncActivity
     */
    public static Intent passArgsIntent(Context packageContext, Transition[] transitions) {
        Intent i = new Intent(packageContext, TransFuncActivity.class);
        i.putExtra(EXTRA_TRANSITIONS, transitions);
        return i;
    }

    // TODO: Fix the RecyclerView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_func);
        Intent argsIntent = getIntent();
        transitions = argsIntent.getExtras().getString(EXTRA_TRANSITIONS);
        recyclerView = (RecyclerView) findViewById(R.id.transRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransitionsAdapter(this, new String[]{});
        recyclerView.setAdapter(adapter);
    }
}
