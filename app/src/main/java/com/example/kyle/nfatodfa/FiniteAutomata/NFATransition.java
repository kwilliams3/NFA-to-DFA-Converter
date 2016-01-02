package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * Created by kyle on 12/16/15.
 */
public class NFATransition extends  Transition{
    private Set<String> toStates;
    private String fromState;

    public NFATransition(String fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    public Set<String> getToStates() {
        return toStates;
    }

    public void setToStates(Set<String> toStates) {
        this.toStates = toStates;
    }

    private String getToStatesAsString(){
        String states = "";
        for(String state : toStates){
            states = states + " " + state;
        }
        return states.trim();
    }

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + fromState + ", " +
                getSymbol() + ") \u2192";
    }

    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + getFromState() + ", " +
                getSymbol() + ") \u2192" + getToStatesAsString();
    }
}
