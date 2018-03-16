package com.example.assignments.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.assignments.model.EmployeePair;
import com.example.assignments.model.ProjectAssignment;
import com.example.assignments.model.Result;

public class FileProcessorService {

	private static final String NULL_DATE = "NULL";
	private DateTimeFormatter patternColons = DateTimeFormatter.ofPattern("yyyy:MM:dd");
	private DateTimeFormatter patternSlash = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private DateTimeFormatter patternDot = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private DateTimeFormatter patternComma = DateTimeFormatter.ofPattern("yyyy,MM,dd");
	private DateTimeFormatter patternSpaces = DateTimeFormatter.ofPattern("yyyy MM dd");

	public List<Result> processFiles(File file) {

		try {
			List<ProjectAssignment> assignments = readFile(file);
			Map<EmployeePair, Long> couples = getCouples(assignments);

			List<Result> results = couples.entrySet().stream().map(mapper()).collect(Collectors.toList());
			Collections.sort(results);
			return results;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private Function<? super Entry<EmployeePair, Long>, ? extends Result> mapper() {
		return entry -> new Result(entry.getKey().getFirstEmployeeID(), entry.getKey().getSecondEmployeeID(),
				entry.getKey().getProjectIDs().toString(), entry.getValue());
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

		LocalDate startDate = parseDate(fields[2].trim());
		LocalDate endDate;

		String endDateString = fields[3].trim();

		if (endDateString.equals(NULL_DATE)) {
			endDate = LocalDate.now();
		} else {
			endDate = parseDate(endDateString);
		}

		return new ProjectAssignment(employeeID, projectID, startDate, endDate);
	}

	private LocalDate parseDate(String string) {
		LocalDate result = null;
		try {
			result = LocalDate.parse(string);
		} catch (DateTimeParseException e) {
			try {
				result = LocalDate.parse(string, patternColons);
			} catch (DateTimeParseException e1) {
				try {
					result = LocalDate.parse(string, patternComma);
				} catch (DateTimeParseException e2) {
					try {
						result = LocalDate.parse(string, patternDot);
					} catch (DateTimeParseException e3) {
						try {
							result = LocalDate.parse(string, patternSlash);
						} catch (DateTimeParseException e4) {
							result = LocalDate.parse(string, patternSpaces);
						}
					}
				}

			}
		}

		return result;
	}

	private Map<EmployeePair, Long> getCouples(List<ProjectAssignment> assignments) {
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

		HashMap<EmployeePair, Long> mergedMap = new HashMap<>();

		for (List<ProjectAssignment> assignemnts : assignmentsProjects.values()) {
			mergedMap.putAll(processSingleProject(assignemnts));
		}

		return mergedMap;

	}

	private HashMap<EmployeePair, Long> processSingleProject(List<ProjectAssignment> assignmentsInSingleProject) {
		HashMap<EmployeePair, Long> map = new HashMap<>();

		for (int i = 0; i < assignmentsInSingleProject.size(); i++) {
			for (int l = i + 1; l < assignmentsInSingleProject.size(); l++) {
				ProjectAssignment first = assignmentsInSingleProject.get(i);
				ProjectAssignment second = assignmentsInSingleProject.get(l);

				long daysWorked = getWorkTogetherPeriod(first, second);

				if (daysWorked > 0) {
					EmployeePair couple = new EmployeePair(first.getEmployeeID(), second.getEmployeeID());
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

		if (!first.getStartDate().isAfter(second.getStartDate()) && !first.getEndDate().isBefore(second.getEndDate())) {
			return ChronoUnit.DAYS.between(second.getStartDate(), second.getEndDate());
		}

		if (!second.getStartDate().isAfter(first.getStartDate()) && !second.getEndDate().isBefore(first.getEndDate())) {
			return ChronoUnit.DAYS.between(first.getStartDate(), first.getEndDate());
		}

		if (!first.getStartDate().isAfter(second.getStartDate()) && !first.getEndDate().isAfter(second.getEndDate())
				&& !first.getEndDate().isBefore(second.getStartDate())) {
			return ChronoUnit.DAYS.between(second.getStartDate(), first.getEndDate());
		}

		if (!second.getStartDate().isAfter(first.getStartDate()) && !second.getEndDate().isAfter(first.getEndDate())
				&& !second.getEndDate().isBefore(first.getStartDate())) {
			return ChronoUnit.DAYS.between(first.getStartDate(), second.getEndDate());
		}

		return -1;
	}

}
