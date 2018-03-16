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

		assertTrue(results.size() == 3);

		assertSingleResult(results, 0, 127,119 , "[12]", 91);
		assertSingleResult(results, 1, 143,127 , "[12]", 67);
		assertSingleResult(results, 2, 218,143 , "[10]", 31);
	}

	private void assertSingleResult(List<Result> results, int resultNumber, int emp1, int emp2, String projects,
			int days) {
		Result result = results.get(resultNumber);
		assertTrue(result.getFirstEmployeeID() == emp1);
		assertTrue(result.getSecondEmployeeID() == emp2);
		assertTrue(result.getProjects().equals(projects));
		assertTrue(result.getPeriod() == days);

	}

	private File readResource(String fileName) throws URISyntaxException {
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
}
