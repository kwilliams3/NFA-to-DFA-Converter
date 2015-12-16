package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton implements Parcelable {
    private String[] states;
    private String[] symbols;
    private String startState;
    private String[] finalStates;

    public String[] getStates() {
        return states;
    }

    public void setStates(String[] states) {
        this.states = states;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String[] getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(String[] finalStates) {
        this.finalStates = finalStates;
    }

    public int getNumberOfTransitions() {
        if (states != null && symbols != null) {
            return (states.length * symbols.length);
        } else {return 0;}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(states);
        dest.writeStringArray(symbols);
        dest.writeString(startState);
        dest.writeStringArray(finalStates);
    }
}
