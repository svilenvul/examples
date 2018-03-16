package com.example.assignments.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EmployeePair {

	private int firstEmployeeID;
	private int secondEmployeeID;
	private Set<Integer> projectIDs;

	public EmployeePair(int firstEmployeeID, int secondEmployeeID) {
		super();
		this.firstEmployeeID = firstEmployeeID;
		this.secondEmployeeID = secondEmployeeID;
		this.projectIDs = new HashSet<>();
	}

	public int getFirstEmployeeID() {
		return firstEmployeeID;
	}

	public int getSecondEmployeeID() {
		return secondEmployeeID;
	}
	
	public Set<Integer> getProjectIDs() {
		return projectIDs;
	}
	
	public boolean addProjectID(int projectID) {
		return projectIDs.add(projectID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!EmployeePair.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final EmployeePair other = (EmployeePair) obj;

		if (this.firstEmployeeID != other.firstEmployeeID && 
				this.firstEmployeeID != other.secondEmployeeID) {
			return false;
		}

		if (this.secondEmployeeID != other.firstEmployeeID && 
				this.secondEmployeeID != other.secondEmployeeID) {
			return false;
		}
		return true;
	}
	

	@Override
    public int hashCode() {
        return Objects.hash(new Integer(firstEmployeeID),new Integer(secondEmployeeID));
    }
}
