package com.example.kyle.nfatodfa;

import android.content.Context;

/**
 * Created by kyle on 11/24/15.
 */
public class Transitions {
    private static Transitions sTransitions;

    public static Transitions get(Context context){
        if(sTransitions == null){
            sTransitions = new Transitions(context);
        }
        return sTransitions;
    }

    private Transitions(Context context){

    }
}
