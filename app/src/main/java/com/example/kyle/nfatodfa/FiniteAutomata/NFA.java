package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class NFA extends FiniteAutomaton implements Parcelable {

    private ArrayList<String> states;
    private String startState;
    private ArrayList<String> finalStates;
    private HashMap<String, HashMap<String, ArrayList<String>>> transitionTable;
    private ArrayList<NFATransition> nfaTransitions;

    public NFA() {}

    @SuppressWarnings("unchecked")
    public NFA(Parcel nfaParcel) {
        setStates(nfaParcel.createStringArrayList());
        setSymbols(nfaParcel.createStringArrayList());
        setStartState(nfaParcel.readString());
        setFinalStates(nfaParcel.createStringArrayList());
        setTransitionTable(nfaParcel.readHashMap(HashMap.class.getClassLoader()));
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    @Override
    public void setSymbols(ArrayList<String> symbols){
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

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<String> finalStates) {
        this.finalStates = finalStates;
    }

    private void setTransitionTable(HashMap<String, HashMap<String, ArrayList<String>>> transitionTable) {
        this.transitionTable = transitionTable;
    }

    /**
     * Fills the transitionTable and transitionsStringPartial all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of states in the nfa
     * @param symbols string array of symbols in the nfa
     */
    private void setTransitionTableAndTransitions(ArrayList<String> states, ArrayList<String> symbols) {
        HashMap<String, HashMap<String, ArrayList<String>>> transitionTable =
                new HashMap<>(states.size() * symbols.size());
        nfaTransitions = new ArrayList<NFATransition>();
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, ArrayList<String>> secondDimension = new HashMap<>();
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
    public ArrayList<String> getResultingStatesInTransitionTable(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStatesInTransitionTable(String fromState, String symbol, ArrayList<String> toStates){
        transitionTable.get(fromState).put(symbol, toStates);
    }

    public ArrayList<NFATransition> getNFATransitions() {
        return nfaTransitions;
    }

    public void setNfaTransitions(ArrayList<NFATransition> nfaTransitions) {
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
        dest.writeStringList(states);
        super.writeToParcel(dest, flags);
        dest.writeString(startState);
        dest.writeStringList(finalStates);
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
