package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by kyle on 1/2/16.
 * Todo:
 */
class NFAConverter {

    // Used to keep track of whether a DFA state is a finish state or not
    private boolean isAFinishState = false;
    private NFA nfa;
    private DFA dfa = new DFA();

    NFAConverter(NFA nfaToBeConverted){
        nfa = nfaToBeConverted;
    }

    /**
     * Converts an NFA to an equivalent DFA
     * @return a DFA which accepts only the language that the given NFA accepts
     */
    DFA convert() {
        dfa.setSymbols(nfa.getSymbols());
        Set<String> nfaStartStateAsSet = new LinkedHashSet<>();
        nfaStartStateAsSet.add(nfa.getStartState());
        dfa.setStartState(takeEpsilonClosure(nfaStartStateAsSet));

        LinkedList<Set<String>> dfaStatesToBeMappedOut = new LinkedList<>();
        dfaStatesToBeMappedOut.push(dfa.getStartState());
        do {
            Set<String> currentDFAState = dfaStatesToBeMappedOut.pop();
            for (String symbol : dfa.getSymbols()) {
                Set<String> newDFAState = new LinkedHashSet<>();
                for (String element : currentDFAState) {
                    Set<String> partOfNewDFAState = new LinkedHashSet<>();
                    partOfNewDFAState.addAll(nfa.getResultingStatesInTransitionTable(element, symbol));
                    if (!partOfNewDFAState.isEmpty()) {
                        newDFAState.addAll(takeEpsilonClosure(partOfNewDFAState));
                    }
                }
                Set<Set<String>> dfaStates = dfa.getStates();
                if (newDFAState.isEmpty()){
                    newDFAState = null;
                }
                else if(!dfaStates.contains(newDFAState)){
                    // Eureka! We found a new DFA state!
                    dfaStatesToBeMappedOut.push(newDFAState);
                    dfaStates.add(newDFAState);
                    dfa.setStates(dfaStates);
                    if(isAFinishState){
                        Set<Set<String>> dfaAcceptStates = dfa.getAcceptStates();
                        dfaAcceptStates.add(newDFAState);
                        dfa.setAcceptStates(dfaAcceptStates);
                        // Reset the variable
                        isAFinishState = false;
                    }
                }
                dfa.setResultingStateInTransitionTable(currentDFAState, symbol, newDFAState);
            }

        } while (!dfaStatesToBeMappedOut.isEmpty());

        return dfa;
    }

    /**
     * Takes the epsilon closure of a partial DFA state and returns
     * just the states that can be reached through epsilon transitions
     * @param partOfDFAState a partial DFA state that does not contain the NFA states
     *                       which can be reached through zero or more e-transitions
     * @return the resulting set after taking the epsilon closure of partOfDFAState
     */
    private Set<String> takeEpsilonClosure(Set<String> partOfDFAState) {
        Set<String> eClosureOfPartialDFAState = new LinkedHashSet<>();
        // The epsilon closure could lead to many different states, and those states
        // could circle back around. It is important that we keep track of which NFA states
        // have been checked for e-transitions and which ones have not been checked yet. This
        // will help us to avoid infinite loops where two separate states lead to each other.
        Set<String> checkedStates = new LinkedHashSet<>();
        LinkedList<String> toBeChecked = new LinkedList<>();

        // Choosing to add partOfDFAState to all three collections in a foreach loop avoids having to
        // call addAll(partOfDFAState) on each collection separately which would result in iterating
        // over partOfDFAState three times.
        // The toBeChecked collection is updated when a new NFA state is reached that has not had
        // the epsilon closure performed on it. We manually add the NFA states, in partOfDFAState,
        // to the toBeChecked collection so that they will have the epsilon closure performed on
        // them as well. We also need to manually add the NFA states, in partOfDFAState, to the
        // checkedStates collection so that they are not accidentally added a second time to the
        // toBeChecked collection if taking the epsilon closure of an NFA state leads to one of them.
        for (String state : partOfDFAState) {
            toBeChecked.add(state);
            checkedStates.add(state);
            eClosureOfPartialDFAState.add(state);
        }

        while(!toBeChecked.isEmpty()) {
            String currentState = toBeChecked.pop();
            Set<String> nfaAcceptStates = nfa.getAcceptStates();
            if(nfaAcceptStates.contains(currentState)){
                isAFinishState = true;
            }
            Set<String> epsilonStates = nfa.getResultingStatesInTransitionTable(currentState, "ϵ");
            eClosureOfPartialDFAState.addAll(epsilonStates);
            checkedStates.add(currentState);
            //toBeChecked should not contain already checked states.
            //Remove any states that have already been checked.
            for (String state : epsilonStates){
                if(checkedStates.contains(state)){
                    epsilonStates.remove(state);
                }
            }
            toBeChecked.addAll(epsilonStates);
        }

        partOfDFAState.addAll(eClosureOfPartialDFAState);
        return partOfDFAState;
    }
}
