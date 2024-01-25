package org.mql.java.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.mql.java.models.ProjectInfo;
import org.mql.java.xml.XMLParser;

public class Swing extends JFrame {
	private static final long serialVersionUID = 1L;
    private JPanel panel;
    private PackageDiagramPanel packageDiagramPanel;
    private ClassDiagramPanel classDiagramPanel;


    public Swing(String xmlFilePath) {
        ProjectInfo projectInfo = XMLParser.parseProjectXml(xmlFilePath);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(234,205,156));

        packageDiagramPanel = new PackageDiagramPanel(projectInfo);
        classDiagramPanel = new ClassDiagramPanel(projectInfo);

        packageDiagramPanel.setBackground(new Color(234,205,156));
        classDiagramPanel.setBackground(new Color(234,205,156));

        

        JButton packageButton = switchButton("Package Diagram", packageDiagramPanel);
        JButton classButton = switchButton("Class Diagram", classDiagramPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(packageButton);
        buttonPanel.add(classButton);
        buttonPanel.setBackground(new Color(1,63,76));
        add(buttonPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(projectInfo.getProjectName());
    }


    private JButton switchButton(String button, JPanel sPanel) {
        JButton switchButton = new JButton(button);
        switchButton.setBackground(new Color(3,159,190)); 
        switchButton.setForeground(new Color(47, 49, 52));
        switchButton.setFont(new Font("SansSerif", Font.PLAIN, 15));
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.add(new JScrollPane(sPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
                }
        });
        return switchButton;
    }


  
}
