package com.example.kyle.nfatodfa;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TransFuncActivity extends FragmentActivity {
    private static final String EXTRA_STATES_PARSED = "com.example.kyle.nfatodfa.MainActivity.statesParsed";
    private static final String EXTRA_SYMBOLS_PARSED = "com.example.kyle.nfatodfa.MainActivity.symbolsParsed";
    private static final String EXTRA_FINAL_STATES_PARSED = "com.example.kyle.nfatodfa.MainActivity.finalStatesParsed";
    private static final String EXTRA_STARTING_STATE = "com.example.kyle.nfatodfa.MainActivity.startingState";
    private String[] transitions;

    /**
     * Returns an intent to transition to TransFuncActivity with the arguments used as extras
     *
     * @param packageContext    the context of the calling activity
     * @param statesParsed      state names parsed in a string array
     * @param symbolsParsed     symbols parsed in a string array
     * @param finalStatesParsed final states parsed in a string array
     * @return the intent that transitions to TransFuncActivity
     */
    public static Intent passArgsIntent(Context packageContext, String[] statesParsed,
                                        String[] symbolsParsed, String[] finalStatesParsed, String startingState) {
        Intent i = new Intent(packageContext, TransFuncActivity.class);
        i.putExtra(EXTRA_STATES_PARSED, statesParsed);
        i.putExtra(EXTRA_SYMBOLS_PARSED, symbolsParsed);
        i.putExtra(EXTRA_FINAL_STATES_PARSED, finalStatesParsed);
        i.putExtra(EXTRA_STARTING_STATE, startingState);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_func);

        Intent argsIntent = getIntent();
        String[] statesParsed = argsIntent.getStringArrayExtra(EXTRA_STATES_PARSED);
        String[] symbolsParsed = argsIntent.getStringArrayExtra(EXTRA_SYMBOLS_PARSED);
        String[] finalStatesParsed = argsIntent.getStringArrayExtra(EXTRA_FINAL_STATES_PARSED);
        String[][] transitionComponents = new String[][]{statesParsed, symbolsParsed};
        String[] transitions;

        AsyncTask pairUpThread = new PairUpThread().execute(transitionComponents);
    }

    private class TransitionHolder extends RecyclerView.ViewHolder{
        public TextView transitionView;

        public TransitionHolder(View itemView){
            super(itemView);
            transitionView = (TextView) itemView;
        }
    }

    /*
    private class TransitionAdapter extends RecyclerView.Adapter<TransitionHolder>{
        private List<String> transitions;

        public TransitionAdapter(List<String> theTransitions){
            transitions = theTransitions;
        }

        @Override
        public TransitionHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TransitionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransitionHolder holder, int position){

        }
    }*/

    private class PairUpThread extends AsyncTask<String[], Void, String[]> {
        ProgressDialog dialog;

        protected void onPreExecute(){
            dialog = new ProgressDialog(TransFuncActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading transitions, please wait...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String[][] transitionComponents){
            // Number of transitions will be equal to number of states times number of symbols
            int numberOfTransitions = transitionComponents[0].length * transitionComponents[1].length;
            String[] transitions = new String[numberOfTransitions];
            int stringArrayIndex = 0;
            for(int i=0; i<transitionComponents[0].length; i++){
                for(int j=0; j<transitionComponents[1].length; j++) {
                    String transitionText = "\uD835\uDEFF(" + transitionComponents[0][i] + ", " +
                            transitionComponents[1][j] + ") \u2192";
                    transitions[stringArrayIndex] = transitionText;
                    stringArrayIndex++;
                }
            }

            // String array containing the strings of transitions
            // to be used in textviews in the recyclerview
            return transitions;
        }

        protected void onPostExecute(String[] result){
            transitions = result;
            dialog.dismiss();

        }
    }
}
