package com.usemodj.forum;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;

public class PasswordHashTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCheckPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//fail("Not yet implemented");
		String userPass = "jinny3208";
		String hashPass = "$P$B4NyoGmPPc708xhxSaDiy7DR98R9GN.";
		long userId = 2;
		boolean check = PasswordHash.checkPassword(userPass, hashPass, userId);
		System.out.println("-- PasswordHash checkPassword: "+ check);
		
		System.out.println("-- md5( salt + userPass): "+ md5( hashPass.substring(4, 12) + userPass, "UTF-8"));
	}
	
	public static String md5(String input, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    String result = input;
	    if(input != null) {
	        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
	        md.update(input.getBytes( charset));
	        BigInteger hash = new BigInteger(1, md.digest());
	        result = hash.toString(16);
	        while(result.length() < 32) {
	            result = "0" + result;
	        }
	    }
	    return result;
	}
	
	@Test
	public void testMD5() throws UnsupportedEncodingException{
		String userPass = "jinny3208";
		String hashPass = "$P$B4NyoGmPPc708xhxSaDiy7DR98R9GN.";
		String md5 = DigestUtils.md5Hex( hashPass.substring(4, 12)+ userPass);
		System.out.println("-- DigestUtils.md5Hex:"+ md5);
		
		 md5 = getHexCode( hashPass.substring(4, 12)+ userPass, "utf-8"); // "US-ASCII"
		System.out.println("-- getHexCode :"+ md5);
		
		System.out.println("--md5Hex(hello):"+ DigestUtils.md5Hex("hello"));
		System.out.println("--md5Hex(4NyoGmPPjinny3208):"+ DigestUtils.md5Hex("4NyoGmPPjinny3208"));
	}
	
	@Test
	public void testMD5getHexCode() throws UnsupportedEncodingException{
		String userPass = "jinny3208";
		String hashPass = "$P$B4NyoGmPPc708xhxSaDiy7DR98R9GN.";
		String md5 = getHexCode( hashPass.substring(4, 12)+ userPass, "utf-8");
		System.out.println("-- getHexCode :"+ md5);
	}

	public static String getHexCode(String inputValue, String charset) throws UnsupportedEncodingException {
	      MessageDigest md5 = null;
	       try {
	          md5 = MessageDigest.getInstance("MD5");
	         } catch(Exception e) {
	         e.printStackTrace();
	      }
	 
	      String eip; 
	      byte[] bip;
	      String temp = "";
	      String tst = inputValue;
	              
	      bip = md5.digest(tst.getBytes( charset));
	      for(int i=0; i < bip.length; i++) {
	          eip = "" + Integer.toHexString((int)bip[i] & 0x000000ff);
	          //System.out.println(i + " : " + eid);
	          if(eip.length() < 2) eip = "0" + eip;
	          temp = temp + eip;
	      }
	      return temp;
	    }
	
	@Test
	public void testStringToInt(){
		//System.out.println(NumberUtils.toInt("a"));
		//System.out.println(NumberUtils.createInteger("0xaa"));
		System.out.println("  Integer.MAX_VALUE:"+ Integer.MAX_VALUE + ", Long.MAX_VALUE:"+Long.MAX_VALUE );
		System.out.println( Long.parseLong("ffffffff",16));
	}
	
	@Test
	public void testPhpPack() throws UnsupportedEncodingException{
		String pass = "1234";
		//php pack("H*", "1234");
		//DigestUtils.
		byte[] base64 = Base64.encodeBase64( pass.getBytes( "UTF-8"));
		System.out.println("-- base64:"+ new  String( base64, "UTF-8"));
		//new String()
	}
	
	@Test
	public void testParseInt16(){
		String num = "8d1aa27b";
		try {
			System.out.println("---parse int 16: "+ Integer.parseInt(num, 16));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("---parse Long16: "+ Long.parseLong(num, 16));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMaxValue(){
		System.out.println("-- Integer.MAX_VALUE: "+ Integer.MAX_VALUE + " hex: "+ Integer.toHexString( Integer.MAX_VALUE));
		
		System.out.println("-- Long.MAX_VALUE: "+ Long.MAX_VALUE + " hex: "+ Long.toHexString( Long.MAX_VALUE));
		
	}
	
	@Test
	public void testCharOrd(){
		int iterationCountLog2 = 8;
		System.out.println("((int)'0' + iterationCountLog2/10) ="+ ((int)'0' + iterationCountLog2/10));
		System .out.println("(int)'0' ="+(int)'0' );
		String output =String.valueOf((char)((int)'0' + iterationCountLog2/10));
		System.out.println("-- output: "+ output);
		
		 output =String.valueOf((char)((int)'0' + iterationCountLog2%10));
		System.out.println("-- output: "+ output);
	}
}
