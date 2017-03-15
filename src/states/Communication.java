package states;

import interfaces.ThreadState;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by jespe on 01-03-2017.
 */
public class Communication implements ThreadState {


    @Override
    public void initializeClientObject() {
 // not in this state
    }

    @Override
    public void communicating() {
// til client fra UWP

    }

    @Override
    public void cleanUp() {

    }
}
