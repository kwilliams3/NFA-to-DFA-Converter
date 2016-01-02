package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Set;

/**
 * This class isn't truly necessary, but I've implemented it for organizational purposes.
 * All NFAs and DFAs are finite automata, and I wanted to keep that hierarchy in my code.
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton implements Parcelable {
    private Set<String> symbols;

    public Set<String> getSymbols() {
        return symbols;
    }

    void setSymbols(Set<String> symbols) {
        this.symbols = symbols;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] symbols = new String[this.symbols.size()];
        dest.writeStringArray(this.symbols.toArray(symbols));
    }
}