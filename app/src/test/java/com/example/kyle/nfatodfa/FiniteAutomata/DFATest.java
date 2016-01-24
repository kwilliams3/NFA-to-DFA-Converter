package com.example.kyle.nfatodfa.FiniteAutomata;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.Override;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Tests the methods within the DFA class
 *
 * @author Kyle Williams
 * @since 1/22/16.
 */
public class DFATest {

    DFA testDFA;
    String startState;
    Set<String> states;
    Set<String> symbols;
    Set<String> acceptStates;

    @Before
    public void setUp() {
        testDFA = new DFA();
        states = new LinkedHashSet<>();
        states.add("state1");
        states.add("state2");
        states.add("state3");
        symbols = new LinkedHashSet<>();
        symbols.add("symbol1");
        symbols.add("symbol2");
        symbols.add("symbol3");
        startState = "startState";
        acceptStates = new LinkedHashSet<>();
        acceptStates.add("state1");
        acceptStates.add("state2");

        testDFA.setStates(states);
        testDFA.setSymbols(symbols);
        testDFA.setStartState(startState);
        testDFA.setAcceptStates(acceptStates);
    }

    @Test
    public void test() {}
}
