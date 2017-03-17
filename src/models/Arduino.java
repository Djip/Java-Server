package models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jespe on 01-03-2017.
 */
public class Arduino {
    private String name;
    private String ip;
    
    @XStreamAlias("core")
    private List<ArduinoMethod> coreMethods = new ArrayList<>();
    
    @XStreamAlias("group")
    private List<ArduinoMethod> groupMethods = new ArrayList<>();

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

    public List<ArduinoMethod> getCoreMethods() {
        return coreMethods;
    }

    public void setCoreMethods(List<ArduinoMethod> coreMethods) {
        this.coreMethods = coreMethods;
    }

    public List<ArduinoMethod> getGroupMethods() {
        return groupMethods;
    }

    public void setGroupMethods(List<ArduinoMethod> groupMethods) {
        this.groupMethods = groupMethods;
    }
}
