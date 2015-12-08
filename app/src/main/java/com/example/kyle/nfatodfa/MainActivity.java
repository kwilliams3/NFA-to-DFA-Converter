package com.example.kyle.nfatodfa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private String[] statesParsed;
    private String[] symbolsParsed;
    private String[] finalStatesParsed;
    private String[][] transitionComponents;
    private String[] transitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String states = ((EditText) findViewById(R.id.stateNamesEdit)).getText().toString();
                // Adds epsilon to the alphabet
                String symbols = "ϵ " + ((EditText) findViewById(R.id.symbolsEdit)).getText().toString();
                String startingState = ((EditText) findViewById(R.id.startStateEdit)).getText().toString();
                String finalStates = ((EditText) findViewById(R.id.finalStatesEdit)).getText().toString();
                if(states.equals("") | symbols.equals("") | startingState.equals("") | finalStates.equals("")){
                    Toast.makeText(getApplicationContext(), R.string.incompleteError,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    statesParsed = states.split(" ");
                    symbolsParsed = symbols.split(" ");
                    finalStatesParsed = finalStates.split(" ");
                    transitionComponents = new String[][]{statesParsed, symbolsParsed};
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle("Loading");
                    dialog.setMessage("Loading transitions, please wait...");
                    dialog.setCancelable(false);
                    dialog.setIndeterminate(true);
                    dialog.show();
                    pairUp(transitionComponents);
                    dialog.dismiss();
                    Intent transFuncActivity = TransFuncActivity.passArgsIntent(getApplicationContext(), transitions);
                    startActivity(transFuncActivity);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void pairUp(String[][] transitionComponents){
        // Number of transitions will be equal to number of states times number of symbols
        int numberOfTransitions = transitionComponents[0].length * transitionComponents[1].length;
        transitions = new String[numberOfTransitions];
        int stringArrayIndex = 0;
        for(int i=0; i<transitionComponents[0].length; i++){
            for(int j=0; j<transitionComponents[1].length; j++) {
                String transitionText = "\uD835\uDEFF(" + transitionComponents[0][i] + ", " +
                        transitionComponents[1][j] + ") \u2192";
                transitions[stringArrayIndex] = transitionText;
                stringArrayIndex++;
            }
        }
    }
}
