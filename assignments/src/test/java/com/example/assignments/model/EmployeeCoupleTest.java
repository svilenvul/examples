package com.example.assignments.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmployeeCoupleTest {

	@Test
	public void testEquality() {
		EmployeeCouple first = new EmployeeCouple(1, 2);
		EmployeeCouple same = new EmployeeCouple(2, 1);

		assertTrue(first.equals(same));

		EmployeeCouple different = new EmployeeCouple(5, 1);

		assertFalse(first.equals(different));

		EmployeeCouple otherDifferent = new EmployeeCouple(2, 12);

		assertFalse(first.equals(otherDifferent));

		EmployeeCouple completelyDifferent = new EmployeeCouple(15, 12);

		assertFalse(first.equals(completelyDifferent));

	}
}
