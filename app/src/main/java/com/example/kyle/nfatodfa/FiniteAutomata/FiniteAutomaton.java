package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton implements Parcelable {
    private String[] symbols;

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(symbols);
    }
}
