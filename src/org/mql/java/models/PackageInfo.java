package org.mql.java.models;

import java.util.List;

public class PackageInfo {
    private String packageName;
    private List<ClassInfo> classes;

    public PackageInfo(String packageName, List<ClassInfo> classes) {
        this.packageName = packageName;
        this.classes = classes;
    }

	public String getpackageName() {
		return packageName;
	}

	public void setpackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<ClassInfo> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassInfo> classes) {
		this.classes = classes;
	}

	

}