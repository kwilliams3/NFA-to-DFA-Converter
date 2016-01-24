package com.example.kyle.nfatodfa.FiniteAutomata;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Kyle Williams
 * @since 1/24/16.
 */
public class NFAConverterTest {
    private NFA testNFA;
    private String startState;
    private Set<String> states;
    private Set<String> symbols;
    private Set<String> acceptStates;

    @Before
    public void setUp() {
        testNFA = new NFA();
        states = new LinkedHashSet<>();
        symbols = new LinkedHashSet<>();
        startState = "state1";
        acceptStates = new LinkedHashSet<>();

        // Fill states and symbols sets
        for (int i=1; i<=3; i++){
            states.add("state" + i);
            symbols.add("symbol" + i);
        }
        symbols.add("ϵ");
        // Fill acceptStates set
        for (int i=1; i<=2; i++){
            acceptStates.add("state" + i);
        }

        testNFA.setStates(states);
        testNFA.setSymbols(symbols);
        testNFA.setStartState(startState);
        testNFA.setAcceptStates(acceptStates);
    }

    @Test
    public void nfaConverterTest1() {
        Set<String> toStates = new LinkedHashSet<>();
        // States 1,2, and 3 will all have the same transitions
        // Processing 1 will take you to state1
        // Processing 2 will take you to state2
        // Processing 3 will take you to state3
        for (int i=1; i<=3; i++){
            toStates.add("state" + i);
            for (int j=1; j<=3; j++){
                testNFA.setResultingStatesInTransitionTable("state" + j, "symbol" + i, toStates);
            }
            toStates.clear();
        }

        DFA convertedNFA = NFAConverter.convert(testNFA);

        Assert.assertNotNull(convertedNFA);

        DFA correctDFA = new DFA();
        // States should remain the same before and after conversion for this specific test
        correctDFA.states = testNFA.states;
        // Symbols should be the same with the exception of the removal of "ϵ" for the DFA
        Set<String> correctlyConvertedSymbols = testNFA.symbols;
        correctlyConvertedSymbols.remove("ϵ");
        correctDFA.symbols = correctlyConvertedSymbols;
        // Start and accept states should remain the same because there were no ϵ-transitions
        correctDFA.startState = testNFA.startState;
        correctDFA.acceptStates = testNFA.acceptStates;

        // Need to build transition table for the correct DFA
        // It should look exactly the same as the NFA transition table with the exception
        // that the resulting states in a transition will not be of type Set
        for (int i=1; i<=3; i++){
            for (int j=1; j<=3; j++){
                correctDFA.setResultingStateInTransitionTable("state" + j, "symbol" + i, "state" + i);
            }
        }



        // Checking for correctly converted states
        Assert.assertEquals(correctDFA.states, convertedNFA.states);

        // Checking for correctly converted symbols
        Assert.assertEquals(correctlyConvertedSymbols, convertedNFA.symbols);

        // Checking for correctly converted transition table
        Assert.assertEquals(correctDFA.getTransitionTable(), convertedNFA.getTransitionTable());

        // Checking for correctly converted start state
        Assert.assertEquals(correctDFA.startState, convertedNFA.startState);

        // Checking for correctly converted accept states
        Assert.assertEquals(correctDFA.acceptStates, convertedNFA.acceptStates);
    }
}
