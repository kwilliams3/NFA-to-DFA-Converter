package com.example.kyle.nfatodfa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class TransFuncActivity extends AppCompatActivity {
    private static final String EXTRA_STATES_PARSED = "com.example.kyle.nfatodfa.MainActivity.statesParsed";
    private static final String EXTRA_SYMBOLS_PARSED = "com.example.kyle.nfatodfa.MainActivity.symbolsParsed";
    private static final String EXTRA_FINAL_STATES_PARSED = "com.example.kyle.nfatodfa.MainActivity.finalStatesParsed";

    /**
     * Returns an intent to transition to TransFuncActivity with the arguments used as extras
     * @param packageContext the context of the calling activity
     * @param statesParsed state names parsed in a string array
     * @param symbolsParsed symbols parsed in a string array
     * @param finalStatesParsed final states parsed in a string array
     * @return the intent that transitions to TransFuncActivity
     */
    public static Intent passArgsIntent(Context packageContext, String[] statesParsed,
                                        String[] symbolsParsed, String[] finalStatesParsed){
        Intent i = new Intent(packageContext, TransFuncActivity.class);
        i.putExtra(EXTRA_STATES_PARSED, statesParsed);
        i.putExtra(EXTRA_SYMBOLS_PARSED, symbolsParsed);
        i.putExtra(EXTRA_FINAL_STATES_PARSED, finalStatesParsed);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_func);
        LinearLayout transFuncInputLayout = (LinearLayout)findViewById(R.id.transFuncInputLayout);

        Intent argsIntent = getIntent();
        String[] statesParsed = argsIntent.getStringArrayExtra(EXTRA_STATES_PARSED);
        String[] symbolsParsed = argsIntent.getStringArrayExtra(EXTRA_SYMBOLS_PARSED);
        String[] finalStatesParsed = argsIntent.getStringArrayExtra(EXTRA_FINAL_STATES_PARSED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trans_func, menu);
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
}
