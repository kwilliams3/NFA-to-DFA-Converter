package com.example.kyle.nfatodfa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TransFuncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_func);
        Intent mainActivityVars = getIntent();
        String[] stateNames = mainActivityVars.getStringArrayExtra("stateNamesParsed");
        String[] symbols = mainActivityVars.getStringArrayExtra("symbolsParsed");
        String[] finalStates = mainActivityVars.getStringArrayExtra("finalStates");
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.transFuncLinearLayout);
        addTransFuncViews(myLayout, stateNames, symbols);
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

    private void addTransFuncViews(LinearLayout myLayout, String[] stateNames, String[] symbols){
        // -------------------------USE AN ARRAY ADAPTER INSTEAD-------------------------
        /*
        for (String state : stateNames) {
            for (String symbol : symbols) {
                View tempTextViewLayout = getLayoutInflater().inflate(R.layout.transition_function_textview, null);
                TextView tempTextView = (TextView)tempTextViewLayout.findViewById(R.id.templateForInflation);
                tempTextView.setText("\uD835\uDEFF(" + state + ", " + symbol + ") =>");
                myLayout.addView(tempTextView);
            }
        }
        */
    }
}
