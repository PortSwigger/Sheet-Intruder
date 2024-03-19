package ch.redguard.burp.sheet_intruder.ui;

import burp.api.montoya.core.ToolType;
import ch.redguard.burp.sheet_intruder.excel.ExcelParser;
import ch.redguard.burp.sheet_intruder.tag.TagType;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class MainPanel extends JPanel {

    public MainPanel() {
        this.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("Sheet Intruder");
        Font font = this.getFont().deriveFont(32f).deriveFont(this.getFont().getStyle() | Font.BOLD);
        headerLabel.setFont(font);

        JLabel subtitle = new JLabel("Make Excel Fuzzing Simpler");
        Font subtitleFont = subtitle.getFont().deriveFont(16f).deriveFont(subtitle.getFont().getStyle() | Font.ITALIC);
        subtitle.setFont(subtitleFont);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        var browseButton = new JButton("Choose Excel File...");
        var fileNameTextField = new JTextField();

        var statusLabel = new JLabel();
        browseButton.addActionListener(actionEvent -> browseFiles(this, fileNameTextField, statusLabel));

        var reloadFileButton = new JButton("Reload file");
        reloadFileButton.addActionListener(actionEvent -> loadFile(statusLabel, new File(fileNameTextField.getText())));

        var modifyRequestLocationLabel = new JMultilineLabel("Modify requests in the following features:");
        JPanel modifyRequestLocationPanel = getModifyRequestLocationPanel();

        JLabel howToLabel = new JLabel("How To");
        howToLabel.setFont(this.getFont().deriveFont(20f).deriveFont(this.getFont().getStyle() | Font.BOLD));


        var howTo = new JMultilineLabel("""
                   1. Choose your Excel file (.xls and .xlsx supported) above
                   2. The selected file is loaded into the extension
                   3. In Repeater, Proxy, Scanner or Intruder you are now able to include the tags described below
                   4. Before sending the request the provided excel is read and the requested modifications made
                """);

        var valueReplaceMode = new JLabel("Value Replacement Mode Tag");
        valueReplaceMode.setFont(this.getFont().deriveFont(16f).deriveFont(this.getFont().getStyle() | Font.BOLD));

        var replaceMode = new JTextArea("""
                <START>
                {
                    "valueToReplace": "replacement",
                    "valueToReplace2": "replacement2"
                }
                <END>
                """.replace("<START>", TagType.VALUE_TAG.getStartTag())
                .replace("<END>", TagType.VALUE_TAG.getEndTag())
        );


        replaceMode.setEditable(false);
        replaceMode.setBorder(BorderFactory.createEtchedBorder());

        var howTo2 = new JMultilineLabel("Search for values in cells and replace in the provided Excel file with the " +
                "desired substitution");
        var cellReplacementMode = new JLabel("Cell Replacement Mode Tag");
        cellReplacementMode.setFont(this.getFont().deriveFont(16f).deriveFont(this.getFont().getStyle() | Font.BOLD));


        var cellMode = new JTextArea("""
                <START>
                {
                   "A1": "replacement",
                   "B1": "replacement2"
                }
                <END>

                <START>
                {
                   "A1": "replacement",
                   "CustomSheet!B21": "otherSheetB21"
                }
                <END>
                                
                <START>
                {
                   "A1:B12": "rangeReplacement",
                   "CustomSheet!A1:D5": "otherSheetRange"
                }
                <END>
                """.replace("<START>", TagType.CELL_TAG.getStartTag())
                .replace("<END>", TagType.CELL_TAG.getEndTag())
        );

        var howTo3 = new JMultilineLabel("""
                Replace cells referenced by their cell number with the given substitution
                """);


        var debugCheckBox = new JCheckBox("Log Debug Messages in Burp Event Log", false);
        debugCheckBox.addActionListener(e -> {
            JCheckBox checkbox = (JCheckBox) e.getSource();
            DebugMode.getInstance().setDebug(checkbox.isSelected());
        });

        cellMode.setEditable(false);
        cellMode.setBorder(BorderFactory.createEtchedBorder());

        this.add(headerLabel, getConstraints(0, 0, 3, 1));
        this.add(subtitle, getConstraints(0, 1, 3, 1));
        this.add(separator, getConstraints(0, 2, 3, 1));

        this.add(browseButton, getConstraints(0, 3, 1, 0.1f));
        this.add(fileNameTextField, getConstraints(1, 3, 1, 0.8f));
        this.add(reloadFileButton, getConstraints(2, 3, 1, 0.1f));

        this.add(statusLabel, getConstraints(0, 4, 3, 1));

        this.add(separator, getConstraints(0, 5, 3, 1));

        this.add(modifyRequestLocationLabel, getConstraints(0, 6, 3, 1, new Insets(20, 10, 0, 0)));
        this.add(modifyRequestLocationPanel, getConstraints(0, 7, 3, 1, new Insets(0, 10, 5, 0)));

        this.add(howToLabel, getConstraints(0, 8, 3, 1, new Insets(10, 10, 5, 0)));
        this.add(howTo, getConstraints(0, 9, 3, 1));
        this.add(valueReplaceMode, getConstraints(0, 10, 3, 1, new Insets(10, 10, 5, 0)));
        this.add(replaceMode, getConstraints(0, 11, 3, 1, new Insets(10, 25, 0, 0)));
        this.add(howTo2, getConstraints(0, 12, 3, 1));
        this.add(cellReplacementMode, getConstraints(0, 13, 3, 1, new Insets(10, 10, 5, 0)));
        this.add(cellMode, getConstraints(0, 14, 3, 1, new Insets(10, 25, 0, 0)));
        this.add(howTo3, getConstraints(0, 15, 3, 1));

        this.add(debugCheckBox, getConstraints(0, 16, 3, 1));

        addBottomPadding();
    }

    private static JPanel getModifyRequestLocationPanel() {
        var selectedOptions = SelectedRequestLocationOptions.getInstance();
        JPanel modifyRequestLocationPanel = new JPanel();
        modifyRequestLocationPanel.setLayout(new BoxLayout(modifyRequestLocationPanel, BoxLayout.X_AXIS));

        // Create checkboxes for each value
        JCheckBox scannerCheckbox = new JCheckBox("Scanner", true);
        JCheckBox intruderCheckbox = new JCheckBox("Intruder", true);
        JCheckBox repeaterCheckbox = new JCheckBox("Repeater", true);
        JCheckBox proxyCheckbox = new JCheckBox("Proxy", true);

        // Add checkboxes to the modifyRequestLocationPanel
        modifyRequestLocationPanel.add(scannerCheckbox);
        modifyRequestLocationPanel.add(intruderCheckbox);
        modifyRequestLocationPanel.add(repeaterCheckbox);
        modifyRequestLocationPanel.add(proxyCheckbox);


        ActionListener actionListener = e -> {
            JCheckBox checkbox = (JCheckBox) e.getSource();
            if (checkbox == scannerCheckbox) {
                selectedOptions.setStateOfTool(ToolType.SCANNER, checkbox.isSelected());
            } else if (checkbox == intruderCheckbox) {
                selectedOptions.setStateOfTool(ToolType.INTRUDER, checkbox.isSelected());
            } else if (checkbox == repeaterCheckbox) {
                selectedOptions.setStateOfTool(ToolType.REPEATER, checkbox.isSelected());
            } else if (checkbox == proxyCheckbox) {
                selectedOptions.setStateOfTool(ToolType.PROXY, checkbox.isSelected());
            }
        };

        scannerCheckbox.addActionListener(actionListener);
        intruderCheckbox.addActionListener(actionListener);
        repeaterCheckbox.addActionListener(actionListener);
        proxyCheckbox.addActionListener(actionListener);

        return modifyRequestLocationPanel;
    }

    private void addBottomPadding() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 13;
        this.add(bottomSeparator, c);
    }

    private GridBagConstraints getConstraints(int x, int y, int width, float weightx) {
        Insets insets = new Insets(0, 10, 0, 0);

        return this.getConstraints(x, y, width, weightx, insets);
    }

    private GridBagConstraints getConstraints(int x, int y, int width, float weightx, Insets insets) {
        var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = weightx;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = insets;
        return gbc;
    }


    private void browseFiles(Component panel, JTextField fileNameTextField, JLabel statusLabel) {
        var jfc = new JFileChooser(fileNameTextField.getText());
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls");
            }

            @Override
            public String getDescription() {
                return "Excel Files (.xls, .xlsx)";
            }
        });
        jfc.setDialogTitle("Select Excel File");
        int status = jfc.showOpenDialog(panel);
        statusLabel.setText("No file loaded");
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            fileNameTextField.setText(selectedFile.getPath());
            loadFile(statusLabel, selectedFile);
        }
    }

    private void loadFile(JLabel statusLabel, File selectedFile) {
        statusLabel.setText("Loading File...");
        var validity = ExcelParser.getValidity(selectedFile);
        if (validity.isValid()) {
            SelectedFile.getInstance().setFile(selectedFile);
            statusLabel.setText("File '" + selectedFile.getName() + "' (" + selectedFile.length() + " bytes) loaded");
        } else {
            statusLabel.setText("Error: File is not a valid excel file: " + validity.getReason());
        }
    }

}
