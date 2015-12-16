package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
abstract class Transition {
    private String fromState;
    private String symbol;

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192";
    }
}
