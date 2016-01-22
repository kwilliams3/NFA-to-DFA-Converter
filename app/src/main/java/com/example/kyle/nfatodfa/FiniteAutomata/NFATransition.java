package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.Set;

/**
 * This class is used to display NFA transitions. Instances of this class are not used in any
 * calculations for the NFA which they belong to. Instead, they are only used as a visual
 * display, of a specific NFA transition, to the user.
 *
 * @author Kyle Williams
 * @since 12/16/15.
 */
final public class NFATransition extends Transition{
    private Set<String> toStates;

    public NFATransition(String fromState, String symbol){
        this.fromState = fromState;
        this.symbol = symbol;
    }

    public Set<String> getToStates() {
        return toStates;
    }

    public void setToStates(Set<String> toStates) {
        this.toStates = toStates;
    }

    /**
     * Used only for the getTransitionStringFull
     * @return a string containing all the states in toStates
     */
    private String getToStatesAsString(){
        StringBuilder statesStringBuilder = new StringBuilder();
        for(String state : toStates){
            statesStringBuilder.append(state);
            statesStringBuilder.append(" ");
        }
        String statesString = statesStringBuilder.toString();
        return statesString;
    }

    /**
     * Returns the transition as a string, with the resulting state, for visual display to the user
     * @return a string with the resulting state
     */
    public String getTransitionStringFull(){
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192" + getToStatesAsString();
    }
}
