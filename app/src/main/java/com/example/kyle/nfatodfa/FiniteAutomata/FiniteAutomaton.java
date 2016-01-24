package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class contains the characteristics that are shared by all finite automata. This class
 * cannot be instantiated due to the fact that a finite automaton is only a term used to describe
 * specific automata.
 *
 * @author Kyle Williams
 * @since 12/9/15
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
        trySettingForDisplayOnlyTransitions();
    }

    public Set<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<String> symbols){
        this.symbols = symbols;
        trySettingForDisplayOnlyTransitions();
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

    /**
     * The "displayOnlyTransitions" are a set of strings that represent transitions in a finite
     * automaton. They are not actually used in any conversions. Instead, they are only used to
     * visually "display" transitions to the user.
     */
    abstract void createForDisplayOnlyTransitions();

    /**
     * Tries to set the transitions that are only used for visual display to the user
     * @return true if the display only transitions were successfully set, otherwise returns false
     */
    private boolean trySettingForDisplayOnlyTransitions(){
        if (states != null && symbols != null){
            createForDisplayOnlyTransitions();
            return true;
        }
        return false;
    }

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