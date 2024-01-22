package org.mql.java.models;

import java.util.List;

public class ClassInfo{

	   private String className;
	   private String classType;
	    private List<FieldInfo> fields;
	    private List<MethodInfo> methods;
	    private List<ConstructorInfo> constructors;
	    private List<RelationInfo> relations;
	   
		public ClassInfo(String className,String classType, List<FieldInfo> fields, List<MethodInfo> methods) {
			this.className = className;
			this.setClassType(classType);
	        this.fields = fields;
	        this.setMethods(methods);
		}
		public ClassInfo(String className,String classType, List<FieldInfo> fields, List<MethodInfo> methods,List<ConstructorInfo> constructors) {
			this.className = className;
			this.setClassType(classType);
	        this.fields = fields;
	        this.setMethods(methods);
	        this.constructors=constructors;
		}
		public ClassInfo(String className,String classType, List<FieldInfo> fields, List<MethodInfo> methods,List<ConstructorInfo> constructors,List<RelationInfo> relations) {
			this.className = className;
			this.setClassType(classType);
	        this.fields = fields;
	        this.setMethods(methods);
	        this.constructors=constructors;
	        this.relations = relations;
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
		public List<ConstructorInfo> getConstructors() {
			return constructors;
		}
		public void setConstructors(List<ConstructorInfo> constructors) {
			this.constructors = constructors;
		}
		public List<RelationInfo> getRelations2() {
			return relations;
		}
		public void setRelations2(List<RelationInfo> relations2) {
			this.relations = relations2;
		}
}
