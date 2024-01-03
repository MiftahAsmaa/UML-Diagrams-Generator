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
		ProjectInfo project = ProjectExplorer.parseProject("D:\\Work-Space-Home\\Miftah Asmaa - UML Diagrams Generator");
		displayProject(project);
	}
	
	public void displayProject(ProjectInfo project) {
		List<PackageInfo> packages = project.getPackages();
        System.out.println("Projet : " + project.getProjectName());
			for (PackageInfo packageE : packages) {
				String packageName = packageE.getpackageName();
				System.out.println("Package : "+ packageName);
				List<ClassInfo> classes = packageE.getClasses();
					for (ClassInfo classE : classes) {
						String className = classE.getClassName();
						System.out.println("\t"+className);
						if(!classE.getFields().isEmpty()) {
							for (var c : classE.getFields()) {
								System.out.println("\t\t"+c.toString());
						}
				}
			}
		}
	}

	public static void main(String[] args) {
		new Examples();
	}
}