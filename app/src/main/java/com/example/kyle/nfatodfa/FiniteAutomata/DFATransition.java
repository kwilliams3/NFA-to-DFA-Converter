package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
class DFATransition extends  Transition{
    private String[] fromState;
    private String[] toState;

    DFATransition(String[] fromState, String symbol){
        setFromState(fromState);
        setSymbol(symbol);
    }

    String[] getFromState() {
        return fromState;
    }

    String getFromStateAsString(){
        String fromStateString = "";
        for (String state : fromState){
            fromStateString = fromStateString.concat(state + " ");
        }
        return fromStateString.trim();
    }

    String[] getToState() {
        return toState;
    }

    String getToStateAsString(){
        String toStateString = "";
        for (String state : toState){
            toStateString = toStateString.concat(state + " ");
        }
        return toStateString.trim();
    }

    void setToState(String[] toState) {
        this.toState = toState;
    }

    void setFromState(String[] fromState) {
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
