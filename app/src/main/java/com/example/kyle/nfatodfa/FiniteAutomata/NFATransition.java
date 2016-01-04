package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * Created by kyle on 12/16/15.
 */
public class NFATransition extends Transition{
    private Set<String> toStates;

    public NFATransition(String fromState, String symbol){
        this.fromState = fromState;
        this.symbol = symbol;
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

    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192" + getToStatesAsString();
    }
}
