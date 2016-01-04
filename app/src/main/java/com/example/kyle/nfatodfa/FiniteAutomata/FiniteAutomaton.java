package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class isn't truly necessary, but I've implemented it for organizational purposes.
 * All NFAs and DFAs are finite automata, and I wanted to keep that hierarchy in my code.
 * Created by kyle on 12/9/15.
 */
abstract class FiniteAutomaton implements Parcelable {

    protected Set<String> states = new LinkedHashSet<>();
    protected Set<String> symbols = new LinkedHashSet<>();
    protected String startState;
    protected Set<String> acceptStates = new LinkedHashSet<>();

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states){
        this.states = states;
        if (this.states != null && getSymbols() != null){
            setTransitionTableAndTransitions(this.states, getSymbols());
        }
    }

    public Set<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<String> symbols){
        this.symbols = symbols;
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

    public Set<String> getAcceptStates() {
        return acceptStates;
    }

    public void setAcceptStates(Set<String> acceptStates) {
        this.acceptStates = acceptStates;
    }

    abstract void setTransitionTableAndTransitions(Set<String> states, Set<String> symbols);

    public int getNumberOfTransitions() {
        if (states != null && getSymbols() != null) {
            return (states.size() * getSymbols().size());
        } else {return 0;}
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] states = new String[this.states.size()];
        dest.writeStringArray(this.states.toArray(states));
        String[] symbols = new String[this.symbols.size()];
        dest.writeStringArray(this.symbols.toArray(symbols));
        dest.writeString(startState);
        String[] acceptStates = new String[this.acceptStates.size()];
        dest.writeStringArray(this.acceptStates.toArray(acceptStates));
    }
}