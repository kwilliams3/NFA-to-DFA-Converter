package com.example.kyle.nfatodfa.TransFuncRecyclerViewComponents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyle.nfatodfa.R;

/**
 * Created by kyle on 12/5/15.
 */
public class TransitionsAdapter extends RecyclerView.Adapter<TransitionViewHolder> {

    private LayoutInflater inflater;
    private String[] transitions;
    public static String[] editTextData;

    public TransitionsAdapter(Context context, String[] transitions){
        inflater = LayoutInflater.from(context);
        this.transitions = transitions;
        TransitionsAdapter.editTextData = new String[transitions.length];
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
