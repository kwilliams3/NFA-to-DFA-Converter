package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by kyle on 12/9/15.
 * TODO: FINISH THE CONVERTER, needs to be changed due to DFA changes
 * TODO: Doc strings for everything necessary throughout all classes - to include doc stringing
 * TODO: the classes themselves (i.e. right here); flood the NFAConverter class' functions
 * TODO: with comments that explain what's going on and why; set things to "final" that should be final
 */
final class DFA extends FiniteAutomaton implements Parcelable {

    private HashMap<String, HashMap<String, String>> transitionTable;
    // dfaTransitions is used solely for a visual display to the user of the DFA transitions.
    // The field is not actually used in any computations. The reason is that at some points we will
    // need to look up which state a transition will lead to. If we created a list of dfaTransition
    // objects. Then, we would have to iterate through that list to find the transition we are
    // looking for. Unfortunately, that would be a O(n) complexity. So, to save time, we use a
    // HashMap instead which allows us to look up a transition in constant time - O(1).
    private Set<DFATransition> dfaTransitions = new LinkedHashSet<>();

    public DFA() {}

    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        states = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        symbols = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        startState = dfaParcel.readString();
        acceptStates = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        transitionTable = dfaParcel.readHashMap(HashMap.class.getClassLoader());
    }

    /**
     * Constructs the DFA transition table and DFA transitions all
     *  at once in order to keep the complexity no larger than O(n^2)
     * @param states string array of array of nfa states (DFA states are a set of states)
     * @param symbols string array of symbols in the dfa
     */
    void setTransitionTableAndTransitions(Set<String> states, Set<String> symbols) {
        transitionTable = new HashMap<>(states.size() * symbols.size());
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, String> secondDimension = new HashMap<>();
                secondDimension.put(symbol, null);
                transitionTable.put(state, secondDimension);
                dfaTransitions.add(new DFATransition(state, symbol));
            }
        }
    }

    /**
     * Returns the resulting state that is reached after processing the transition
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's finite set of symbols
     * @return the resulting state that is reached after processing the transition
     */
    public String getResultingStateFromTransitionTable(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    public void setResultingStateInTransitionTable(String fromState, String symbol, String toState){
        transitionTable.get(fromState).put(symbol, toState);
    }

    public Set<DFATransition> getDFATransitions() {
        return dfaTransitions;
    }

    public void setDfaTransitions(Set<DFATransition> dfaTransitions) {
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
