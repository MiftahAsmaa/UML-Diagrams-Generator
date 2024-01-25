package org.mql.java.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.mql.java.models.ProjectInfo;

public class PackageDiagramViewer extends JFrame {

    private static final long serialVersionUID = 1L;
    public PackageDiagramViewer(ProjectInfo projectInfo) {
        setTitle(projectInfo.getProjectName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PackageDiagramPanel diagramPanel = new PackageDiagramPanel(projectInfo);
        JScrollPane scrollPane = new JScrollPane(diagramPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        diagramPanel.setPreferredSize(new Dimension(3000, 600));

        add(scrollPane);
    }

}
