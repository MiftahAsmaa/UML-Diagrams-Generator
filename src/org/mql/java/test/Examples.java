package org.mql.java.test;

import java.util.List;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;
import org.mql.java.models.RelationInfo;
import org.mql.java.reflection.ProjectExplorer;
import org.mql.java.xml.XMIWriter;
import org.mql.java.xml.XMLWriter;

public class Examples {

	public Examples() {
		exp01();
	}


	public void exp01() {
		ProjectInfo project = ProjectExplorer.parseProject("D:\\Work-Space-Home\\p01-revision");
		displayProject(project);

        XMLWriter.writeProjectToXML(project, "resources/output.xml");
		XMIWriter.writeProjectToXMI(project, "resources/outputxmi.xmi");
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
						System.out.println("\n\tClass: " + className + " type : "+ classE.getClassType());
						//if("Class".equals(classE.getClassType())) {
						 System.out.print("\t\tRelations:\n");
						 if(classE.getRelations2() != null) {
								for (RelationInfo	r : classE.getRelations2()) {
									System.out.println("\t\t\t"+r.getDescription());
								}
							}
						//}
				            System.out.println();
				            if(!classE.getConstructors().isEmpty()) {
				            	System.out.println("\t\tConstructors: ");
				            	for (var c : classE.getConstructors()) {
									System.out.println("\t\t\t"+c.getconstructorModifier()+"  "+c.getconstructorName());
								}
				            }
							if(!classE.getFields().isEmpty()) {
								System.out.println("\t\tFields: ");
								for (var c : classE.getFields()) {
									System.out.println("\t\t\t"+c.getFieldName());
								}
							}else if(!classE.getMethods().isEmpty()) {
				            	System.out.println("\t\tMethods : ");
								for (var c : classE.getMethods()) {
									System.out.println("\t\t\t"+c.getMethodName()+" : "+c.getReturnType());
								}
							}		
				}
			}
		}
	

	public static void main(String[] args) {
		new Examples();
	}
}