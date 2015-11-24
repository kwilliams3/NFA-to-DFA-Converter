package com.example.kyle.nfatodfa;

import java.util.UUID;

/**
 * Created by kyle on 11/24/15.
 */
public class Transition {
    private UUID mId;
    private String mTransition;

    public Transition(){
        // Generate unique identifier
        mId = UUID.randomUUID();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTransition() {
        return mTransition;
    }

    public void setmTransition(String mTransition) {
        this.mTransition = mTransition;
    }
}
