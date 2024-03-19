package ch.redguard.burp.sheet_intruder.ui;

public class DebugMode {
    private static DebugMode instance;
    private boolean debug = false;

    public static DebugMode getInstance() {
        if (instance == null) {
            instance = new DebugMode();
        }
        return instance;
    }

    private DebugMode(){

    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public void resetState() {
        instance = null;
    }
}
