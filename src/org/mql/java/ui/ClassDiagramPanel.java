package org.mql.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;

public class ClassDiagramPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private ProjectInfo projectInfo;

    public ClassDiagramPanel(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 50;
        int y = 50;
        int classesPerRow = 2;

        List<PackageInfo> packages = projectInfo.getPackages();
        for (PackageInfo pkg : packages) {
            List<ClassInfo> classes = pkg.getClasses();
            for (int i = 0; i < classes.size(); i++) {
                drawClass(g, classes.get(i), x, y);
                x += 300;

                if (i % classesPerRow == classesPerRow - 1) {
                    x = 100;
                    y += calculateMaxClassHeight(classes, i, classesPerRow) + 50; 
                }
            }
        }
    }

    private int calculateMaxClassHeight(List<ClassInfo> classes, int currentIndex, int classesPerRow) {
        int maxHeight = 0;

        for (int i = currentIndex - (currentIndex % classesPerRow); i <= currentIndex; i++) {
            if (i < classes.size()) {
                ClassInfo currentClass = classes.get(i);
                int classHeight = calculateClassHeight(currentClass);

                if (classHeight > maxHeight) {
                    maxHeight = classHeight;
                }
            }
        }

        return maxHeight;
    }

    private void drawClass(Graphics g, ClassInfo classInfo, int x, int y) {
        g.setColor(new Color(245, 245, 220));

        int classHeight = calculateClassHeight(classInfo);
        g.fillRect(x, y, 210, classHeight);

        g.setColor(Color.black);
        g.drawRect(x, y, 210, classHeight);

        g.setColor(Color.black);
        g.drawString(classInfo.getClassName(), x + 40, y + 20);

        y += 30;
        g.drawLine(x, y, x + 210, y);

        g.setColor(Color.black);
        y += 15;
        String mod = "+";

        for (FieldInfo field : classInfo.getFields()) {
            if (field.getFieldModifier().contains("private")) {
                mod = "-";
            } else if (field.getFieldModifier().contains("protected")) {
                mod = "#";
            } else if (field.getFieldModifier().contains("package")) {
                mod = "~";
            }

            g.drawString(mod + " " + field.getFieldName() + " : " + field.getFieldType(), x + 10, y);
            y += 15;
        }

        if (!classInfo.getFields().isEmpty()) {
            g.drawLine(x, y, x + 210, y);
            y += 15;
        }

        for (MethodInfo method : classInfo.getMethods()) {
            if (method.getMethodModifier().contains("private")) {
                mod = "-";
            } else if (method.getMethodModifier().contains("protected")) {
                mod = "#";
            } else if (method.getMethodModifier().contains("package")) {
                mod = "~";
            }

            List<String> params = method.getParameters();
            String parameters = String.join(", ", params);
            g.setColor(Color.black);
            g.drawString(mod + " " + method.getReturnType() + " " + method.getMethodName() + "( " + parameters + ")", x + 10, y);

            y += 15;
        }
    }

    private int calculateClassHeight(ClassInfo classInfo) {
        int classHeight = 30;
        int lineHeight = 15;

        classHeight += lineHeight;

        if (!classInfo.getFields().isEmpty()) {
            classHeight += classInfo.getFields().size() * lineHeight;
            classHeight += lineHeight;
        }

        if (!classInfo.getMethods().isEmpty()) {
            classHeight += classInfo.getMethods().size() * lineHeight;
        }

        if (classInfo.getFields().isEmpty() && classInfo.getMethods().isEmpty()) {
            classHeight += lineHeight; 
        }

        return classHeight;
    }

    @Override
    public Dimension getPreferredSize() {
        int totalHeight = 200; 

        List<PackageInfo> packages = projectInfo.getPackages();
        for (PackageInfo pkg : packages) {
            List<ClassInfo> classes = pkg.getClasses();
            for (ClassInfo classInfo : classes) {
                totalHeight += calculateClassHeight(classInfo); 
            }
        }

        return new Dimension(3000, totalHeight);
    }
}
