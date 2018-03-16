package com.example.assignments.model;

import java.time.LocalDate;

public class ProjectAssignment {
	
	private int employeeID;
	private int projectID;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public ProjectAssignment(int employeeID, int projectID, LocalDate startDate, LocalDate endDate) {
		this.employeeID = employeeID;
		this.projectID = projectID;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public int getProjectID() {
		return projectID;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	
	
}
