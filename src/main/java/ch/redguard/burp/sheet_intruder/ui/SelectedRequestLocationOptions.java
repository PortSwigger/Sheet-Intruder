package ch.redguard.burp.sheet_intruder.ui;

import burp.api.montoya.core.ToolType;

import java.util.HashMap;
import java.util.Map;

public class SelectedRequestLocationOptions {

    private final Map<ToolType, Boolean> selectedOptions = new HashMap<>(Map.of(
            ToolType.INTRUDER, true,
            ToolType.PROXY, true,
            ToolType.REPEATER, true,
            ToolType.SCANNER, true
    ));

    private static SelectedRequestLocationOptions instance;

    public static SelectedRequestLocationOptions getInstance() {
        if (instance == null) {
            instance = new SelectedRequestLocationOptions();
        }
        return instance;
    }

    private SelectedRequestLocationOptions(){

    }

    public boolean isToolActive(ToolType toolType){
        return selectedOptions.get(toolType);
    }

    public void setStateOfTool(ToolType toolType, boolean selected){
        this.selectedOptions.put(toolType, selected);
    }

    public void resetState() {
        instance = null;
    }
}
