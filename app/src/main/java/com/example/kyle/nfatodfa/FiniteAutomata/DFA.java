package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The DFA class represents a deterministic finite automaton.
 *
 * @author Kyle Williams
 * @since 12/9/15.
 * TODO: FINISH THE CONVERTER, needs to be changed due to DFA changes
 * TODO: Doc strings for everything necessary throughout all classes - to include doc stringing
 * TODO: the classes themselves (i.e. right here); flood the NFAConverter class' functions
 * TODO: with comments that explain what's going on and why; set things to "final" that should be final
 */
final class DFA extends FiniteAutomaton implements Parcelable {

    // We use a HashMap to look up transitions in the DFA. The reason I chose a HashMap is because
    // at some points we will need to look up which state a transition will lead to.
    // If we created a list of dfaTransition objects, then we would have to iterate through that
    // list to find the transition we are looking for. Unfortunately, that would be a O(n)
    // complexity. So, to save time, we use a HashMap instead which allows us to look up a
    // transition in constant time - O(1).
    private HashMap<String, HashMap<String, String>> transitionTable = new HashMap<>();
    // displayOnlyTransitions is used solely for a visual display to the user of the DFA transitions.
    // The field is not actually used in any computations.
    private Set<DFATransition> dfaTransitions = new LinkedHashSet<>();

    public DFA() {}

    // Parcel.writeMap does not store the type of HashMap in use. So, dfaParcel.readHashMap produces
    // an Unchecked assignment. The transitionTable could be passed using a Bundle, but the same
    // Unchecked assignment would be produced when using getSerializable after using writeSerializable.
    @SuppressWarnings("unchecked")
    public DFA(Parcel dfaParcel) {
        states = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        symbols = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        startState = dfaParcel.readString();
        acceptStates = new LinkedHashSet<>(Arrays.asList(dfaParcel.createStringArray()));
        transitionTable = dfaParcel.readHashMap(HashMap.class.getClassLoader());
    }

    /**
     * Constructs DFA transitions that are only used for visual display to the user
     */
    void createForDisplayOnlyTransitions() {
        for (String state : states) {
            for (String symbol: symbols) {
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
        HashMap<String, String> secondDimension = new HashMap<>();
        secondDimension.put(symbol, null);
        transitionTable.put(fromState, secondDimension);
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
