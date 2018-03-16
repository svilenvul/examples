package com.example.assignments.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmployeePairTest {

	@Test
	public void testEquality() {
		EmployeePair first = new EmployeePair(1, 2);
		EmployeePair same = new EmployeePair(2, 1);

		assertTrue(first.equals(same));

		EmployeePair different = new EmployeePair(5, 1);

		assertFalse(first.equals(different));

		EmployeePair otherDifferent = new EmployeePair(2, 12);

		assertFalse(first.equals(otherDifferent));

		EmployeePair completelyDifferent = new EmployeePair(15, 12);

		assertFalse(first.equals(completelyDifferent));

	}
}
