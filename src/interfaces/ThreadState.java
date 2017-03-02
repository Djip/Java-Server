package interfaces;

/**
 * Created by jespe on 01-03-2017.
 */
public interface ThreadState {
    void initializeClientObject();
    void communicating();
    void cleanUp();
}
