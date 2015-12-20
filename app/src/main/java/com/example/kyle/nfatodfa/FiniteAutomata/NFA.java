package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by kyle on 12/9/15.
 */
public class NFA extends FiniteAutomaton implements Parcelable {

    private Set<String> states;
    private String startState;
    private Set<String> finalStates;
    private HashMap<String, HashMap<String, Set<String>>> transitionTable;
    private Set<NFATransition> nfaTransitions;

    public NFA() {}

    @SuppressWarnings("unchecked")
    public NFA(Parcel nfaParcel) {
        setStates(new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray())));
        setSymbols(new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray())));
        setStartState(nfaParcel.readString());
        setFinalStates(new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray())));
        setTransitionTable(nfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    @Override
    public void setSymbols(Set<String> symbols){
        super.setSymbols(symbols);
        if (states != null && getSymbols() != null){
            setTransitionTableAndTransitions(states, getSymbols());
        }
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    private void setTransitionTable(HashMap<String, HashMap<String, Set<String>>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Constucts the NFA transition table and NFA transitions all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of states in the nfa
     * @param symbols string array of symbols in the nfa
     */
    private void setTransitionTableAndTransitions(Set<String> states, Set<String> symbols) {
        HashMap<String, HashMap<String, Set<String>>> transitionTable =
                new HashMap<>(states.size() * symbols.size());
        nfaTransitions = new LinkedHashSet<>();
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, Set<String>> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
                nfaTransitions.add(new NFATransition(state, symbol));
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
    public Set<String> getResultingStatesInTransitionTable(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStatesInTransitionTable(String fromState, String symbol, Set<String> toStates){
        transitionTable.get(fromState).put(symbol, toStates);
    }

    public Set<NFATransition> getNFATransitions() {
        return nfaTransitions;
    }

    public void setNfaTransitions(Set<NFATransition> nfaTransitions) {
        this.nfaTransitions = nfaTransitions;
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
        String[] states = new String[this.states.size()];
        String[] finalStates = new String[this.finalStates.size()];
        dest.writeStringArray(this.states.toArray(states));
        super.writeToParcel(dest, flags);
        dest.writeString(startState);
        dest.writeStringArray(this.finalStates.toArray(finalStates));
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
