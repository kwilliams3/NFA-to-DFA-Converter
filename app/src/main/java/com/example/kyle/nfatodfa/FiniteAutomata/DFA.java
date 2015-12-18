package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class DFA extends FiniteAutomaton implements Parcelable {

    private String[][] states;
    private String[] startState;
    private String[] finalStates;
    private HashMap<String[], HashMap<String, String[]>> transitionTable;
    private ArrayList<DFATransition> dfaTransitions;

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        setStates(readStates(dfaParcel));
        setSymbols(dfaParcel.createStringArray());
        setStartState(dfaParcel.createStringArray());
        setFinalStates(dfaParcel.createStringArray());
        setTransitionTable(dfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    public void setStates(String[][] states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    @Override
    public void setSymbols(String[] symbols){
        super.setSymbols(symbols);
        if (states != null && getSymbols() != null){
            setTransitionTableAndTransitions(states, getSymbols());
        }
    }

    public String[] getStartState() {
        return startState;
    }

    public void setStartState(String[] startState) {
        this.startState = startState;
    }

    public String[] getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(String[] finalStates) {
        this.finalStates = finalStates;
    }

    private void setTransitionTable(HashMap<String[], HashMap<String, String[]>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Fills the transitionTable and transitionsStringPartial all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of array of nfa states (DFA states are a set of states)
     * @param symbols string array of symbols in the dfa
     */
    private void setTransitionTableAndTransitions(String[][] states, String[] symbols) {
        HashMap<String[], HashMap<String, String[]>> transitionTable =
                new HashMap<>(states.length * symbols.length);
        dfaTransitions = new ArrayList<>();
        for (String[] state : states) {
            for (String symbol: symbols) {
                HashMap<String, String[]> secondDimension = new HashMap<>();
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
    public String[] getResultingStateFromTransitionTable(String[] fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStateFromTransitionTable(String[] fromState, String symbol, String[] toState){
        transitionTable.get(fromState).put(symbol, toState);
    }

    public ArrayList<DFATransition> getDFATransitions() {
        return dfaTransitions;
    }

    public void setDfaTransitions(ArrayList<DFATransition> dfaTransitions) {
        this.dfaTransitions = dfaTransitions;
    }

    public int getNumberOfTransitions() {
        if (states != null && getSymbols() != null) {
            return (states.length * getSymbols().length);
        } else {return 0;}
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeStates(dest);
        super.writeToParcel(dest, flags);
        dest.writeStringArray(startState);
        dest.writeStringArray(finalStates);
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

    private void writeStates(Parcel dest) {
        dest.writeInt(states.length);
        for (String[] state : states){
            dest.writeInt(state.length);
            dest.writeStringArray(state);
        }
    }

    private String[][] readStates(Parcel parcel) {
        String[][] dfaStates = new String[parcel.readInt()][];
        for (int i=0; i<dfaStates.length; i++) {
            int arraySize = parcel.readInt();
            dfaStates[i] = new String[arraySize];
            dfaStates[i] = parcel.createStringArray();
        }
        return dfaStates;
    }
}
