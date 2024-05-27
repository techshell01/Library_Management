package com.pocupload.pdfrecord.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

	public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(password.getBytes());

			// bytes to hex
			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
