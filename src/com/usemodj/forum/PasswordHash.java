package com.usemodj.forum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.crypt.Crypt;
import com.usemodj.forum.service.MetaService;

public class PasswordHash {
	private static Logger logger = Logger.getLogger( PasswordHash.class );
	public static final int CRYPT_BLOWFISH = 1;
	public static final int CRYPT_EXT_DES = 1;
	
	String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	boolean portableHashes;
	private String randomState;

	private String  rndValue;
	private int iterationCountLog2;
	
	public PasswordHash( int iterationCountLog2, boolean portableHashes) {
		//this.itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		if( iterationCountLog2 < 4 || iterationCountLog2 > 31)
			iterationCountLog2 = 8;
		this.iterationCountLog2 = iterationCountLog2;
		
		this.portableHashes = portableHashes;
		//$this->random_state = microtime() . (function_exists('getmypid') ? getmypid() : '') . uniqid(rand(), TRUE);
		this.randomState = String.valueOf(new Date().getTime()) + UUID.randomUUID();
	}

//	public PasswordHash() {
//		// TODO Auto-generated constructor stub
//	}

	public static boolean checkPassword(String userPass, String hashPass,
			long userId) {
		// list($hash, $broken) = array_pad( explode( '---', $hash ), 2, '' );
		PasswordHash hasher = new PasswordHash(8, true);
		boolean check = hasher.checkPassword(userPass, hashPass);
		return check;
	}

	private boolean checkPassword(String userPass, String hashPass) {
		String hash = cryptPrivate( userPass, hashPass);
		logger.debug("-- checkPassword cryptPrivate=" + hash);
		
		if( "*".equals( hash.charAt(0))){
			//hash =  UnixCrypt.crypt(hashPass,  userPass);
			//hash = JCrypt.crypt(hashPass,  userPass);
			hash = Crypt.crypt(hashPass,  userPass);
		}
		logger.debug("-- checkPassword Crypt=" + hash);
		logger.debug("--                         hashPass=" + hashPass);
		
		return hash.equals( hashPass);
	}

	private String cryptPrivate(String userPass, String hashPass) {
		String output = "*0";
		if(  output.equals( hashPass.substring(0, 2)))
			output = "*1";
		
		if( ! "$P$".equals( hashPass.substring(0, 3)))
			return output;
		
		int countLog2 = this.itoa64.indexOf( hashPass.charAt(3));
		logger.debug("--- cryptPrivate() countLog2="+ countLog2);
		if( countLog2 < 7 || countLog2 > 30)
			return output;
		
		int count = 1 << countLog2;
		String salt = hashPass.substring( 4,  12);
		logger.debug("-- salt: "+ salt);
		
		if( salt.length() != 8)
			return output;
		
		byte[] hash = DigestUtils.md5( salt + userPass);
		//String hash =DigestUtils.md5Hex(salt + userPass);
		//logger.debug("-- md5Hex("+ salt +userPass+ "): "+  Hex.encodeHexString(hash));
		//logger.debug("-- md5( salt +userPass):encode64: "+this.encode64( hash, 16));
		do {
			hash = DigestUtils.md5(  StringUtils.newStringUtf8( hash) + userPass);
			//hash = DigestUtils.md5(  StringUtils.newStringUsAscii( hash) + userPass);
			//hash = DigestUtils.md5Hex(  hash + userPass);
			//logger.debug("-- md5( hash +userPass): "+ Hex.encodeHexString(hash));
			//logger.debug("-- md5( hash +userPass):encode64: "+this.encode64( hash, 16));
		} while( --count > 0);
		
		output = hashPass.substring( 0, 12);
		output += this.encode64( hash, 16);
		
		return output;
	}

