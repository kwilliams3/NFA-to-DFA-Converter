package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class DFA extends FiniteAutomaton implements Parcelable {

    private ArrayList<ArrayList<String>> states;
    private ArrayList<String> startState;
    private ArrayList<String> finalStates;
    private HashMap<ArrayList<String>, HashMap<String, ArrayList<String>>> transitionTable;
    private ArrayList<DFATransition> dfaTransitions;

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        setStates(readStates(dfaParcel));
        setSymbols(dfaParcel.createStringArrayList());
        setStartStateFromParcel(dfaParcel.createStringArrayList());
        setFinalStates(dfaParcel.createStringArrayList());
        setTransitionTable(dfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    public ArrayList<ArrayList<String>> getStates() {
        return states;
    }

    private void setStates(ArrayList<ArrayList<String>> states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    public void setStatesAndFinalStates(NFA nfa) {
        ArrayList<ArrayList<String>> dfaStates = new ArrayList<>();
        ArrayList<String> nfaStates = nfa.getStates();
        ArrayList<String> nfaFinalStates = nfa.getFinalStates();
        ArrayList<String> emptySet = new ArrayList<>();
        emptySet.add("∅");
        dfaStates.add(emptySet);

    }

    /**
     * Sets the symbols
     * @param nfaSymbols takes the NFA symbols array, removes epsilon from the array, and sets
     *                   it as the DFA symbols array
     */
    public void setSymbols(ArrayList<String> nfaSymbols){
        ArrayList<String> dfaSymbols = new ArrayList<>(nfaSymbols.subList(1, nfaSymbols.size()-1));
        super.setSymbols(dfaSymbols);
        if (states != null && getSymbols() != null){
            setTransitionTableAndTransitions(states, getSymbols());
        }
    }

    public ArrayList<String> getStartState() {
        return startState;
    }

    /**
     * Sets the DFA start state to the epsilon closure of the NFA start state
     * @param nfa the NFA which is being converted
     */
    public void setStartState(NFA nfa) {
        ArrayList<String> dfaStartState = new ArrayList<>();
        dfaStartState.add(nfa.getStartState());
        dfaStartState.addAll(nfa.getResultingStatesInTransitionTable(nfa.getStartState(), "ϵ"));
        this.startState = dfaStartState;
    }

    private void setStartStateFromParcel(ArrayList<String> startState){
        this.startState = startState;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<String> finalStates) {
        this.finalStates = finalStates;
    }

    private void setTransitionTable(HashMap<ArrayList<String>, HashMap<String, ArrayList<String>>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Fills the transitionTable and dfaTransitions all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of array of nfa states (DFA states are a set of states)
     * @param symbols string array of symbols in the dfa
     */
    private void setTransitionTableAndTransitions(ArrayList<ArrayList<String>> states, ArrayList<String> symbols) {
        HashMap<ArrayList<String>, HashMap<String, ArrayList<String>>> transitionTable =
                new HashMap<>(states.size() * symbols.size());
        dfaTransitions = new ArrayList<>();
        for (ArrayList<String> state : states) {
            for (String symbol: symbols) {
                HashMap<String, ArrayList<String>> secondDimension = new HashMap<>();
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
    public ArrayList<String> getResultingStateFromTransitionTable(ArrayList<String> fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStateFromTransitionTable(ArrayList<String> fromState, String symbol, ArrayList<String> toState){
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
            return (states.size() * getSymbols().size());
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
        dest.writeStringList(startState);
        dest.writeStringList(finalStates);
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
        for (ArrayList<String> state : states){
            dest.writeStringList(state);
        }
    }

    private ArrayList<ArrayList<String>> readStates(Parcel parcel) {
        ArrayList<ArrayList<String>> dfaStates = new ArrayList<>();
        for (int i=0; i< dfaStates.size(); i++) {
            dfaStates.add(parcel.createStringArrayList());
        }
        return dfaStates;
    }
}
