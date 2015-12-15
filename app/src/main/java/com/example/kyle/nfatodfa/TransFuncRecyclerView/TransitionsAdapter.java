package com.example.kyle.nfatodfa.TransFuncRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyle.nfatodfa.FiniteAutomata.NFA;
import com.example.kyle.nfatodfa.R;

import java.util.HashMap;

/**
 * Created by kyle on 12/5/15.
 */
public class TransitionsAdapter extends RecyclerView.Adapter<TransitionViewHolder> {

    private LayoutInflater inflater;
    private NFA nfa;
    private String[] transitions;
    public static String[] editTextData;

    public TransitionsAdapter(Context context, NFA nfa){
        inflater = LayoutInflater.from(context);
        transitions = nfa.getTransitionsStringArrayPartial();
        TransitionsAdapter.editTextData = new String[nfa.getNumberOfTransitions()];
    }

    @Override
    public TransitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.transition, parent, false);
        return new TransitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransitionViewHolder holder, int position) {
        holder.textView.setText(transitions[position]);
        holder.updateTextWatcherPosition(position);
        holder.editText.setText(editTextData[position]);
    }

    @Override
    public int getItemCount() {
        return editTextData.length;
    }
}
