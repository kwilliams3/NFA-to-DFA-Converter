package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * Created by kyle on 12/16/15.
 */
public class DFATransition extends  Transition{
    private Set<String> fromState;
    private Set<String> toState;

    public DFATransition(Set<String> fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    public Set<String> getFromState() {
        return fromState;
    }

    private String getFromStateAsString(){
        String fromStateString = "";
        for (String state : fromState){
            fromStateString = fromStateString.concat(state + " ");
        }
        return fromStateString.trim();
    }

    public Set<String> getToState() {
        return toState;
    }

    private String getToStateAsString(){
        String toStateString = "";
        for (String state : toState){
            toStateString = toStateString.concat(state + " ");
        }
        return toStateString.trim();
    }

    public void setToState(Set<String> toState) {
        this.toState = toState;
    }

    public void setFromState(Set<String> fromState) {
        this.fromState = fromState;
    }

    public String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + getFromStateAsString() + ", " +
                getSymbol() + ") \u2192";
    }

    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + getFromStateAsString() + ", " +
                getSymbol() + ") \u2192" + getToStateAsString();
    }
}
