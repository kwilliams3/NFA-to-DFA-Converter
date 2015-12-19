package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * This class isn't really necessary, but I've implemented it for organizational purposes. All
 *     NFAs and DFAs are finite automata, and I wanted to keep that hierarchy in my code.
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton implements Parcelable {
    private ArrayList<String> symbols;

    public ArrayList<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<String> symbols) {
        this.symbols = symbols;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(symbols);
    }
}
