package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
abstract class Transition {
    protected String fromState;
    protected String symbol;

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    String getSymbol() {
        return symbol;
    }

    void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192";
    }

    public abstract String getTransitionStringFull();
}
