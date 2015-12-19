package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.ArrayList;

/**
 * Created by kyle on 12/16/15.
 */
class NFATransition extends  Transition{
    private ArrayList<String> toStates;
    private String fromState;

    NFATransition(String fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    ArrayList<String> getToStates() {
        return toStates;
    }

    void setToStates(ArrayList<String> toStates) {
        this.toStates = toStates;
    }

    private String getToStatesAsString(){
        String states = "";
        for(String state : toStates){
            states = states + " " + state;
        }
        return states.trim();
    }

    String getFromState() {
        return fromState;
    }

    void setFromState(String fromState) {
        this.fromState = fromState;
    }

    String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + fromState + ", " +
                getSymbol() + ") \u2192";
    }

    String getTransitionStringFull(){
        return "\uD835\uDEFF(" + getFromState() + ", " +
                getSymbol() + ") \u2192" + getToStatesAsString();
    }
}
