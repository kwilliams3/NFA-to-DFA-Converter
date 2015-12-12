package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class NFA extends FiniteAutomaton implements Parcelable {
    private HashMap<String, HashMap<String, String[]>> transitionTable;

    public NFA() {}

    public NFA(NFA nfa) {

    }

    public HashMap<String, HashMap<String, String[]>> getTransitionTable() {
        return transitionTable;
    }

    private void setTransitionTable(String[] states, String[] symbols) {
        HashMap<String, HashMap<String, String[]>> transitionTable =
                new HashMap<>((states.length * symbols.length));
        // Filling transitionTable
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, String[]> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
            }
        }
        this.transitionTable = transitionTable;
    }

    /**
     * Returns the resulting states that can be reached after processing the transition
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return array containing resulting states that can be reached after processing the transition
     */
    public String[] getResultingStates(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    /**
     * Returns a string that states all or part of a transition from the automaton
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return string stating a transition, includes the set of resulting states only if any such states are known
     */
    public String getTransitionString(String fromState, String symbol) {
        String transitionString;
        String[] toStates = getResultingStates(fromState, symbol);

        if (toStates == null) {
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192";
        } else {
            String toStatesString = "";
            for (String toState : toStates){
                toStatesString = toStatesString + toState + " ";
            }
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192" + toStatesString.trim();
        }

        return transitionString;
    }

    @Override
    public void setStates(String[] states){
        super.setStates(states);
        if (getSymbols() != null){
            setTransitionTable(getStates(), getSymbols());
        }
    }

    @Override
    public void setSymbols(String[] symbols){
        super.setSymbols(symbols);
        if (getStates() != null){
            setTransitionTable(getStates(), getSymbols());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeMap(transitionTable);
    }

    public static final Parcelable.Creator<NFA> CREATOR
            = new Parcelable.Creator<NFA>() {
        public NFA createFromParcel(Parcel nfaParcel) {
            return new NFA(nfaParcel);
        }

        public NFA[] newArray(int size) {
            return new NFA[size];
        }
    };
}
