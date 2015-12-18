package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * Created by kyle on 12/16/15.
 */
abstract class Transition {
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
