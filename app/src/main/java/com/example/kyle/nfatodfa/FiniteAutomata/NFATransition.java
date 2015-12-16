package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
public class NFATransition extends  Transition{
    private String[] toStates;

    public NFATransition(String fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    public String[] getToStates() {
        return toStates;
    }

    public void setToStates(String[] toStates) {
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
        return "\uD835\uDEFF(" + getFromState() + ", " +
                getSymbol() + ") \u2192" + getToStatesAsString();
    }
}
