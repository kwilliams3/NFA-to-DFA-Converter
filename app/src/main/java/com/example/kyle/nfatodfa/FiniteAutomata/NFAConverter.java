package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by kyle on 1/2/16.
 */
final class NFAConverter {

    // Used to keep track of whether a DFA state is a finish state or not
    private boolean isAFinishState = false;
    private NFA nfa;
    private DFA dfa = new DFA();
    private Set<String> dfaStates = new LinkedHashSet<>();
    private MyStringBuilder newStateBuilder = new MyStringBuilder();
    private MyStringBuilder currentStateBuilder = new MyStringBuilder();
    private String newState;
    private String currentState;

    /**
     * Converts an NFA to an equivalent DFA
     * @return a DFA which accepts only the language that the given NFA accepts
     */
    DFA convert(NFA nfaToBeConverted) {
        nfa = nfaToBeConverted;
        Set<String> dfaSymbols = nfa.symbols;
        dfaSymbols.remove("ϵ");
        dfa.symbols = dfaSymbols;


        return dfa;
    }

    private void powerSetConstruction(){
        Set<String> nfaStartStateAsSet = new LinkedHashSet<>();
        nfaStartStateAsSet.add(nfa.startState);
        Set<String> unformattedDFAStartState = takeEpsilonClosure(nfaStartStateAsSet);
        dfa.startState = fixme;
        newStateBuilder.clear();

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
                newState = newStateBuilder.toString();
                if (newDFAState.isEmpty()){
                    newDFAState = null;
                }
                else if(!dfa.states.contains(newState)){
                    // Eureka! We found a new DFA state!
                    dfaStatesToBeMappedOut.push(newDFAState);
                    dfa.states.add(newState);

                    if(isAFinishState){
                        dfa.acceptStates.add(newState);
                        isAFinishState = false; // Reset the variable
                    }
                }

                newStateBuilder.clear();
                dfa.setResultingStateInTransitionTable(currentDFAState, symbol, newState);
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
     * @param partOfDFAState a partial DFA state that does not contain the NFA states
     *                       which can be reached through zero or more e-transitions
     * @return the resulting set after taking the epsilon closure of partOfDFAState
     */
    private Set<String> takeEpsilonClosure(Set<String> partOfDFAState) {
        if (partOfDFAState.isEmpty()) {
            return partOfDFAState;
        }

        Set<String> eClosureOfPartialDFAState = new LinkedHashSet<>();
        // The epsilon closure could lead to many different states, and those states
        // could circle back around. It is important that we keep track of which NFA states
        // have been checked for e-transitions and which ones have not been checked yet. This
        // will help us to avoid infinite loops where two separate states lead to each other.
        Set<String> checkedStates = new LinkedHashSet<>();
        LinkedList<String> toBeChecked = new LinkedList<>();

        //  Choosing to add partOfDFAState to the two collections in a foreach loop avoids having to
        // call addAll(partOfDFAState) on the collections separately which would result in iterating
        // over partOfDFAState twice.
        // The toBeChecked collection is updated when a new NFA state is reached that has not had
        // the epsilon closure performed on it. We manually add the NFA states, in partOfDFAState,
        // to the toBeChecked collection so that they will have the epsilon closure performed on
        // them as well. We also need to manually add the NFA states, in partOfDFAState, to the
        // checkedStates collection so that none are accidentally added a second time to the
        // toBeChecked collection if taking the epsilon closure of an NFA state leads to one of them.
        for (String state : partOfDFAState) {
            toBeChecked.add(state);
            checkedStates.add(state);
            eClosureOfPartialDFAState.add(state);
            newStateBuilder.append(state);
            newStateBuilder.append(" ");
        }

        while(!toBeChecked.isEmpty()) {
            String currentState = toBeChecked.pop();
            Set<String> nfaAcceptStates = nfa.getAcceptStates();
            if(nfaAcceptStates.contains(currentState)){
                isAFinishState = true;
            }
            Set<String> epsilonStates = nfa.getResultingStatesInTransitionTable(currentState, "ϵ");
            for (String state : epsilonStates) {
                eClosureOfPartialDFAState.add(state);
                newStateBuilder.append(state);
                newStateBuilder.append(" ");
            }
            checkedStates.add(currentState);
            // We need to add the epsilonStates to toBeChecked, and toBeChecked should not contain
            // already checked states. Remove any states that have already been checked.
            for (String state : epsilonStates){
                if(checkedStates.contains(state)){
                    epsilonStates.remove(state);
                }
            }
            toBeChecked.addAll(epsilonStates);
        }

        return eClosureOfPartialDFAState;
    }
}
