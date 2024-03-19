package ch.redguard.burp.sheet_intruder;

import ch.redguard.burp.sheet_intruder.ui.MainPanel;

import javax.swing.*;
import java.awt.*;

public class TestUI {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Burp Suite - Sheet Intruder");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());

        Dimension preferredSize = new Dimension(1280, 800);
        jFrame.setPreferredSize(preferredSize);
        JMenuBar menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);
        jFrame.pack();
        var uiPanel = new MainPanel();
        var pane = new JScrollPane(uiPanel);
        uiPanel.setPreferredSize(preferredSize);

        Container content = jFrame.getContentPane();
        content.setLayout(new BorderLayout());

        content.add(pane, BorderLayout.WEST);
        jFrame.setVisible(true);
    }
}
