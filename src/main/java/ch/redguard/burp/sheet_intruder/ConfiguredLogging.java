package ch.redguard.burp.sheet_intruder;

import burp.api.montoya.logging.Logging;
import ch.redguard.burp.sheet_intruder.ui.DebugMode;

import java.io.PrintStream;

public class ConfiguredLogging implements Logging {

    private final Logging baseLogging;

    ConfiguredLogging(Logging baseLogging) {
        this.baseLogging = baseLogging;
    }

    @Override
    public PrintStream output() {
        return baseLogging.output();
    }

    @Override
    public PrintStream error() {
        return baseLogging.error();
    }

    @Override
    public void logToOutput(String message) {
        baseLogging.logToOutput(message);
    }

    @Override
    public void logToError(String message) {
        baseLogging.logToError(message);
    }

    @Override
    public void logToError(String message, Throwable cause) {
        baseLogging.logToError(message, cause);
    }

    @Override
    public void logToError(Throwable cause) {
        baseLogging.logToError(cause);
    }

    @Override
    public void raiseDebugEvent(String message) {
        if (DebugMode.getInstance().isDebug()) {
            baseLogging.raiseDebugEvent(message);
        }
    }

    @Override
    public void raiseInfoEvent(String message) {
        baseLogging.raiseInfoEvent(message);
    }

    @Override
    public void raiseErrorEvent(String message) {
        baseLogging.raiseErrorEvent(message);
    }

    @Override
    public void raiseCriticalEvent(String message) {
        baseLogging.raiseCriticalEvent(message);
    }
}
