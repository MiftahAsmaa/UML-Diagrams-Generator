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
	            File projectRootName = new File(rootPath);
	            File projectRoot = new File(rootPath + "\\src");
	            String projectName = projectRootName.getName();
	            List<PackageInfo> packages = getPackages(projectRoot, projectRoot.getAbsolutePath());
	            return new ProjectInfo(projectName, packages);
	        } catch (Exception e) {
	            System.out.println("Error : " + e.getMessage());
	        }
		 return null;
	    }
	 private static List<PackageInfo> getPackages(File projectRoot, String projectPath) {
	        List<PackageInfo> packageEntities = new ArrayList<>();
	        findPackages(projectRoot, packageEntities, projectPath, "");
	        return packageEntities;
	    }

	    private static void findPackages(File rootDir, List<PackageInfo> packageInfos, String projectPath, String currentPackage) {
	        File[] files = rootDir.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    if (file.listFiles() != null && file.listFiles().length == 0) {
	                        String packageName = currentPackage.isEmpty() ? file.getName() : currentPackage + "." + file.getName();
	                        addPackage(packageInfos, packageName, projectPath);
	                    } else {
	                        String packageName = currentPackage.isEmpty() ? file.getName() : currentPackage + "." + file.getName();
	                        findPackages(file, packageInfos, projectPath, packageName);
	                    }
	                } else if (file.getName().endsWith(".java")) {
	                    addPackage(packageInfos, currentPackage, projectPath);
	                }
	            }
	        }
	    }

	    private static void addPackage(List<PackageInfo> packageInfos, String packageName, String projectPath) {
	        if (packageInfos.stream().noneMatch(p -> p.getPackageName().equals(packageName))) {
	            packageInfos.add(PackageExplorer.parsePackage( packageName,projectPath));
	        }
	    }
	  
	    
	   
}