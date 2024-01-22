package org.mql.java.models;

public class RelationInfo {
	
	private RelationType type;
	private String description;
	private String sourceClass;
	private String targetClass;
	
    public enum RelationType {
    	ASSOCIATION,
        COMPOSITION,
        AGGREGATION,
        UTILISATION,
        EXTENSION,
        IMPLEMENTATION,
    }


    public RelationInfo(RelationType type, String sourceClass, String targetClass) {
        this.type = type;
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

<<<<<<< HEAD
    
	public void setDescription(String description) {
		this.description = description;
	}

=======
>>>>>>> ddb01e3b545d8fa4f30c6f4d282c81730d8d9c37
	public String getDescription() {
	    switch (this.type) {
	        case AGGREGATION:
	            return String.format(type + " Class %s is aggregated with class %s.", sourceClass, targetClass);
	        case COMPOSITION:
	            return String.format(type + " Class %s is composed with class %s.", sourceClass, targetClass);
	        case EXTENSION:
	            return String.format(type + " Class %s extends class %s.", sourceClass, targetClass);
	        case IMPLEMENTATION:
	            return String.format(type + " Class %s implements interface %s.", sourceClass, targetClass);
	        case UTILISATION:
	            return String.format(type + " Class %s uses class %s.", sourceClass, targetClass);
	        case ASSOCIATION:
	            return String.format(type + " Class %s is in association with class %s.", sourceClass, targetClass);

	        default:
	            return null; 
	    }
	}
    public void setDescription(String description) {
		this.description = description;
	}

    public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public void setSourceClass(String sourceClass) {
		this.sourceClass = sourceClass;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
}
