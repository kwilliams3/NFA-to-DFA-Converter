package com.example.kyle.nfatodfa;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kyle.nfatodfa.FiniteAutomata.NFA;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStatesSymbolsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatesSymbolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatesSymbolsFragment extends Fragment {
    private NFA nfa;
    private OnStatesSymbolsInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatesSymbolsFragment.
     */
    public static StatesSymbolsFragment newInstance() {
        return new StatesSymbolsFragment();
    }

    public StatesSymbolsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_states_symbols, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        final Activity activity = getActivity();
        Button nextButton = (Button) activity.findViewById(R.id.convert);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String states = ((EditText) activity.findViewById(R.id.stateNamesEdit)).getText().toString();
                // Adds the empty symbol to the alphabet
                String symbols = "ϵ " + ((EditText) activity.findViewById(R.id.symbolsEdit)).getText().toString();
                String startState = ((EditText) activity.findViewById(R.id.startStateEdit)).getText().toString();
                String acceptStates = ((EditText) activity.findViewById(R.id.acceptStatesEdit)).getText().toString();
                if (states.equals("") | symbols.equals("") | startState.equals("") | acceptStates.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.incompleteError,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Set<String> statesSet = new LinkedHashSet<>(Arrays.asList(states.split(" ")));
                    Set<String> symbolsSet = new LinkedHashSet<>(Arrays.asList(symbols.split(" ")));
                    Set<String> acceptStatesSet = new LinkedHashSet<>(Arrays.asList(acceptStates.split(" ")));
                    NFA nfa = new NFA();
                    nfa.setStates(statesSet);
                    nfa.setSymbols(symbolsSet);
                    nfa.setStartState(startState);
                    nfa.setAcceptStates(acceptStatesSet);
                    onNextButtonClick(nfa);
                }
            }
        });
    }

    public void onNextButtonClick(NFA nfa) {
        if (mListener != null) {
            mListener.onStatesSymbolsInteraction(nfa);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStatesSymbolsInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStatesSymbolsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnStatesSymbolsInteractionListener {
        void onStatesSymbolsInteraction(NFA nfa);
    }

}
