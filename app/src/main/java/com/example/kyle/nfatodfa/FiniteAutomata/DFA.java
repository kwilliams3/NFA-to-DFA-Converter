package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
public class DFA extends FiniteAutomaton {
    private HashMap<String, HashMap<String, String>> transitionTable;

    public HashMap<String, HashMap<String, String>> getTransitionTable() {
        return transitionTable;
    }

    private void setTransitionTable(String[] states, String[] symbols) {
        HashMap<String, HashMap<String, String>> transitionTable =
                new HashMap<>((states.length * symbols.length));
        // Filling transitionTable
        for (String state : states) {
            for (String symbol: symbols) {
                HashMap<String, String> secondDimension = new HashMap<>();
                secondDimension.put(symbol,null);
                transitionTable.put(state, secondDimension);
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
    public String getResultingState(String fromState, String symbol) {
        return (transitionTable.get(fromState)).get(symbol);
    }

    /**
     * Returns a string that states all or part of a transition from the automaton
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return string stating a transition, includes the resulting state only if any such state is known
     */
    public String getTransitionString(String fromState, String symbol) {
        String transitionString;
        String toState = getResultingState(fromState, symbol);

        if (toState == null) {
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192";
        } else {
            transitionString = "\uD835\uDEFF(" + fromState + ", " + symbol + ") \u2192" + toState;
        }

        return transitionString;
    }

    @Override
    public void setStates(String[] states){
        super.setStates(states);
        if (getSymbols() != null){
            setTransitionTable(getStates(), getSymbols());
        }
    }

    @Override
    public void setSymbols(String[] symbols){
        super.setSymbols(symbols);
        if (getStates() != null){
            setTransitionTable(getStates(), getSymbols());
        }
    }
}
