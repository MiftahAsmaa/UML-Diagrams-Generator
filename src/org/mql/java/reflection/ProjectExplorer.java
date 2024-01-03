package org.mql.java.reflection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;



public class ProjectExplorer {
	
	public ProjectExplorer() {

	}
	 public static ProjectInfo parseProject(String rootPath) {
	        try {
	        	File srcProject = new File(rootPath);
	            File projectRoot = new File(rootPath+"\\src");
	            String projectName = srcProject.getName();
	            List<PackageInfo> packages = getPackages(projectRoot);
	            return new ProjectInfo(projectName, packages);
	        } catch (Exception e) {
	            System.out.println("Erreur : " + e.getMessage());
	            return null;
	        }
	    }

	    private static List<PackageInfo> getPackages(File srcDirectory) {
	        List<PackageInfo> packageInfos = new ArrayList<>();
	        findPackages(srcDirectory, srcDirectory, packageInfos);
	        return packageInfos;
	    }
	    
	    private static void findPackages(File rootDir, File currentDir, List<PackageInfo> packages) {
	        for (File file : currentDir.listFiles()) {
	            if (file.isDirectory() && !containsSubDirectories(file)) {
	                String packageName = file.getAbsolutePath().substring(rootDir.getAbsolutePath().length() + 1)
	                        .replace(File.separatorChar, '.');
	                packages.add(PackageExplorer.parsePackage(packageName));
	            } else if (file.isDirectory()) {
	                findPackages(rootDir, file, packages);
	            }
	        }
	    }

	    private static boolean containsSubDirectories(File directory) {
	        if (directory.isDirectory()) {
	            File[] files = directory.listFiles();
	            if (files != null) {
	                for (File file : files) {
	                    if (file.isDirectory()) {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }
}