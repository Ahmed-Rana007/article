package com.hbl.selfservice;
import java.security.MessageDigest;    
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class MD5Encyption {

	 
		  /**
		   * Cipher for encode
		   */
		  private final MessageDigest md;

		  public MD5Encyption() throws SecurityException {
		    try {
		      md = MessageDigest.getInstance("MD5", "SUN");
		    }catch(Exception se) {
		      throw new SecurityException("In MD5 constructor " + se);
		    }
		  }

		  public String encode(String in) throws Exception {
		    if (in == null) {
		      return null;
		    }
		    try {
		      byte[] raw = null;
		      byte[] stringBytes = null;
		      stringBytes = in.getBytes("UTF8");
		      synchronized(md) {
		        raw = md.digest(stringBytes);
		      }
		      BASE64Encoder encoder = new BASE64Encoder();
		      return encoder.encode(raw);
		    } catch (Exception se) {
		      throw new Exception("Exception while encoding " + se);
		    }

		  }
		 
		  

		  /**
		   * Test harness
		   * @param args
		   */
		  public static void main(String[] args) {
		     String clearText = "Test@12345";
		     String encryptedText = "XEKNiHXSlIYH8+P+E01xtA==";
		    try {
		    	MD5Encyption app = new MD5Encyption();
		      String encryptedHash = app.encode(clearText);
		      System.out.println(encryptedHash);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

		  }
		}