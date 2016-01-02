package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by kyle on 12/9/15.
 */
class DFA extends FiniteAutomaton implements Parcelable {

    private Set<Set<String>> states;
    private Set<String> startState;
    private Set<String> finalStates;
    private HashMap<Set<String>, HashMap<String, Set<String>>> transitionTable;
    // dfaTransitions is used solely for a visual display to the user of the DFA transitions.
    // The variable is not actually used in any computations.
    private Set<DFATransition> dfaTransitions;

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        setStates(readStates(dfaParcel));
        setSymbols(new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray())));
        setStartStateFromParcel(new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray())));
        setFinalStates(new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray())));
        setTransitionTable(dfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    public Set<Set<String>> getStates() {
        return states;
    }

    void setStates(Set<Set<String>> states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    /**
     * Sets the symbols
     * @param nfaSymbols takes the NFA symbols array, removes epsilon from the array, and sets
     *                   it as the DFA symbols array
     */
    @Override
    void setSymbols(Set<String> nfaSymbols){
        nfaSymbols.remove("ϵ");
        Set<String> dfaSymbols = new LinkedHashSet<>(nfaSymbols);
        super.setSymbols(dfaSymbols);
        if (states != null && getSymbols() != null){
            setTransitionTableAndTransitions(states, getSymbols());
        }
    }

    public Set<String> getStartState() {
        return startState;
    }

    void setStartState(Set<String> eClosureOfNFAStartState) {
        this.startState = eClosureOfNFAStartState;
    }

    private void setStartStateFromParcel(Set<String> startState){
        this.startState = startState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    private void setTransitionTable(HashMap<Set<String>, HashMap<String, Set<String>>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Constucts the DFA transition table and DFA transitions all
     *  at once in order to keep the complexity no larger than O(n^2)
     * @param states string array of array of nfa states (DFA states are a set of states)
     * @param symbols string array of symbols in the dfa
     */
    private void setTransitionTableAndTransitions(Set<Set<String>> states, Set<String> symbols) {
        HashMap<Set<String>, HashMap<String, Set<String>>> transitionTable =
                new HashMap<>(states.size() * symbols.size());
        dfaTransitions = new LinkedHashSet<>();
        for (Set<String> state : states) {
            for (String symbol: symbols) {
                HashMap<String, Set<String>> secondDimension = new HashMap<>();
                secondDimension.put(symbol, null);
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
    public Set<String> getResultingStateFromTransitionTable(ArrayList<String> fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStateFromTransitionTable(ArrayList<String> fromState, String symbol, Set<String> toState){
        transitionTable.get(fromState).put(symbol, toState);
    }

    public Set<DFATransition> getDFATransitions() {
        return dfaTransitions;
    }

    public void setDfaTransitions(Set<DFATransition> dfaTransitions) {
        this.dfaTransitions = dfaTransitions;
    }

    public int getNumberOfTransitions() {
        if (states != null && getSymbols() != null) {
            return (states.size() * getSymbols().size());
        } else {return 0;}
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] startState = new String[this.startState.size()];
        String[] finalStates = new String[this.finalStates.size()];
        writeStates(dest);
        super.writeToParcel(dest, flags);
        dest.writeStringArray(this.startState.toArray(startState));
        dest.writeStringArray(this.finalStates.toArray(finalStates));
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
        for (Set<String> state : states){
            String[] states = new String[state.size()];
            dest.writeStringArray(state.toArray(states));
        }
    }

    private Set<Set<String>> readStates(Parcel parcel) {
        Set<Set<String>> dfaStates = new LinkedHashSet<>();
        for (int i=0; i< dfaStates.size(); i++) {
            dfaStates.add(new LinkedHashSet<>(Arrays.asList(parcel.createStringArray())));
        }
        return dfaStates;
    }
}
