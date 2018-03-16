package com.example.assignments.model;

public class Result implements Comparable<Result>{
	
	private int firstEmployeeID;
	private int secondEmployeeID;
	private String projects;
	private long period;
	
	public Result(int firstEmployeeID, int secondEmployeeID, String projects, long period) {
		super();
		this.firstEmployeeID = firstEmployeeID;
		this.secondEmployeeID = secondEmployeeID;
		this.projects = projects;
		this.period = period; 
	}


	public int getFirstEmployeeID() {
		return firstEmployeeID;
	}


	public int getSecondEmployeeID() {
		return secondEmployeeID;
	}


	public String getProjects() {
		return projects;
	}


	public long getPeriod() {
		return period;
	}


	@Override
	public int compareTo(Result o) {
		return  (int)o.period -(int)this.period;
	}
}
