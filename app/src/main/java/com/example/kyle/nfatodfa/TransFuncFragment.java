package com.example.kyle.nfatodfa;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kyle.nfatodfa.FiniteAutomata.NFA;
import com.example.kyle.nfatodfa.FiniteAutomata.NFATransition;
import com.example.kyle.nfatodfa.TransFuncRecyclerView.TransitionsAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTransFuncInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransFuncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransFuncFragment extends Fragment {

    // the fragment initialization parameter
    private static final String ARG_NFA = "NFA";
    private NFA nfa;
    private OnTransFuncInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nfa Parameter 1.
     * @return A new instance of fragment TransFuncFragment.
     */
    public static TransFuncFragment newInstance(NFA nfa) {
        TransFuncFragment fragment = new TransFuncFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NFA, nfa);
        fragment.setArguments(args);
        return fragment;
    }

    public TransFuncFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nfa = getArguments().getParcelable(ARG_NFA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trans_func, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        Activity activity = getActivity();
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.transRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplication()));
        RecyclerView.Adapter adapter = new TransitionsAdapter(activity.getApplication(), nfa);
        recyclerView.setAdapter(adapter);
        Button convertButton = (Button) activity.findViewById(R.id.convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NFATransition> nfaTransitions = nfa.getNFATransitions();
                String[] toStates = TransitionsAdapter.getEditTextData();
                for(int i=0; i<toStates.length; i++){
                    String[] nfaToStates = toStates[i].split("\\s+");
                    NFATransition transition = nfaTransitions.get(i);
                    transition.setToStates(nfaToStates);
                    nfa.setResultingStatesInTransitionTable(transition.getFromState(),
                            transition.getSymbol(), transition.getToStates());
                }
                nfa.setNfaTransitions(nfaTransitions);
                for(NFATransition transition : nfaTransitions){
                    Toast.makeText(getActivity().getApplicationContext(),
                            transition.getTransitionStringFull(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTransFuncInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTransFuncInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTransFuncInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction();
    }

}
