package com.usemodj.crypt;

import static org.junit.Assert.*;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

public class CryptTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCrypt() {
		//fail("Not yet implemented");
		String userPass = "jinny3208";
		String salt = "$P$B4NyoGmPPc708xhxSaDiy7DR98R9GN."; //stored hash
		StopWatch clock = new StopWatch( );
		salt = salt.substring(4, 12);
		
		clock.start( );
		System.out.println( "Crypt=           ["+ Crypt.crypt(salt, userPass) + "]");
		clock.stop( );
		System.out.println( "It takes " + clock.getTime( ) + " milliseconds" );
		
		clock.reset();
		clock.start();
		System.out.println( "Crypt2=         ["+Crypt2.crypt(salt, userPass) + "]");
		clock.stop( );
		System.out.println( "==>It takes " + clock.getTime( ) + " milliseconds" );
	
		clock.reset();
		clock.start();
		System.out.println( "JCrypt=         ["+JCrypt.crypt(salt, userPass) + "]");
		clock.stop( );
		System.out.println( "==>It takes " + clock.getTime( ) + " milliseconds" );
		
		clock.reset();
		clock.start();
		System.out.println( "UnixCrypt=  ["+UnixCrypt.crypt(salt, userPass) + "]");
		clock.stop( );
		System.out.println( "==> It takes " + clock.getTime( ) + " milliseconds" );
		
		clock.reset();
		clock.start();
		System.out.println( "UnixCrypt2=["+UnixCrypt2.crypt(userPass, salt) + "]");
		clock.stop( );
		System.out.println( "==> It takes " + clock.getTime( ) + " milliseconds" );

		clock.reset();
		clock.start();
		System.out.println( "Password.crypt=["+Password.crypt(userPass, salt) + "]");
		clock.stop( );
		System.out.println( "==> It takes " + clock.getTime( ) + " milliseconds" );

	}

	@Test
	public void testJCrypt() {
		//fail("Not yet implemented");
		String userPass = "jinny3208";
		String salt = "$P$B4NyoGmPPc708xhxSaDiy7DR98R9GN."; //stored hash
		System.out.println( "JCrypt=["+JCrypt.crypt(salt, userPass) + "]");
	}

}
