package org.mql.java.reflection;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.PackageInfo;

public class PackageExplorer {
	
	public PackageExplorer() {

	}
	
	public static PackageInfo parsePackage(String packageName, String projectPath) {
	    try {
	        List<ClassInfo> classes = findClasses(packageName, projectPath);
	        return new PackageInfo(packageName, classes);
	    } catch (Exception e) {
	        System.out.println("Error : " + e.getMessage());
	    }
	    return null;
	}

	public static List<ClassInfo> findClasses(String packageName, String projectPath) {
	    List<ClassInfo> classInfos = new Vector<>();
	    try {
	        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        String packagePath = packageName.replace('.', File.separatorChar);
	        String bin = projectPath.replace("src", "bin");
	        Set<String> SetClasses = new HashSet<>();

	        bin = bin.endsWith(File.separator + "bin") ? bin : bin + File.separator + "bin";

			URLClassLoader urlClassLoader = new URLClassLoader(
	                new URL[]{new File(bin).toURI().toURL()},
	                classLoader
	        );
	        Enumeration<URL> resources = urlClassLoader.getResources(packagePath);
	        while (resources.hasMoreElements()) {
	            URL resource = resources.nextElement();
	            if (resource.getProtocol().equals("file")) {
	                File classDir = new File(resource.toURI());
	                if (classDir.exists()) {
	                    File[] classFiles = classDir.listFiles((dir, name) -> name.endsWith(".class"));
	                    if (classFiles != null) {
	                        for (File classFile : classFiles) {
	                            String className = packageName + '.' + classFile.getName().replace(".class", "");
	                            if (SetClasses.add(className)) {
		                            Class<?> classe = urlClassLoader.loadClass(className);
		                            classInfos.add(ClassExplorer.parseClass(classe));
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return classInfos;
	}
}