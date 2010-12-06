package com.usemodj.struts;


import org.junit.Before;
import org.junit.Test;

public class StatusTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getStatusHash(){
		System.out.println(Status.getStatusHash());
	}
	@Test
	public void statusString(){
		System.out.println(Status.statusString());
	}
	@Test
	public void setStatus(){
		Status st = Status.valueOf("AT");
		System.out.println( st.toString() );
	}
}
