package com.example.kyle.nfatodfa;

/**
 * Created by kyle on 12/8/15.
 */
public class Transition {
    private String fromState;
    private String symbol;
    private String toState = null;

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPartialTransitionString(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192";
    }

    public String getFullTransitionString(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192 " + toState;
    }
}
