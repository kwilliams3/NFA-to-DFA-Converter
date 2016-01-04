package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * Created by kyle on 12/16/15.
 */
final public class NFATransition extends Transition{
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
        StringBuilder statesStringBuilder = new StringBuilder();
        for(String state : toStates){
            statesStringBuilder.append(state);
            statesStringBuilder.append(" ");
        }
        String statesString = statesStringBuilder.toString();
        return statesString;
    }

    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192" + getToStatesAsString();
    }
}
