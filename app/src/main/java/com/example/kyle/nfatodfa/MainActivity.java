package com.example.kyle.nfatodfa;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateNames = ((EditText) findViewById(R.id.stateNamesEdit)).getText().toString();
                String symbols = ((EditText) findViewById(R.id.symbolsEdit)).getText().toString();
                String startingState = ((EditText) findViewById(R.id.startStateEdit)).getText().toString();
                String finalStates = ((EditText) findViewById(R.id.finalStatesEdit)).getText().toString();
                if(stateNames.equals("") | symbols.equals("") | startingState.equals("") | finalStates.equals("")){
                    Toast.makeText(getApplicationContext(), "Please complete all fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    String[] stateNamesParsed = stateNames.split(" ");
                    String[] symbolsParsed = symbols.split(" ");
                    String[] finalStatesParsed = finalStates.split(" ");
                    for(String state : stateNamesParsed){
                        for(String symbol : symbolsParsed){
                            String hi = "\uD835\uDEFF(" + state + ", " + symbol + ") =>";
                            Toast.makeText(getApplicationContext(), hi, Toast.LENGTH_SHORT).show();
                        }
                    }
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
}
