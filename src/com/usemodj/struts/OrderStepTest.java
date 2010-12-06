package com.usemodj.struts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OrderStepTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetFullName() {
		//fail("Not yet implemented");
		System.out.println(OrderStep.OD + ": " +OrderStep.OD.getFullName());
		System.out.println("OrderStep.name: "+ OrderStep.OD.name());
	}

	@Test
	public void testGetKoName() {
		//fail("Not yet implemented");
		System.out.println(OrderStep.OD + ": " +OrderStep.OD.getKoName());
	}

}
