package org.mql.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;

public class PackageDiagramPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private ProjectInfo projectInfo;

    public PackageDiagramPanel(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 120;
        int y = 20;
        int classesPerRow = 2; 

        List<PackageInfo> packages = projectInfo.getPackages();
        for (int i = 0; i < packages.size(); i++) {
            drawPackage(g, packages.get(i), x, y );
            x+=300;
            if (i % classesPerRow == classesPerRow - 1) {
                x = 100;
                y += calculateMaxClassHeight(packages, i, classesPerRow) + 50; 
            }
        }
    }
  
    private int calculateMaxClassHeight(List<PackageInfo> classes, int currentIndex, int classesPerRow) {
        int maxHeight = 0;

        for (int i = currentIndex - (currentIndex % classesPerRow); i <= currentIndex; i++) {
            if (i < classes.size()) {
                PackageInfo currentClass = classes.get(i);
                int classHeight = calculatePackageHeight(currentClass);

                if (classHeight > maxHeight) {
                    maxHeight = classHeight;
                }
            }
        }
		return maxHeight;
    }
       
        private void drawPackage(Graphics g, PackageInfo packageInfo, int x, int y) {
            g.setColor(new Color(245, 245, 220));

            int classHeight = calculatePackageHeight(packageInfo);
            g.fillRect(x, y, 210, classHeight);

            g.setColor(Color.black);
            g.drawRect(x, y, 210, classHeight);

            g.setColor(Color.black);
            g.drawString(packageInfo.getPackageName(), x + 40, y + 20);

            y += 30;
            g.drawLine(x, y, x + 210, y);

            g.setColor(Color.black);
            y += 15;

            for (ClassInfo classInfo : packageInfo.getClasses()) {
                g.drawString("-" + classInfo.getClassType() + " : " + classInfo.getClassName(), x + 10, y);
                y += 15;
            }
                y += 15;
            }
        

        private int calculatePackageHeight(PackageInfo classInfo) {
            int classHeight = 30;
            int lineHeight = 15;

            classHeight += lineHeight;

            if (!classInfo.getClasses().isEmpty()) {
                classHeight += classInfo.getClasses().size() * lineHeight;
                classHeight += lineHeight;
            }
            return classHeight;
        }

        @Override
        public Dimension getPreferredSize() {
            int totalHeight = 200; 

            List<PackageInfo> packages = projectInfo.getPackages();
            for (PackageInfo pkg : packages) {
                    totalHeight += calculatePackageHeight(pkg); 
                
            }

            return new Dimension(3000, totalHeight);
        }
   
}


