package org.mql.java.test;

import java.util.List;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;
import org.mql.java.reflection.ProjectExplorer;

public class Examples {

	public Examples() {
		exp01();
	}


	public void exp01() {
		ProjectInfo project = ProjectExplorer.parseProject("D:\\Work-Space-Home\\p01-revision");
		displayProject(project);
	}
	
	public void displayProject(ProjectInfo project) {
		List<PackageInfo> packages = project.getPackages();
        System.out.println("Projet : " + project.getProjectName());
			for (PackageInfo packageE : packages) {
				String packageName = packageE.getPackageName();
				System.out.println("Package : "+ packageName);
				List<ClassInfo> classes = packageE.getClasses();
					for (ClassInfo classE : classes) {
						String className=classE.getClassName().substring(classE.getClassName().lastIndexOf('.') + 1);
						System.out.println("\tClass: " + className + " type : "+ classE.getClassType());
						 System.out.print("\tRelations:");
				            classE.displayRelations();
				            System.out.println();
							if(!classE.getFields().isEmpty()) {
								System.out.println("\tFields: ");
								for (var c : classE.getFields()) {
									System.out.println("\t\t"+c.getFieldName());
								}
							}else if(!classE.getMethods().isEmpty()) {
				            	System.out.println("\tMethods : ");
								for (var c : classE.getMethods()) {
									System.out.println("\t\t"+c.getMethodName()+" : "+c.getReturnType());
								}
							}		
				}
			}
		}
	

	public static void main(String[] args) {
		new Examples();
	}
}