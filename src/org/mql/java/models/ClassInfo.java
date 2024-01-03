package org.mql.java.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassInfo {

	   private String className;
	    private List<String> fields;

	    public ClassInfo parseClass(Class<?> classe) {
	        String className = classe.getName();
	        Field[] fields = classe.getDeclaredFields();
	        ClassInfo classInfo = new ClassInfo(className, getFields(fields));
	        return classInfo;
	    }
	    

	    private List<String> getFields(Field[] fields) {
	        List<String> fieldNames = new ArrayList<>();
	        for (Field field : fields) {
	            fieldNames.add(field.getName());
	        }
	        return fieldNames;
	    }
	    
	   
	    
	    
		public ClassInfo(String className, List<String> fields) {
	        this.className = className;
	        this.fields = fields;
	    }
	
		public List<String> getFields() {
			return fields;
		}

		public void setFields(List<String> fields) {
			this.fields = fields;
		}


		public String getClassName() {
			return className;
		}


		public void setClassName(String className) {
			this.className = className;
		}
		
}
