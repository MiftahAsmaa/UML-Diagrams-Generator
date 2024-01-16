package org.mql.java.models;

import java.util.List;

public class MethodInfo {
    private String methodName;
    private String returnType;
    private List<String> parameters;

    public MethodInfo(String methodName, String returnType, List<String> parameters) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
