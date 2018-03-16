package com.example.assignments.service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.example.assignments.model.Result;

public class FileProcessorServiceTest {
	@Test
	public void simpleDataTest() throws URISyntaxException {
		FileProcessorService service = new FileProcessorService();
		File testFile = readResource("simpleData.txt");

		List<Result> results = service.processFiles(testFile);

		System.out.println(results.size());
		assertTrue(results.size() == 1);

		//assertSingleResult(results, 1, 143,127 , "[12]", 65);
		assertSingleResult(results, 0, 218,143 , "[10]", 30);
	}

	private void assertSingleResult(List<Result> results, int resultNumber, int emp1, int emp2, String projects,
			int days) {
		Result result = results.get(resultNumber);
		System.out.println(result.getFirstEmployeeID());
		assertTrue(result.getFirstEmployeeID() == emp1);
		System.out.println(result.getSecondEmployeeID());
		assertTrue(result.getSecondEmployeeID() == emp2);
		System.out.println(result.getProjects());
		assertTrue(result.getProjects().equals(projects));
		System.out.println(result.getPeriod());
		assertTrue(result.getPeriod() == days);

	}

	private File readResource(String fileName) throws URISyntaxException {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
}
