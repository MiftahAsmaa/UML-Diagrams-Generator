package org.mql.java.reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.PackageInfo;

public class PackageExplorer {
	
	public PackageExplorer() {

	}
	
    public static PackageInfo parsePackage(String packageName) {
        try {
            List<ClassInfo> classes = findClasses(packageName);

            return new PackageInfo(packageName, classes);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return null; 
        }
    }
    
    private static List<ClassInfo> findClasses(String packageName) {
        List<ClassInfo> classInfos = new ArrayList<>();
        try {
            String classPath = System.getProperty("java.class.path");
            String packageFolder = packageName.replace('.', File.separatorChar);
            File classDir = new File(classPath + File.separator + packageFolder);
            if (classDir.exists()) {
            	
                File[] classFiles = classDir.listFiles((dir, name) -> name.endsWith(".class"));
                if (classFiles != null) {
                    for (File classFile : classFiles) {
                        String className = packageName + '.' + classFile.getName().replace(".class", "");
                        Class<?> classe = Class.forName(className);
                        classInfos.add(ClassExplorer.parseClass(classe));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classInfos;
    }
   

	
}
