package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kyle on 12/9/15.
 */
public class NFA extends FiniteAutomaton implements Parcelable {
    private HashMap<String, HashMap<String, String[]>> transitionTable;
    private String[] transitionsStringArrayPartial;

    public NFA() {}

    @SuppressWarnings("unchecked")
    public NFA(Parcel nfaParcel) {
        setStates(nfaParcel.createStringArray());
        setSymbols(nfaParcel.createStringArray());
        setStartState(nfaParcel.readString());
        setFinalStates(nfaParcel.createStringArray());
        setTransitionTable(nfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    private void setTransitionTable(HashMap<String, HashMap<String, String[]>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Fills the transitionTable and transitionsStringPartial all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of states in the nfa
     * @param symbols string array of symbols in the nfa
     */
    private void setTransitionTableAndTransitionsStringPartial(String[] states, String[] symbols) {
        HashMap<String, HashMap<String, String[]>> transitionTable =
                new HashMap<>((states.length * symbols.length));
        ArrayList<String> transitionsArrayList = new ArrayList<>();
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, String[]> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
                transitionsArrayList.add(getTransitionStringPartial(state, symbol));
            }
        }
        transitionsStringArrayPartial = transitionsArrayList.toArray(new String[transitionsArrayList.size()]);
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

    @Override
    public String[] getTransitionsStringArrayPartial() {
        return transitionsStringArrayPartial;
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
