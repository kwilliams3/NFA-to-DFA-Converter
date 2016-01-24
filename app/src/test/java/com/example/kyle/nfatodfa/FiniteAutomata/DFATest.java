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
 * @since 1/24/16.
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
        symbols = new LinkedHashSet<>();
        startState = "startState";
        acceptStates = new LinkedHashSet<>();

        for (int i=1; i<=3; i++){
            states.add("state" + i);
            symbols.add("symbol" + i);
        }
        for (int i=1; i<=2; i++){
            acceptStates.add("state" + i);
        }

        testDFA.setStates(states);
        testDFA.setSymbols(symbols);
        testDFA.setStartState(startState);
        testDFA.setAcceptStates(acceptStates);
    }

    @Test
    public void test() {}
}
