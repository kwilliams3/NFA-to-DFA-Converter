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


    private HashMap<String, HashMap<String, Set<String>>> transitionTable = new HashMap<>();
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
     * Constructs NFA transitions that are only used for visual display to the user
     */
    void setForDisplayOnlyTransitions() {
        for (String state : states) {
            for (String symbol: symbols) {
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
        HashMap<String, Set<String>> secondDimension = new HashMap<>();
        secondDimension.put(symbol, toStates);
        transitionTable.put(fromState, secondDimension);
    }

    public Set<NFATransition> getNFATransitions() {
        return nfaTransitions;
    }

    public void setNfaTransitions(Set<NFATransition> nfaTransitions) {
        this.nfaTransitions = nfaTransitions;
    }

    /**
     * Converts the NFA into a DFA
     * @return an equivalent DFA, or null if the NFA is not ready to be converted
     */
    private DFA convertToDFA(){
        if (isReadyForConversion()) {
            return NFAConverter.convert(this);
        }

        return null;
    }

    /**
     * Check to see that all variables were filled out
     * @return true if the NFA is ready to be converted
     */
    private boolean isReadyForConversion() {
        return !states.isEmpty() && !symbols.isEmpty() &&
                !startState.isEmpty() && !acceptStates.isEmpty();
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