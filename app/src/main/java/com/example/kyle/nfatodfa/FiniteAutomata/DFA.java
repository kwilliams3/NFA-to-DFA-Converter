package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class DFA extends FiniteAutomaton implements Parcelable {
    private DFATransition[] transitionTable;

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        setStates(dfaParcel.createStringArray());
        setSymbols(dfaParcel.createStringArray());
        setStartState(dfaParcel.readString());
        setFinalStates(dfaParcel.createStringArray());
        setTransitionTable(dfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    private void setTransitionTable(DFATransition[] transitionTable) {
        this.transitionTable = transitionTable;
    }

    private void setTransitionTable(String[] states, String[] symbols) {
        DFATransition[] transitionTable =
                new DFATransition[states.length * symbols.length];
        // Filling transitionTable
        int indexCounter = 0;
        for (String state : states) {
            for (String symbol: symbols) {
                transitionTable[indexCounter] = new DFATransition(state, symbol);
                indexCounter++;
            }
        }
        this.transitionTable = transitionTable;
    }

    /**
     * Returns the resulting state that is reached after processing the transition
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return the resulting state that is reached after processing the transition
     */
    public String getResultingState(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    /**
     * Returns a string that states all or part of a transition from the automaton
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return string stating a transition, includes the resulting state only if any such state is known
     */
    public String getTransitionString(String fromState, String symbol) {
        String transitionString;
        String toState = getResultingState(fromState, symbol);

        if (toState == null) {
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192";
        } else {
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192" + toState;
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

    public static final Parcelable.Creator<DFA> CREATOR
            = new Parcelable.Creator<DFA>() {
        public DFA createFromParcel(Parcel dfaParcel) {
            return new DFA(dfaParcel);
        }

        public DFA[] newArray(int size) {
            return new DFA[size];
        }
    };
}
