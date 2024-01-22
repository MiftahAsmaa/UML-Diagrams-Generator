package org.mql.java.models;

public class FieldInfo {
    private String fieldName;
    private String fieldModifier;
    private String fieldType;

    public FieldInfo(String fieldName, String fieldModifier, String fieldType) {
        this.fieldName = fieldName;
        this.fieldModifier = fieldModifier;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

	public String getFieldModifier() {
		return fieldModifier;
	}

	public void setFieldModifier(String fieldModifier) {
		this.fieldModifier = fieldModifier;
	}
}
