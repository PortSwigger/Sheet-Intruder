package ch.redguard.burp.sheet_intruder;

import burp.api.montoya.extension.ExtensionUnloadingHandler;
import ch.redguard.burp.sheet_intruder.ui.DebugMode;
import ch.redguard.burp.sheet_intruder.ui.SelectedFile;
import ch.redguard.burp.sheet_intruder.ui.SelectedRequestLocationOptions;

public class SheetIntruderUnloadingHandler implements ExtensionUnloadingHandler {
    @Override
    public void extensionUnloaded() {
        SelectedFile.getInstance().resetState();
        DebugMode.getInstance().resetState();
        SelectedRequestLocationOptions.getInstance().resetState();
    }
}
