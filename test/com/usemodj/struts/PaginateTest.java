package com.usemodj.struts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PaginateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testReplaceFirst(){
		String str = "topic?topicId=1&&page=1&";
		System.out.println(str.replaceFirst("&+$", "").replaceAll("&{2,}", "&"));
	}
}
