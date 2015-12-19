package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * Created by kyle on 12/16/15.
 */
class DFATransition extends  Transition{
    private Set<String> fromState;
    private Set<String> toState;

    DFATransition(Set<String> fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    Set<String> getFromState() {
        return fromState;
    }

    private String getFromStateAsString(){
        String fromStateString = "";
        for (String state : fromState){
            fromStateString = fromStateString.concat(state + " ");
        }
        return fromStateString.trim();
    }

    Set<String> getToState() {
        return toState;
    }

    private String getToStateAsString(){
        String toStateString = "";
        for (String state : toState){
            toStateString = toStateString.concat(state + " ");
        }
        return toStateString.trim();
    }

    void setToState(Set<String> toState) {
        this.toState = toState;
    }

    void setFromState(Set<String> fromState) {
        this.fromState = fromState;
    }

    String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + getFromStateAsString() + ", " +
                getSymbol() + ") \u2192";
    }

    String getTransitionStringFull(){
        return "\uD835\uDEFF(" + getFromStateAsString() + ", " +
                getSymbol() + ") \u2192" + getToStateAsString();
    }
}
