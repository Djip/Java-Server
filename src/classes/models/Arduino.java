package classes.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jespe on 01-03-2017.
 */
public class Arduino {
    private String name;
    private String ip;
    private Map<String, ArduinoMethod> coreMethods = new HashMap<>();
    private Map<String, ArduinoMethod> groupMethods = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, ArduinoMethod> getCoreMethods() {
        return coreMethods;
    }

    public void setCoreMethods(Map<String, ArduinoMethod> coreMethods) {
        this.coreMethods = coreMethods;
    }

    public Map<String, ArduinoMethod> getGroupMethods() {
        return groupMethods;
    }

    public void setGroupMethods(Map<String, ArduinoMethod> groupMethods) {
        this.groupMethods = groupMethods;
    }
}
