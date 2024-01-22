package org.mql.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.ConstructorInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.RelationInfo;
import org.mql.java.models.RelationInfo.RelationType;

public class ClassExplorer {

	public ClassExplorer() {
		
	}
	public static ClassInfo parseClass(Class<?> classe) {
		        String className = classe.getSimpleName();
		        String classType = getClassType(classe);
		        List<FieldInfo> fields = getFields(classe.getDeclaredFields());
		        List<MethodInfo> methods = getMethods(classe.getDeclaredMethods());
		        List<ConstructorInfo> constructorsNames = getConstructors(classe.getDeclaredConstructors(),className);
		        ClassInfo classInfo = new ClassInfo(className,classType, fields, methods,constructorsNames);
		        
		        if(!classe.isAnnotation() && !classe.isEnum() && !classe.isInterface()) {
	            	classInfo.setRelations2(getRelations(classe));
		        }
        return classInfo;
    }

	public static List<RelationInfo> getRelations(Class<?> classe) {
		List<RelationInfo> relations = new ArrayList<>();
		RelationInfo re;
		Class<?> superClass = classe.getSuperclass();
	    if (superClass != null && !superClass.equals(Object.class)) {
	        re = new RelationInfo(RelationType.EXTENSION, classe.getSimpleName(), superClass.getSimpleName());
	        relations.add(re);
	    }
	    
	    Class<?>[] interfaces = classe.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            re = new RelationInfo(RelationType.IMPLEMENTATION, classe.getSimpleName(), interfaceClass.getSimpleName());
	        relations.add(re);
        }
        Set<Class<?>> nduplicates = new HashSet<>();
        for (Field field : classe.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (!nduplicates.contains(fieldType) && !fieldType.isPrimitive()) {
                if (isAggregation(field)) {
                    Class<?> elementType = getAggregationElementType(field);
                    if(!elementType.isPrimitive()) {
	                    re = new RelationInfo(RelationType.AGGREGATION, classe.getSimpleName(), elementType.getSimpleName());
	                    relations.add(re);
                    }
                }
                else if (isAssociation(classe,field)) {
                    re = new RelationInfo(RelationType.ASSOCIATION, classe.getSimpleName(), fieldType.getSimpleName());
                    relations.add(re);
                } else if (isComposition(field)) {
                    re = new RelationInfo(RelationType.COMPOSITION, classe.getSimpleName(), fieldType.getSimpleName());
                    relations.add(re);
                } 
                nduplicates.add(fieldType);
            }
        }
        nduplicates.clear();
        for (Method method : classe.getDeclaredMethods()) {
            for (Class<?> parameterType : method.getParameterTypes()) {
            	if(!nduplicates.contains(parameterType) && !parameterType.isPrimitive()) {
            		re = new RelationInfo(RelationType.UTILISATION, classe.getSimpleName(), parameterType.getSimpleName());
            		relations.add(re);
            		nduplicates.add(parameterType);
            	}
            }
        }
        nduplicates.clear();
        
		return relations;
	}
	
	private static boolean isAssociation(Class<?> classe, Field field) {
	    Set<Class<?>> processedTypes = new HashSet<>();
	    Class<?> fieldClass = field.getType();
	    if (!processedTypes.contains(fieldClass) && !fieldClass.isPrimitive()) {
	        processedTypes.add(fieldClass);
	        return Arrays.stream(fieldClass.getDeclaredFields())
	                .anyMatch(innerField -> innerField.getType().equals(classe));
	    }
	    return false;
	}

	private static boolean isAggregation(Field field) {
	    return Collection.class.isAssignableFrom(field.getType()) || field.getType().isArray();
	}

	private static Class<?> getAggregationElementType(Field field) {
	    if (field.getType().isArray()) {
	        return field.getType().getComponentType();
	    } else if (Collection.class.isAssignableFrom(field.getType())) {
	        Type genericType = field.getGenericType();
	        if (genericType instanceof ParameterizedType) {
	            Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
	            if (typeArguments.length > 0) {
	                if (typeArguments[0] instanceof Class) {
	                    return (Class<?>) typeArguments[0];
	                }
	            }
	        }
	    }
	    return null;
	}
	
	private static boolean isComposition(Field field) {
	    return !field.getType().isPrimitive() && !field.getType().isArray() && !Collection.class.isAssignableFrom(field.getType());
	}
    
    
    private static List<FieldInfo> getFields(Field[] fields) {
        List<FieldInfo> fieldInfos = new Vector<FieldInfo>();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldModifier = field.getModifiers();
            String fieldType = field.getType().getSimpleName();
            fieldInfos.add(new FieldInfo(fieldName,Modifier.toString(fieldModifier), fieldType));
        }
        return fieldInfos;
    }

    private static List<MethodInfo> getMethods(Method[] methods) {
        List<MethodInfo> methodInfos = new Vector<MethodInfo>();
        for (Method method : methods) {
            String methodName = method.getName();
            int methodModifier = method.getModifiers();
            String returnType = method.getReturnType().getSimpleName();
            List<String> parameterTypes = getParameterTypes(method.getParameterTypes());
            methodInfos.add(new MethodInfo(methodName,Modifier.toString(methodModifier), returnType, parameterTypes));
        }
        return methodInfos;
    }

    public static List<String> getParameterTypes(Class<?>[] parameterTypes) {
        List<String> parameterTypeNames = new Vector<>();
        for (Class<?> parameterType : parameterTypes) {
            parameterTypeNames.add(parameterType.getSimpleName());
        }
        return parameterTypeNames;
    }
    
    private static List<ConstructorInfo> getConstructors(Constructor<?>[] constructors, String className) {
        List<ConstructorInfo> constructorInfos = new Vector<ConstructorInfo>();
        for (Constructor<?> constructor : constructors) {
            String constructorName = className;
            int constructorModifier = constructor.getModifiers();
            List<String> parameterTypes = getParameterTypes(constructor.getParameterTypes());
            constructorInfos.add(new ConstructorInfo(constructorName,Modifier.toString(constructorModifier), parameterTypes));
        }
        return constructorInfos;
    }

//    public static String getSuperClass(Class<?> classe) {
//	    if (classe != null) {
//	    	if(classe.getSuperclass() != null && !classe.getSuperclass().getSimpleName().equals("Object")) {
//	    		return classe.getSuperclass().getSimpleName();
//	    	}else {
//				return null;
//			}
//	    } else {
//	        return null;
//	    }
//	}
    public static String getClassType(Class<?> className) {
        try {
            if (className.isInterface()) {
                return "Interface";
            } else if (className.isEnum()) {
                return "Enum";
            }else if(className.isAnnotation()) {
            	return "Annotation";
            } else {
                return "Class";
            }
        } catch (Exception e) {
           System.out.println("Erreur: "+e.getMessage());
        }
        return "";
    }

	}
