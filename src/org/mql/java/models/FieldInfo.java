package org.mql.java.models;

public class FieldInfo {
    private String fieldName;
    private String fieldType;

    public FieldInfo(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
}
