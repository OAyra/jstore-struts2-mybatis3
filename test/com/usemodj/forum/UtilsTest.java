package com.usemodj.forum;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSanitizeUser() {
		//fail("Not yet implemented");
		String username = "<h>ABCD  EFG %3F%ef &nbsp;&234f;한ㄱㄹ";
		System.out.println( username);
		System.out.println( Utils.sanitizeUser(username, true));
	}
	@Test
	public void testSendMail() throws IOException, MessagingException{
		String subject = "java mail test";
		String text = "java mail message body";
		String to = "usemodj@gmail.com";
		Utils.sendMail(  subject, text, to);
		
	}
}
