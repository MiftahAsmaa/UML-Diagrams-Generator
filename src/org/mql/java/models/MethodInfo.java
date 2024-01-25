package org.mql.java.models;

import java.util.List;

public class MethodInfo {
    private String methodName;
    private String methodModifier;
    private String returnType;
    private List<String> parameters;

    public MethodInfo(String methodName,String methodModifier, String returnType, List<String> parameters) {
        this.methodName = methodName;
        this.methodModifier = methodModifier;
        this.returnType = returnType;
        this.parameters = parameters;
    }

	public MethodInfo(String methodName,String methodModifier, String returnType) {
		this.methodName = methodName;
        this.methodModifier = methodModifier;
        this.returnType = returnType;
        }

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodModifier() {
		return methodModifier;
	}

	public void setMethodModifier(String methodModifier) {
		this.methodModifier = methodModifier;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}


	
}
