package com.example.kyle.nfatodfa.TransFuncRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kyle.nfatodfa.R;

/**
 * @author Kyle Williams
 * @since 12/8/15
 */
class TransitionViewHolder extends RecyclerView.ViewHolder {

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
     * Used to keep track of the text a user enters into an EditText within the RecyclerView
     * so that the text can be recalled even after the EditText is reused for different View
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