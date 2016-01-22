package com.example.kyle.nfatodfa.FiniteAutomata;

/**
 * This class is used to display DFA transitions. Instances of this class are not used in any
 * calculations for the DFA which they belong to. Instead, they are only used as a visual
 * display, of a specific DFA transition, to the user.
 *
 * @author Kyle Williams
 * @since 12/16/15
 */
final public class DFATransition extends Transition{
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

    /**
     * Returns the transition as a string, with the resulting state, for visual display to the user
     * @return a string with the resulting state
     */
    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192" + toState;
    }
}
