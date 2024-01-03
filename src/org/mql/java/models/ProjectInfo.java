package org.mql.java.models;

import java.util.List;

public class ProjectInfo {

	private String projectName;
	private List<PackageInfo> packages;
	
	public ProjectInfo(String projectName, List<PackageInfo> packages) {
        this.setProjectName(projectName);
        this.setPackages(packages);
	}

	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public List<PackageInfo> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageInfo> packages) {
		this.packages = packages;
	}
	

}
