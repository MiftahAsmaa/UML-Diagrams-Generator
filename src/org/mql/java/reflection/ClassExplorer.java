package org.mql.java.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.ClassInfo;

public class ClassExplorer {
	
	public ClassExplorer() {
		
	}
	 public static ClassInfo parseClass(Class<?> classe) {
	        try {
	            String className = classe.getSimpleName();
	            List<String> fieldNames = getFields(classe.getDeclaredFields());
	            return new ClassInfo(className, fieldNames);
	        } catch (Exception e) {
	        	System.out.println("Erreur : "+e.getMessage());
	        	return null; 
	        }
	    }

		public static List<String> getFields(Field[] fields ) {
			List<String> fieldsName = new ArrayList<String>();
				for(Field f : fields) 
					fieldsName.add(f.getName());
			return fieldsName;
		}
		

	}
