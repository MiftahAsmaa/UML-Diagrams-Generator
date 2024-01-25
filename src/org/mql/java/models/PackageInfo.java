package org.mql.java.models;

import java.util.List;

public class PackageInfo {
    private String packageName;
    private List<ClassInfo> classes;
    private List<PackageInfo> subPackages;

    public PackageInfo(String packageName, List<ClassInfo> classes, List<PackageInfo> subPackages) {
        this.setPackageName(packageName);
        this.classes = classes;
        this.setSubPackages(subPackages);
    }
   
	public PackageInfo(String packageName, List<ClassInfo> classes) {
        this.setPackageName(packageName);
        this.classes = classes;
    }

    public PackageInfo(String packageName) {
        this.setPackageName(packageName);
	}
	public String getPackageName() {
    	return packageName;
    }
    public void setPackageName(String packageName) {
    	this.packageName = packageName;
    }
	
	public List<ClassInfo> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassInfo> classes) {
		this.classes = classes;
	}
	public List<PackageInfo> getSubPackages() {
		return subPackages;
	}
	public void setSubPackages(List<PackageInfo> subPackages) {
		this.subPackages = subPackages;
	}	

}