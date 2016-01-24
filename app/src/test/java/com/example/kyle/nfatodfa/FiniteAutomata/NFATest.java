package com.example.kyle.nfatodfa.FiniteAutomata;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Tests the methods within the NFA class
 *
 * @author Kyle Williams
 * @since 1/24/16.
 */
public class NFATest {

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
    public void convertToDFA_Should_Not_Return_Null() {
        DFA convertedNFA = testNFA.convertToDFA();
        Assert.assertNotNull(convertedNFA);
    }
}