	private String encode64(byte[] input, int count) {
		String output ="";
		int i = 0;
		do {
			int value = (int)input[i++]; //php  ord($input[$i++])
			output += this.itoa64.charAt( value & 0x3f);
			if( i < count)
				value |=((int)input[i]) << 8;
			output += this.itoa64.charAt((value >> 6) & 0x3f);
			if( i++ >= count)
				break;
			
			if( i < count)
				value |= ((int)input[i]) << 16;
			output += this.itoa64.charAt( (value >> 12) & 0x3f);
			if( i++ >= count)
				break;
			output += this.itoa64.charAt( (value >> 18) & 0x3f);
		} while( i < count);
		
		return output;
	}
	
	public int rand(SqlSession sqlSession, int min, int max){
		String seed = getTransient( sqlSession, "random_seed");
		
		//Reset rndValue after 14 uses
		// 32(md5) + 40(sha1) + 40(sha1)/8 = 14 random numbers from rndValue
		if( null == rndValue || rndValue.length() < 8) {
			rndValue = DigestUtils.md5Hex( UUID.randomUUID() + seed);
			rndValue += DigestUtils.shaHex( rndValue);
			rndValue += DigestUtils.shaHex( rndValue + seed);
			seed = DigestUtils.md5Hex(seed + rndValue	);
			setTransient(sqlSession, "random_seed", seed, 0);
		}
		
		//Take the first 8 digits for our value
		String value = rndValue.substring(0, 8);
		// Strip the first eight, leaving the remainder for the next call to wp_rand()
		rndValue = rndValue.substring( 8);
		long nValue = Math.abs( Long.parseLong( value,16));
		// Reduce the value to be within the min - max range
		// 4294967295 = 0xffffffff = max random number
		// Integer.MAX_VALUE= 2147483647
		if( max != 0)
			nValue = min +(long)((max - min +1) * ((double)nValue/(4294967295L+1)));
		logger.debug("--long nValue: " + nValue);
		
		return Math.abs(  (int)nValue);
	}

	private void setTransient(SqlSession sqlSession, String transients, String seed, long expiration) {
		transients = "bp_bbpress_"+ transients;
		String transientTimeout = "_transient_timeout_" + transients;
		transients = "_transient_" + transients;
		MetaService  ms = new MetaService();
		if( 0 != expiration) {
			long epoch = System.currentTimeMillis()/1000; //sec
			try {
				ms.updateMeta(sqlSession, 0, transientTimeout, String.valueOf(epoch + expiration), "bb_option");
			} catch (Exception e) {
				// e.printStackTrace();
				logger.error("-- PasswordHash.setTransient() Exception: " + e.getMessage());
			}
		}
		try {
			ms.updateMeta(sqlSession, 0, transients, seed, "bb_option"	);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("-- PasswordHash.setTransient() Exception: " + e.getMessage());
		}
	}

