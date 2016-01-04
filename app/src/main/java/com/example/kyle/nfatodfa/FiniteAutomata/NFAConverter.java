package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Converts an NFA into a DFA that accepts the same language
 * Given an NFA, it will return an equivalent DFA.
 *
 * @author Kyle Williams
 * @since 1/2/16
 */
final class NFAConverter {

    // TODO: Change transition table in automaton classes to only be filled dynamically (i.e. - not with "null"s)

    private NFA nfa; // the NFA to be converted

    private DFA dfa = new DFA(); // the DFA to be returned
    private Set<String> dfaStates = new LinkedHashSet<>();
    private String dfaStartState;
    private Set<String> dfaAcceptStates = new LinkedHashSet<>();

    private String newDFAState;
    private String currentState;
    private MyStringBuilder newDFAStateBuilder = new MyStringBuilder();
    private MyStringBuilder currentStateBuilder = new MyStringBuilder();
    private boolean dfaStateisAnAcceptState = false; // Keeps track of whether a DFA state is an accept state or not

    /**
     * Converts an NFA to an equivalent DFA
     * @return a DFA which accepts only the language that the given NFA accepts
     */
    DFA convert(NFA nfaToBeConverted) {
        nfa = nfaToBeConverted;
        powerSetConstruction();
        dfa.states = dfaStates;
        dfa.symbols = getDFASymbols();
        dfa.startState = dfaStartState;
        dfa.acceptStates = dfaAcceptStates;
        // The DFA transition table is filled inside powerSetConstruction();


        return dfa;
    }

    private void powerSetConstruction(){
        Set<String> nfaStartStateAsSet = new LinkedHashSet<>();
        nfaStartStateAsSet.add(nfa.startState);
        Set<String> unformattedDFAStartState = takeEpsilonClosure(nfaStartStateAsSet);
        dfa.startState = fixme;
        newDFAStateBuilder.clear();

        LinkedList<Set<String>> dfaStatesToBeMappedOut = new LinkedList<>();
        dfaStatesToBeMappedOut.push(unformattedDFAStartState);
        do {
            Set<String> currentDFAState = dfaStatesToBeMappedOut.pop();
            Set<String> newDFAState = new LinkedHashSet<>();
            for (String symbol : dfa.symbols) {
                for (String element : currentDFAState) {
                    newDFAState.addAll(move(element, symbol));
                    newDFAState.addAll(takeEpsilonClosure(newDFAState));
                    currentStateBuilder.append(element);
                }
                // The dfaStateBuilder string is used several times below. To avoid multiple string
                // allocations, resulting from multiple toString() calls, we create a string once
                // and assign it to dfaState.
                this.newDFAState = newDFAStateBuilder.toString();
                if (newDFAState.isEmpty()){
                    newDFAState = null;
                }
                else if(!dfa.states.contains(this.newDFAState)){
                    // Eureka! We found a new DFA state!
                    dfaStatesToBeMappedOut.push(newDFAState);
                    dfa.states.add(this.newDFAState);

                    if(dfaStateisAnAcceptState){
                        dfa.acceptStates.add(this.newDFAState);
                        dfaStateisAnAcceptState = false; // Reset the variable
                    }
                }

                newDFAStateBuilder.clear();
                dfa.setResultingStateInTransitionTable(currentDFAState, symbol, this.newDFAState);
                newDFAState.clear();
            }

        } while (!dfaStatesToBeMappedOut.isEmpty());
    }

    private Set<String> move(String element, String symbol){
        return nfa.getResultingStatesInTransitionTable(element, symbol);
    }

    /**
     * Takes the epsilon closure of a partial DFA state and returns
     * just the states that can be reached through epsilon transitions
     * @param newDFAState a partial DFA state that does not contain the NFA states
     *                       which can be reached through zero or more e-transitions
     * @return the resulting set after taking the epsilon closure of newDFAState
     */
    private Set<String> takeEpsilonClosure(Set<String> newDFAState) {
        if (newDFAState.isEmpty()) {
            return newDFAState;
        }

        // The epsilon closure could lead to many different states, and those states
        // could circle back around. It is important that we keep track of which NFA states
        // have been checked for e-transitions and which ones have not been checked yet. This
        // will help us to avoid infinite loops where two separate states lead to each other.
        Set<String> checkedStates = new LinkedHashSet<>();
        LinkedList<String> toBeChecked = new LinkedList<>();

        Set<String> eClosureOfNewDFAState = new LinkedHashSet<>();

        // Choosing to add newDFAState to the two collections in a foreach loop avoids having to
        // call addAll(newDFAState) on the collections separately which would result in iterating
        // over newDFAState twice.
        // The toBeChecked collection is updated when a new NFA state is reached that has not had
        // the epsilon closure performed on it. We manually add the NFA states, in newDFAState,
        // to the toBeChecked collection so that they will have the epsilon closure performed on
        // them as well. We also need to manually add the NFA states, in newDFAState, to the
        // checkedStates collection so that none are accidentally added a second time to the
        // toBeChecked collection if taking the epsilon closure of an NFA state leads to one of them.
        for (String state : newDFAState) {
            toBeChecked.add(state);
            checkedStates.add(state);
            eClosureOfNewDFAState.add(state);
            newDFAStateBuilder.append(state);
        }

        while(!toBeChecked.isEmpty()) {
            String currentNFAState = toBeChecked.pop();
            // If currentNFAState is an accept state, then the entire
            // DFA state that it accompanies will also be an accept state
            if (nfa.acceptStates.contains(currentNFAState)) {
                dfaStateisAnAcceptState = true;
            }
            Set<String> epsilonStates = nfa.getResultingStatesInTransitionTable(currentNFAState, "ϵ");
            for (String state : epsilonStates) {
                if (!eClosureOfNewDFAState.contains(state)){
                    newDFAStateBuilder.append(state);
                    eClosureOfNewDFAState.add(state);
                }
            }
            checkedStates.add(currentNFAState);
            // We need to add the epsilonStates to toBeChecked, and toBeChecked should not contain
            // already checked states. Remove any states that have already been checked.
            for (String state : epsilonStates){
                if(checkedStates.contains(state)){
                    epsilonStates.remove(state);
                }
            }
            toBeChecked.addAll(epsilonStates);
        }

        return eClosureOfNewDFAState;
    }

    private Set<String> getDFASymbols(){
        Set<String> dfaSymbols = nfa.symbols;
        dfaSymbols.remove("ϵ");
        return dfaSymbols;
    }
}
