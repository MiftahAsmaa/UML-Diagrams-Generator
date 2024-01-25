package org.mql.java.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.mql.java.models.ProjectInfo;

public class ClassDiagramViewer extends JFrame {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
	private ProjectInfo projectInfo;

    public ClassDiagramViewer(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;

        setTitle(projectInfo.getProjectName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ClassDiagramPanel diagramPanel = new ClassDiagramPanel(projectInfo);
        JScrollPane scrollPane = new JScrollPane(diagramPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        diagramPanel.setPreferredSize(new Dimension(600, 4000));

        add(scrollPane);
    }

}