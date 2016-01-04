package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a non
 * Created by kyle on 12/9/15.
 */
final public class NFA extends FiniteAutomaton implements Parcelable {


    private HashMap<String, HashMap<String, Set<String>>> transitionTable;
    // nfaTransitions is used solely for a visual display to the user of the NFA transitions.
    // The field is not actually used in any computations. The reason is that at some points we will
    // need to look up which state a transition will lead to. If we created a list of nfaTransition
    // objects. Then, we would have to iterate through that list to find the transition we are
    // looking for. Unfortunately, that would be a O(n) complexity. So, to save time, we use a
    // HashMap instead which allows us to look up a transition in constant time - O(1).
    private Set<NFATransition> nfaTransitions = new LinkedHashSet<>();;

    public NFA() {}

    @SuppressWarnings("unchecked")
    public NFA(Parcel nfaParcel) {
        states = new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray()));
        symbols = new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray()));
        startState = nfaParcel.readString();
        acceptStates = new LinkedHashSet<>(Arrays.asList(nfaParcel.createStringArray()));
        transitionTable = nfaParcel.readHashMap(HashMap.class.getClassLoader());
    }

    /**
     * Constructs the NFA transition table and NFA transitions all
     *  at once in order to keep the runtime no longer than O(n^2)
     * @param states string array of states in the nfa
     * @param symbols string array of symbols in the nfa
     */
    void setTransitionTableAndTransitions(Set<String> states, Set<String> symbols) {
        transitionTable = new HashMap<>(states.size() * symbols.size());
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, Set<String>> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
                nfaTransitions.add(new NFATransition(state, symbol));
            }
        }
    }

    /**
     * Returns the resulting states that can be reached after processing the transition
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return array containing resulting states that can be reached after processing the transition
     */
    public Set<String> getResultingStatesInTransitionTable(String fromState, String symbol) {
        return transitionTable.get(fromState).get(symbol);
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