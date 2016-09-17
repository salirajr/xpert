/**
 * Modification History
 * Date    Time                         Modified By             Comments
 *************************************************************************************
 * 
 * eai-xmb
 *
 * Mar 25, 2015 4:03:18 PM              Abdul Azis Nur          Creation
 * Mar 30, 2015 13:03:18 PM             Jovi Rengga Salira      Naming conversion, UIIDFactory - UIIDGenerator 
 *
 *
 *************************************************************************************
 */
package com.xybase.ax.eai.archcomp.xmb.common;

import java.net.*;
import java.rmi.server.*;
import java.security.*;

public class UUIDGenerator {

	private static String hostId;
	private static SecureRandom mySecureRand;

	static {
		try {
			UUIDGenerator.hostId = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException ex) {
			//ex.printStackTrace();
		}
		UUIDGenerator.mySecureRand = new SecureRandom();
	}

	public static String generate() {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			//ex.printStackTrace();
			return new UID().toString();
		}
		final StringBuffer sb = new StringBuffer(70);
		sb.append(UUIDGenerator.hostId);
		sb.append(':');
		sb.append(System.currentTimeMillis());
		sb.append(':');
		sb.append(UUIDGenerator.mySecureRand.nextLong());
		final byte[] array = md5.digest(sb.toString().getBytes());
		sb.setLength(0);
		for (int i = 0; i < array.length; ++i) {
			final int b = array[i] & 0xFF;
			if (b < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(b));
		}
		final String valueAfterMD5 = sb.toString().toUpperCase();
		sb.setLength(0);
		sb.append(valueAfterMD5);
		sb.insert(8, '-');
		sb.insert(13, '-');
		sb.insert(18, '-');
		sb.insert(23, '-');
		return sb.toString();
	}
	
}
