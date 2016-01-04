package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
public class DFATransition extends Transition{
    private String toState;

    public DFATransition(String fromState, String symbol){
        this.fromState = fromState;
        this.symbol = symbol;
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192" + toState;
    }
}
