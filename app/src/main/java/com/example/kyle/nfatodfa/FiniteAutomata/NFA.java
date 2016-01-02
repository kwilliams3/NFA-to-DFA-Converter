package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by kyle on 12/9/15.
 */
public class NFA extends FiniteAutomaton implements Parcelable {

    private Set<String> states;
    private String startState;
    private Set<String> finalStates;
    private HashMap<String, HashMap<String, Set<String>>> transitionTable;
    // nfaTransitions is used solely for a visual display to the user of the NFA transitions.
    // The variable is not actually used in any computations.
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

    /**
     * Converts an NFA to an equivalent DFA
     * @param nfa the NFA to be converted
     * @return a DFA which accepts only the language that the given NFA accepts
     */
    public DFA convertToDFA(NFA nfa){
        DFA resultingDFA = new DFA();
        resultingDFA.setSymbols(nfa.getSymbols());
        Set<String> startStateSet = new LinkedHashSet<>();
        startStateSet.add(nfa.getStartState());
        resultingDFA.setStartState(takeEpsilonClosure(startStateSet));


        return resultingDFA;
    }

    /**
     * Takes the epsilon closure of a partial DFA state and returns
     * just the states that can be reached through epsilon transitions
     * @param partOfDFAState a partial DFA state that does not contain the NFA states
     *                       which can be reached through zero or more e-transitions
     * @return the resulting set after taking the epsilon closure of partOfDFAState
     */
    private Set takeEpsilonClosure(Set<String> partOfDFAState){
        Set<String> eClosureOfDFAState = new LinkedHashSet<>();
        // The epsilon closure could lead to many different states, and those states
        // could circle back around. It is important that we keep track of which NFA states
        // have been checked for e-transitions and which ones have not been checked yet. This
        // will help us to avoid infinite loops where two separate states lead to each other.
        Set<String> checkedStates = new LinkedHashSet<>();
        LinkedList<String> toBeChecked = new LinkedList<>();

        // Choosing to add partOfDFAState to all three collections in a foreach loop avoids having to
        // call addAll(partOfDFAState) on each collection separately which would result in iterating
        // over partOfDFAState three times.
        // The toBeChecked collection is updated when a new NFA state is reached that has not had
        // the epsilon closure performed on it. We manually add the NFA states, in partOfDFAState,
        // to the toBeChecked collection so that they will have the epsilon closure performed on
        // them as well. We also need to manually add the NFA states, in partOfDFAState, to the
        // checkedStates collection so that they are not accidentally added a second time to the
        // toBeChecked collection if taking the epsilon closure of an NFA state leads to one of them.
        for (String state : partOfDFAState){
            toBeChecked.add(state);
            checkedStates.add(state);
            eClosureOfDFAState.add(state);
        }

        while(!toBeChecked.isEmpty()){
            String currentState = toBeChecked.pop();
            Set<String> epsilonStates = getResultingStatesInTransitionTable(currentState, "ϵ");
            eClosureOfDFAState.addAll(epsilonStates);
            checkedStates.add(currentState);
            //toBeChecked should not contain already checked states.
            //Remove any states that have already been checked.
            for (String state : epsilonStates){
                if(checkedStates.contains(state)){
                    epsilonStates.remove(state);
                }
            }
            toBeChecked.addAll(epsilonStates);
        }

        partOfDFAState.addAll(eClosureOfDFAState);
        return partOfDFAState;
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
