package classes.models;

/**
 * Created by jesper on 01-03-2017.
 */
public class ArduinoMethod {
    private String name;
    private int defaultValue;
    private int minValue;
    private int maxValue;
    private int currentValue;
    private String unitName;
    private String unitCount;

    public String getName() {
        return name;
    }

    public ArduinoMethod(String name, int defaultValue, int minValue, int maxValue, int currentValue, String unitName, String unitCount) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.unitName = unitName;
        this.unitCount = unitCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }
}
