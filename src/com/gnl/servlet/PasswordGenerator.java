package com.gnl.servlet;

import java.util.Random;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class PasswordGenerator {

	public PasswordGenerator() {
		// TODO Auto-generated constructor stub
	}

	public  String generateRandomPassword() {
		String num = "123456789";
		String specchar = "@#$";
		String capital = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
		String small = "abcdefghijklmnpqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(9);

		for (int j = 1; j < 3; j++) {
			sb.append(num.charAt(rnd.nextInt(num.length())));
		}
		sb.append(capital.charAt(rnd.nextInt(capital.length())));
		sb.append(specchar.charAt(rnd.nextInt(specchar.length())));
		for (int k = 1; k < 4; k++) {
			sb.append(small.charAt(rnd.nextInt(small.length())));
		}
		sb.append(num.charAt(rnd.nextInt(num.length())));
		String input = new String(sb);
		char n = capital.charAt(rnd.nextInt(capital.length()));
		String s2 = n + shuffleString(input);
		StringBuilder sb1 = new StringBuilder(s2);
		return sb1.toString();
	}

	public static String shuffleString(String input) {
		List<String> result = Arrays.asList(input.split(""));
		Collections.shuffle(result);

		return result.stream().collect(Collectors.joining());
	}

}
