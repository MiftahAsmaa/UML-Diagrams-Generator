package org.mql.java.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassInfo{

	   private String className;
	   private String classType;
	    private List<FieldInfo> fields;
	    private List<MethodInfo> methods;
	    private Map<String, String> relations= new HashMap<>();

	   
		public ClassInfo(String className,String classType, List<FieldInfo> fields, List<MethodInfo> methods) {
			this.className = className;
			this.setClassType(classType);
	        this.fields = fields;
	        this.setMethods(methods);
		}


		public void addRelation(String className, String relationType) {
			relations.put(className, relationType);
		}
		
		public void displayRelations() {
			for (Map.Entry<String, String> entry : relations.entrySet()) {
				System.out.println("   " + entry.getValue() + ": " + entry.getKey());
			}
		}
	
		public List<FieldInfo> getFields() {
			return fields;
		}

		public void setFields(List<FieldInfo> fields) {
			this.fields = fields;
		}


		public String getClassName() {
			return className;
		}


		public void setClassName(String className) {
			this.className = className;
		}

		public List<MethodInfo> getMethods() {
			return methods;
		}

		public void setMethods(List<MethodInfo> methods) {
			this.methods = methods;
		}


		public String getClassType() {
			return classType;
		}


		public void setClassType(String classType) {
			this.classType = classType;
		}
	

}
