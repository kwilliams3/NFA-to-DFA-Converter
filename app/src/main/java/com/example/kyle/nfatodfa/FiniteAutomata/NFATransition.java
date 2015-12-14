package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyle on 12/14/15.
 */
public class NFATransition extends Transition implements Parcelable {

    private String[] toStates;

    public NFATransition(String state, String symbol){
        setFromState(state);
        setSymbol(symbol);
    }

    public NFATransition(Parcel nfaTransition){
        setFromState(nfaTransition.readString());
        setSymbol(nfaTransition.readString());
        setToStates(nfaTransition.createStringArray());
    }

    public String[] getToStates() {
        return toStates;
    }

    public String getToStatesAsString(){
        String toStatesString = "";
        for (String state :
                toStates) {
            toStatesString = state + " ";
        }
        toStatesString = toStatesString.trim();

        return toStatesString;
    }

    public void setToStates(String[] toStates) {
        this.toStates = toStates;
    }

    @Override
    public String getFullTransitionString() {
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192 " + getToStatesAsString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(toStates);
    }

    public static final Parcelable.Creator<NFATransition> CREATOR
            = new Parcelable.Creator<NFATransition>() {
        public NFATransition createFromParcel(Parcel nfaTransitionParcel) {
            return new NFATransition(nfaTransitionParcel);
        }

        public NFATransition[] newArray(int size) {
            return new NFATransition[size];
        }
    };
}
