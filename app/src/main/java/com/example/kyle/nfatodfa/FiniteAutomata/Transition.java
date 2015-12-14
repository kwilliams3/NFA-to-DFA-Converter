package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyle on 12/8/15.
 */
abstract class Transition implements Parcelable {
    protected String fromState;
    protected String symbol;

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPartialTransitionString(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192";
    }

    abstract public String getFullTransitionString();

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromState);
        dest.writeString(symbol);
    }
}
