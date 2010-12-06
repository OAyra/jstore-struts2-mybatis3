package com.usemodj.struts;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

public class SHA1UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSHA1() {
		//fail("Not yet implemented");
		try {
			System.out.println(SHA1Utils.SHA1("한글문자"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHashCode(){
		String hangul = "한글문자";
		System.out.println(" hashCode:"+ hangul.hashCode());
		System.out.println(" hashCode:"+ (long)hangul.hashCode());
	}

}
