package org.mql.java.models;

import java.util.List;

public class ConstructorInfo {
    private String constructorName;
    private String constructorModifier;
    private List<String> parameters;

    public ConstructorInfo(String constructorName,String constructorModifier,List<String> parameters) {
        this.constructorName = constructorName;
        this.constructorModifier = constructorModifier;
        this.parameters = parameters;
    }

    public String getconstructorName() {
        return constructorName;
    }

    public List<String> getParameters() {
        return parameters;
    }

	public String getconstructorModifier() {
		return constructorModifier;
	}

	public void setconstructorModifier(String constructorModifier) {
		this.constructorModifier = constructorModifier;
	}
}
