package org.mql.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.MethodInfo;

public class ClassExplorer {

	public ClassExplorer() {
		
	}
	public static ClassInfo parseClass(Class<?> classe) {
		        String className = classe.getName();
		        String classType = getClassType(classe);
		        List<FieldInfo> fields = getFields(classe.getDeclaredFields());
		        List<MethodInfo> methods = getMethods(classe.getDeclaredMethods());
		    
		        ClassInfo classInfo = new ClassInfo(className,classType, fields, methods);
		  
        Class<?> superclass = classe.getSuperclass();
        if (superclass != null) {
            classInfo.addRelation(superclass.getSimpleName(), "extends");
        }

        Class<?>[] interfaces = classe.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            classInfo.addRelation(interfaceClass.getSimpleName(), "implements");
        }
        detectAssociationRelations(classe, classInfo);

        return classInfo;
    }

    private static void detectAssociationRelations(Class<?> classe, ClassInfo classInfo) {
        Field[] fields = classe.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String relationType = "association"; 

            if (fieldType.equals(classe)) {
                relationType = "self-association";
            } else if (fieldType.isAssignableFrom(List.class) || fieldType.isArray()) {
                relationType = "aggregation";
            } else if (fieldType.isPrimitive() || fieldType.equals(String.class)) {
                relationType = "attribute";
            }

            classInfo.addRelation(fieldType.getSimpleName(), relationType);
        }
    }
    
    
    private static List<FieldInfo> getFields(Field[] fields) {
        List<FieldInfo> fieldInfos = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            fieldInfos.add(new FieldInfo(fieldName, fieldType));
        }
        return fieldInfos;
    }

    private static List<MethodInfo> getMethods(Method[] methods) {
        List<MethodInfo> methodInfos = new ArrayList<>();
        for (Method method : methods) {
            String methodName = method.getName();
            String returnType = method.getReturnType().getName();
            List<String> parameterTypes = getParameterTypes(method.getParameterTypes());
            methodInfos.add(new MethodInfo(methodName, returnType, parameterTypes));
        }
        return methodInfos;
    }

    public static List<String> getParameterTypes(Class<?>[] parameterTypes) {
        List<String> parameterTypeNames = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            parameterTypeNames.add(parameterType.getName());
        }
        return parameterTypeNames;
    }
    
    public static String getClassType(Class<?> className) {
        try {
            if (className.isInterface()) {
                return "Interface";
            } else if (className.isEnum()) {
                return "Enum";
            } else {
                return "Class";
            }
        } catch (Exception e) {
           System.out.println("Erreur: "+e.getMessage());
        }
        return "";
    }

	}