	private String getTransient(SqlSession sqlSession, String trans) {
		String trans2 = "bp_bbpress_"+ trans;
		String transientOption = "_transient_" + trans2;
		String transientTimeout = "_transient_timeout_" + trans2;
		MetaService  ms = new MetaService();
		String value = null;
		try {
			String timeout = ms.getBBOption(sqlSession, transientTimeout);
			logger.debug("-- transientTimeout: "+ timeout);
			if( null != timeout){
				try {
					long lTimeout = Long.parseLong( timeout);
					long epoch = System.currentTimeMillis()/1000; //sec
					if( lTimeout < epoch) {
						ms.deleteBBOption( sqlSession, transientOption);
						ms.deleteBBOption(sqlSession, transientTimeout);
						return  null;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			value = ms.getBBOption(sqlSession, transientOption);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PasswordHash.getTransient() Exception: "+ e.getMessage());
		}
		return value;
	}
	public  String generatePassword( SqlSession sqlSession, int length, boolean specialChars){
		//default  int length = 12, boolean specialChars = true
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		if( specialChars) 
			chars += "@#$%^&*()";
		
		String password = "";
		for( int i = 0; i < length; i++ ){
			int rand = rand(sqlSession, 0, chars.length() -1);
			password += chars.substring(rand, rand+1);
		}
		return password;
	}

	public String hashPassword(String userPass) {
		
		String random = "";
		if( CRYPT_BLOWFISH == 1 && !this.portableHashes){
			random = this.getRandomBytes( 16);
			String hash = Crypt.crypt( this.gensaltBlowfish( random), userPass);
			if( hash.length() == 60)
				return hash;
		}
		
		if(CRYPT_EXT_DES == 1 && !this.portableHashes){
			if( random.length() < 3)
				random = this.getRandomBytes( 3);
			String hash = Crypt.crypt( this.gensaltExtended( random), userPass);
			if( hash.length() == 20)
				return hash;
		}
		
		if( random.length() < 6)
			random = this.getRandomBytes( 6);
		String hash = this.cryptPrivate(userPass, this.gensaltPrivate( random));
		if( hash.length() == 34)
			return hash;
		
		/*# Returning '*' on error is safe here, but would _not_ be safe
		# in a crypt(3)-like function used _both_ for generating new
		# hashes and for validating passwords against existing hashes.
		*/
		return"*";
	}
	
	private String gensaltPrivate(String input) {
		String output = "$P$";
		output += this.itoa64.charAt(Math.min(this.iterationCountLog2+5, 30));
		output += this.encode64(input.getBytes(),  6);
		return output;
	}

	private String gensaltExtended(String input) {
		int countLog2 = Math.min( this.iterationCountLog2 + 8, 24);
		/*# This should be odd to not reveal weak DES keys, and the
		 # maximum valid value is (2**24 - 1) which is odd anyway. */
		int count = ( 1 << countLog2) -1;
		String output = "_";
		output += this.itoa64.charAt( count & 0x3f);
		output += this.itoa64.charAt( (count >> 6) & 0x3f);
		output += this.itoa64.charAt( (count >> 12) & 0x3f);
		output += this.itoa64.charAt( (count >> 18) & 0x3f);
		
		output += this.encode64(input.getBytes(), 3);
		return output;
	}

	private String gensaltBlowfish(String input) {
		/*# This one needs to use a different order of characters and a
		# different encoding scheme from the one in encode64() above.
		# We care because the last character in our encoded string will
		# only represent 2 bits.  While two known implementations of
		# bcrypt will happily accept and correct a salt string which
		# has the 4 unused bits set to non-zero, we do not want to take
		# chances and we also do not want to waste an additional byte
		# of entropy. */
		String itoa64 ="./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		String output = "$2a$";
		//$output .= chr(ord('0') + $this->iteration_count_log2 / 10);
		output += String.valueOf((char)((int)'0' + iterationCountLog2/10));
		//$output .= chr(ord('0') + $this->iteration_count_log2 % 10);
		output += String.valueOf((char)((int)'0' + iterationCountLog2%10));
		output += '$';

		int i = 0;
		do {
			int c1 = (int)input.charAt(i++);
			output += itoa64.charAt(c1 >> 2);
			c1 = (c1 & 0x03) << 4;
			if( i >= 16){
				output += itoa64.charAt(c1);
				break;
			}
			int c2 = (int)input.charAt(i++);
			c1 |= c2 >> 4;
			output += itoa64.charAt(c1);
			c1 = (c2 & 0x0f) << 2;
			
			c2 = (int)input.charAt(i++);
			c1 |= c2 >> 6;
			output += itoa64.charAt(c1);
			output += itoa64.charAt( c2 & 0x3f);
		} while( true);
		
		return output; 
	}

	public String getRandomBytes( int count) {
		String output = "";
		BufferedReader reader = null;
		char[] cbuf = new char[count];
		int length =0;
		try {
			reader = new BufferedReader(new FileReader("/dev/urandom"));
			length = reader.read(cbuf, 0, count);
			output =  String.valueOf( cbuf);
			
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {}
		}
		
		if( length < count){
			output = "";
			for( int i = 0; i < count; i += 16){
				this.randomState = DigestUtils.md5Hex( new Date().getTime() + this.randomState);
				//$output .=  pack('H*', md5($this->random_state));
				output +=  DigestUtils.md5Hex( this.randomState);
			}
			output = output.substring(0, count);
		}
		return output;
	}
}
