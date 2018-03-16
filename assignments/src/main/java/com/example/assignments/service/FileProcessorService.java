package com.example.assignments.service;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.assignments.model.EmployeeCouple;
import com.example.assignments.model.ProjectAssignment;
import com.example.assignments.model.Result;

public class FileProcessorService {

	private static final String NULL_DATE = "NULL";

	List<Result> processFiles(File file) {

		try {
			List<ProjectAssignment> assignments = readFile(file);
			Map<EmployeeCouple, Long> couples = getCouples(assignments);

			List<Result> results = couples.entrySet()
					.stream()
					.map( mapper())
					.collect(Collectors.toList());
			Collections.sort(results);
			return results;
		} catch (IOException e) {
			return null;
		}

	}


	private Function<? super Entry<EmployeeCouple, Long>, ? extends Result> mapper() {
		return entry -> new Result(entry.getKey().getFirstEmployeeID(),
				entry.getKey().getSecondEmployeeID(),
				entry.getKey().getProjectIDs().toString(),
				entry.getValue());
	}
	

	private List<ProjectAssignment> readFile(File file) throws IOException {
		try (Stream<String> lines = Files.lines(file.toPath())) {
			return lines.map(s -> parse(s)).collect(Collectors.toList());
		}
	}

	private ProjectAssignment parse(String line) {
		String[] fields = line.split(",");

		int employeeID = Integer.parseInt(fields[0].trim());
		int projectID = Integer.parseInt(fields[1].trim());

		// TODO think about more patterns

		LocalDate startDate = LocalDate.parse(fields[2].trim());
		LocalDate endDate;

		String endDateString = fields[3].trim();

		if (endDateString.equals(NULL_DATE)) {
			endDate = LocalDate.now();
		} else {
			endDate = LocalDate.parse(endDateString);
		}

		return new ProjectAssignment(employeeID, projectID, startDate, endDate);
	}

	private Map<EmployeeCouple, Long> getCouples(List<ProjectAssignment> assignments) {
		Map<Integer, List<ProjectAssignment>> assignmentsProjects = new HashMap<>();

		// Group assignments by project
		for (ProjectAssignment assignment : assignments) {
			int projectID = assignment.getProjectID();
			if (assignmentsProjects.containsKey(projectID)) {
				List<ProjectAssignment> assignmentsInSameProject = assignmentsProjects.get(projectID);
				assignmentsInSameProject.add(assignment);
			} else {
				List<ProjectAssignment> assignmentsInSameProject = new LinkedList<>();
				assignmentsInSameProject.add(assignment);
				assignmentsProjects.put(projectID, assignmentsInSameProject);
			}
		}
		
		HashMap<EmployeeCouple, Long> mergedMap = new HashMap<>();

		for (List<ProjectAssignment> assignemnts : assignmentsProjects.values()) {
			mergedMap.putAll(processSingleProject(assignemnts));
		}
		

		return mergedMap;

	}

	private HashMap<EmployeeCouple, Long> processSingleProject(List<ProjectAssignment> assignmentsInSingleProject) {
		HashMap<EmployeeCouple, Long> map = new HashMap<>();
		
		for (int i = 0; i < assignmentsInSingleProject.size(); i++) {
			for (int l = i + 1; l < assignmentsInSingleProject.size(); l++) {
				ProjectAssignment first = assignmentsInSingleProject.get(i);
				ProjectAssignment second = assignmentsInSingleProject.get(l);

				long daysWorked = getWorkTogetherPeriod(first, second);

				if (daysWorked > 0) {
					EmployeeCouple couple = new EmployeeCouple(first.getEmployeeID(), second.getEmployeeID());
					couple.addProjectID(first.getProjectID());

					if (map.containsKey(couple)) {
						long lastResult = map.get(couple);
						lastResult += daysWorked;
						map.remove(couple);
						map.put(couple, lastResult);

					} else {
						map.put(couple, daysWorked);
					}
				}

			}
		}
		
		return map;
	}

	private long getWorkTogetherPeriod(ProjectAssignment first, ProjectAssignment second) {

		if (first.getStartDate().isBefore(second.getStartDate()) && first.getEndDate().isAfter(second.getEndDate())) {
			return ChronoUnit.DAYS.between(second.getStartDate(), second.getEndDate());
		}

		if (second.getStartDate().isBefore(first.getStartDate()) && second.getEndDate().isAfter(first.getEndDate())) {
			return ChronoUnit.DAYS.between(first.getStartDate(), first.getEndDate());
		}

		if (first.getStartDate().isBefore(second.getStartDate()) && first.getEndDate().isBefore(second.getEndDate())
				&& first.getEndDate().isAfter(second.getStartDate())) {
			return ChronoUnit.DAYS.between(second.getStartDate(), first.getEndDate());
		}

		if (second.getStartDate().isBefore(first.getStartDate()) && second.getEndDate().isBefore(first.getEndDate())
				&& second.getEndDate().isAfter(first.getStartDate())) {
			return ChronoUnit.DAYS.between(first.getStartDate(), second.getEndDate());
		}

		return -1;
	}

}
