package com.example.kyle.nfatodfa.TransFuncRecyclerViewComponents;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kyle.nfatodfa.R;

/**
 * Created by kyle on 12/8/15.
 */
public class TransitionViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public EditText editText;
    public MyCustomEditTextListener myCustomEditTextListener;

    public TransitionViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        editText = (EditText) itemView.findViewById(R.id.editText);
        myCustomEditTextListener = new MyCustomEditTextListener();
        editText.addTextChangedListener(myCustomEditTextListener);

    }

    public void updateTextWatcherPosition(int position){
        myCustomEditTextListener.updatePosition(position);
    }

    /**
     * Used to keep track of the text entered into an EditText within the RecyclerView
     * Credit for this idea goes to "dkarmazi" for his answer on StackOverflow
     */
    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            TransitionsAdapter.editTextData[position] = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }
}