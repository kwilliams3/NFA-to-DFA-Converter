package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * This class is used as a model for any finite automaton display-only transitions. It does not
 * pertain to any type of finite automaton in particular. "display-only" means that subclasses
 * of this class are not used in any calculations for the automaton which they belong to. Instead,
 * they are only used as a visual display, of a specific transition, to the user.
 *
 * @author Kyle Williams
 * @since 12/16/15
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

    /**
     * Returns the transition as a string, without the resulting state, for visual display to the user
     * @return a string without the resulting state
     */
    public String getTransitionStringPartial(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192";
    }

    /**
     * Returns the transition as a string, with the resulting state, for visual display to the user
     */
    public abstract String getTransitionStringFull();
}
