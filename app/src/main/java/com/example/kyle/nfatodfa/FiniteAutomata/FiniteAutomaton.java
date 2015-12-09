package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.HashMap;

/**
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton {
    private String[] states;
    private String[] symbols;
    private HashMap<String, String> transitionTable;
    private String startState;
    private String acceptState;

    public String[] getStates() {
        return states;
    }

    public void setStates(String[] states) {
        this.states = states;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public void setSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    public HashMap<String, String> getTransitionTable() {
        return transitionTable;
    }

    public void setTransitionTable(HashMap<String, String> transitionTable) {
        this.transitionTable = transitionTable;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getAcceptState() {
        return acceptState;
    }

    public void setAcceptState(String acceptState) {
        this.acceptState = acceptState;
    }

    /**
     * Returns the resulting state that is reached after processing the transition
     * @param state string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @return the resulting state that is reached after processing the transition
     */
    public abstract String getResultingState(String state, String symbol);

    /**
     * Returns a string that states all or part of a transition from the automaton
     * @param fromState string representing a state from the automaton's finite set of states
     * @param symbol string representing a symbol from the automaton's alphabet
     * @param fullString boolean indicating whether all or part of the transition needs to be stated
     * @return string stating all of part of a transition
     */
    public abstract String getTransitionString(String fromState, String symbol, boolean fullString);
}
