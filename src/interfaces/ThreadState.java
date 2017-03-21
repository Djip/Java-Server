package interfaces;

/**
 * Created by jespe on 01-03-2017.
 */
public interface ThreadState {
    void initializeClientObject();
    void communicating(String methodName, int newValue);
    void communicating(String methodName, int newValue, String unitName, int unitId);
    void heartbeat();
    void cleanUp();
}
