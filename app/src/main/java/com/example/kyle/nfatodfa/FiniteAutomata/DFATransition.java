package com.example.kyle.nfatodfa.FiniteAutomata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyle on 12/14/15.
 */
public class DFATransition extends Transition implements Parcelable {

    private String toState;

    public DFATransition(String state, String symbol){
        setFromState(state);
        setSymbol(symbol);
    }

    public DFATransition(Parcel dfaTransitionParcel){
        setFromState(dfaTransitionParcel.readString());
        setSymbol(dfaTransitionParcel.readString());
        setToState(dfaTransitionParcel.readString());
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    @Override
    public String getFullTransitionString() {
        return "\uD835\uDEFF(" + fromState + ", " +
                symbol + ") \u2192 " + toState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(toState);
    }

    public static final Parcelable.Creator<DFATransition> CREATOR
            = new Parcelable.Creator<DFATransition>() {
        public DFATransition createFromParcel(Parcel dfaTransitionParcel) {
            return new DFATransition(dfaTransitionParcel);
        }

        public DFATransition[] newArray(int size) {
            return new DFATransition[size];
        }
    };
}
