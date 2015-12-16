package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class DFA extends FiniteAutomaton implements Parcelable {
    private HashMap<String, HashMap<String, String>> transitionTable;
    private ArrayList<DFATransition> dfaTransitions;

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        setStates(dfaParcel.createStringArray());
        setSymbols(dfaParcel.createStringArray());
        setStartState(dfaParcel.readString());
        setFinalStates(dfaParcel.createStringArray());
        setTransitionTable(dfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    private void setTransitionTable(HashMap<String, HashMap<String, String>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Fills the transitionTable and transitionsStringPartial all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states
     * @param symbols
     */
    private void setTransitionTableAndTransitionsStringPartial(String[] states, String[] symbols) {
        HashMap<String, HashMap<String, String>> transitionTable =
                new HashMap<>(states.length * symbols.length);
        dfaTransitions = new ArrayList<>();
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, String> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
                dfaTransitions.add(new DFATransition(state, symbol));
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
    public String getResultingStateFromTransitionTable(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStateFromTransitionTable(String fromState, String symbol, String toState){
        transitionTable.get(fromState).put(symbol, toState);
    }

    @Override
    public void setStates(String[] states){
        super.setStates(states);
        if (getStates() != null && getSymbols() != null){
            setTransitionTableAndTransitionsStringPartial(getStates(), getSymbols());
        }
    }

    @Override
    public void setSymbols(String[] symbols){
        super.setSymbols(symbols);
        if (getStates() != null && getSymbols() != null){
            setTransitionTableAndTransitionsStringPartial(getStates(), getSymbols());
        }
    }

    public ArrayList<DFATransition> getDFATransitions() {
        return dfaTransitions;
    }

    public void setDfaTransitions(ArrayList<DFATransition> dfaTransitions) {
        this.dfaTransitions = dfaTransitions;
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
