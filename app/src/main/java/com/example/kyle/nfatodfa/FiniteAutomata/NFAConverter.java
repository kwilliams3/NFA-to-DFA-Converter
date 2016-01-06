package com.example.kyle.nfatodfa.FiniteAutomata;

import java.util.HashMap;
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

    private NFAConverter(){}

    private static NFA nfa; // the NFA to be converted

    private static DFA dfa = new DFA(); // the DFA to be returned
    private static Set<String> dfaStates = new LinkedHashSet<>();
    private static Set<String> dfaSymbols;
    private static String dfaStartState;
    private static Set<String> dfaAcceptStates = new LinkedHashSet<>();

    // A DFA state name can be a string containing all the names of the NFA states which it
    // represents. This custom string builder is used in the takeEpsilonClosure method to
    // construct that string which is then later assigned to the DFA state.
    private static MyStringBuilder newFormattedDFAStateBuilder = new MyStringBuilder();

    // Used to construct a string that is used to fill the DFA transition table
    // δ(oldFormattedDFAState, symbol)-->newFormattedDFAState
    private static MyStringBuilder oldFormattedDFAStateBuilder = new MyStringBuilder();

    private static boolean dfaStateIsAnAcceptState = false;

    /**
     * Converts an NFA to an equivalent DFA
     * @return a DFA which accepts only the language that the given NFA accepts
     */
    public static DFA convert(NFA nfaToBeConverted) {
        nfa = nfaToBeConverted;
        powersetConstruction();
        dfa.states = dfaStates;
        dfa.symbols = dfaSymbols;
        dfa.startState = dfaStartState;
        dfa.acceptStates = dfaAcceptStates;
        // DFA transition table is filled out during powersetConstruction()

        return dfa;
    }

    /**
     * Converts an NFA alphabet to a DFA alphabet
     * @return the NFA alphabet with ϵ removed
     */
    private static Set<String> convertNFASymbols(){
        Set<String> dfaSymbols = nfa.symbols;
        dfaSymbols.remove("ϵ");
        return dfaSymbols;
    }

    /**
     * powersetConstruction is a method that makes use of the powerset construction
     * (aka - subset construction, Rabin-Scott powerset construction) method to convert an NFA into
     * a DFA that accepts the same language. This method was originally published by Michael O. Rabin
     * and Dana Scott in 1959.
     *
     * Ultimately, converting an NFA to a DFA is a O(2^n) time complexity. Fortunately, by making
     * use of the powerset construction method, many NFAs can be converted to DFAs in well under a
     * 2^n runtime.
     *
     * Personally, I think that Dr. James F. Power, from Maynooth University in Ireland, best explains
     * the method on his webpage at http://www.cs.nuim.ie/~jpower/Courses/Previous/parsing/node9.html
     * I've included his description below which contains two main points that I've marked as
     * "[#1]" and "[#2]".
     *
     * "We merge together NFA states by looking at them from the point of view of the input
     * characters. [#1] From the point of view of the input, any two states that are connected by an
     * ϵ-transition may as well be the same, since we can move from one to the other without
     * consuming any character. Thus states which are connected by an ϵ-transition will be
     * represented by the same states in the DFA. [#2] If it is possible to have multiple
     * transitions based on the same symbol, then we can regard a transition on a symbol as moving
     * from a state to a set of states (ie. the union of all those states reachable by a transition
     * on the current symbol). Thus these states will be combined into a single DFA state."
     *
     * He then goes on to provide an algorithm (referenced below), which I referred to during the
     * making of my powersetConstruction method.
     *
     * "1.Create the start state of the DFA by taking the ϵ-closure of the start state of the NFA.
     * 2.Perform the following for the new DFA state:
     *      For each possible input symbol:
     *          Apply move to the newly-created state and the input symbol; this will return a set of states.
     *          Apply the ϵ-closure to this set of states, possibly resulting in a new set.
     *      This set of NFA states will be a single state in the DFA.
     * 3.Each time we generate a new DFA state, we must apply step 2 to it. The process is complete
     *  when applying step 2 does not yield any new states.
     * 4.The finish states of the DFA are those which contain any of the finish states of the NFA."
     */
    private static void powersetConstruction(){
        // Unformatted DFA states are sets of sets of strings, and they are only used during the
        // conversion from NFA to DFA. Actual DFA states are simply sets of strings.
        // This is why there is a need for two kinds of fields - sets of unformatted DFA states
        // and sets of formatted states. Both sets are filled during the same loops in order to avoid
        // reiterating over, possibly very long, sets of NFA states.
        Set<String> currentUnformattedDFAState;
        Set<String> newUnformattedDFAState = new LinkedHashSet<>();

        String newFormattedDFAState;
        String oldFormattedDFAState;

        LinkedList<Set<String>> dfaStatesToBeMappedOut = new LinkedList<>();
        dfaStatesToBeMappedOut.push(createDFAStartState()); // Step #1

        oldFormattedDFAState = oldFormattedDFAStateBuilder.toString();

        dfaSymbols = convertNFASymbols();
        while (!dfaStatesToBeMappedOut.isEmpty()) {
            currentUnformattedDFAState = dfaStatesToBeMappedOut.pop();
            for (String symbol : dfaSymbols) { // Step #2
                for (String element : currentUnformattedDFAState) {
                    newUnformattedDFAState.addAll(move(element, symbol)); // "apply move"
                    newUnformattedDFAState.addAll(takeEpsilonClosure(newUnformattedDFAState)); // "apply e-closure"
                }

                newFormattedDFAState = newFormattedDFAStateBuilder.toString();

                if(!NFAConverter.dfaStates.contains(newFormattedDFAState)){
                    // Eureka! We found a new DFA state!
                    dfaStatesToBeMappedOut.push(newUnformattedDFAState);
                    NFAConverter.dfaStates.add(newFormattedDFAState);
                    if(dfaStateIsAnAcceptState){
                        NFAConverter.dfaAcceptStates.add(newFormattedDFAState);
                        dfaStateIsAnAcceptState = false; // Reset the variable
                    }
                }
                newUnformattedDFAState.clear();

                dfa.setResultingStateInTransitionTable(oldFormattedDFAState, symbol, newFormattedDFAState);

                oldFormattedDFAState = newFormattedDFAStateBuilder.toString();
                oldFormattedDFAStateBuilder.replaceWith(newFormattedDFAState);
                newFormattedDFAStateBuilder.clear();
            }

        }
    }

    /**
     * Creates the DFA start state and stores it in the dfaStartState variable. It does not
     * return the DFA start state, however. Instead, it returns a set containing NFA states, which
     * represents the DFA start state. This is because the set of strings will be used in future
     * calculations to map out the DFA.
     * @return a set containing NFA states which represents the DFA start state
     */
    private static Set<String> createDFAStartState(){
        Set<String> nfaStartStateAsSet = new LinkedHashSet<>();
        nfaStartStateAsSet.add(nfa.startState);
        Set<String> unformattedDFAStartState = takeEpsilonClosure(nfaStartStateAsSet);
        String newFormattedDFAState = newFormattedDFAStateBuilder.toString();
        NFAConverter.dfaStartState = newFormattedDFAState;
        oldFormattedDFAStateBuilder.replaceWith(newFormattedDFAState);
        newFormattedDFAStateBuilder.clear();
        return unformattedDFAStartState;
    }

    private static Set<String> move(String element, String symbol){
        return nfa.getResultingStatesInTransitionTable(element, symbol);
    }

    /**
     * Takes the epsilon closure of a set of NFA states.
     * @param partOfNewDFAState a part of the new DFA state
     * @return the resulting set after taking the epsilon closure of partOfNewDFAState
     */
    private static Set<String> takeEpsilonClosure(Set<String> partOfNewDFAState) {
        if (partOfNewDFAState.isEmpty()) {
            return partOfNewDFAState;
        }

        // The epsilon closure could lead to many different states, and those states
        // could circle back around. It is important that we keep track of which NFA states
        // have been checked for e-transitions and which ones have not been checked yet. This
        // will help us to avoid infinite loops where two separate states lead to each other.
        Set<String> checkedStates = new LinkedHashSet<>();
        LinkedList<String> toBeChecked = new LinkedList<>();

        Set<String> eClosureOfNewDFAState = new LinkedHashSet<>();

        // Choosing to add partOfNewDFAState to the two collections in a foreach loop avoids having to
        // call addAll(partOfNewDFAState) on the collections separately which would result in iterating
        // over partOfNewDFAState twice.
        // The toBeChecked collection is updated when a new NFA state is reached that has not had
        // the epsilon closure performed on it. We manually add the NFA states, in partOfNewDFAState,
        // to the toBeChecked collection so that they will have the epsilon closure performed on
        // them as well. We also need to manually add the NFA states, in partOfNewDFAState, to the
        // checkedStates collection so that none are accidentally added a second time to the
        // toBeChecked collection if taking the epsilon closure of an NFA state leads to one of them.
        for (String state : partOfNewDFAState) {
            toBeChecked.add(state);
            checkedStates.add(state);
            eClosureOfNewDFAState.add(state);
            newFormattedDFAStateBuilder.append(state);
        }

        Set<String> epsilonStates;
        while(!toBeChecked.isEmpty()) {
            String currentNFAState = toBeChecked.pop();
            // If currentNFAState is an accept state, then the entire
            // DFA state that it accompanies will also be an accept state
            if (nfa.acceptStates.contains(currentNFAState)) {
                dfaStateIsAnAcceptState = true;
            }
            epsilonStates = nfa.getResultingStatesInTransitionTable(currentNFAState, "ϵ");
            for (String state : epsilonStates) {
                if (!eClosureOfNewDFAState.contains(state)){
                    newFormattedDFAStateBuilder.append(state);
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
}
