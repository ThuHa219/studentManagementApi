package fpt.training.studentManagementRest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean validatePassword(String password) {
		String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		Pattern p = Pattern.compile(pattern);
		if (password == null) {
			return false;
		}
		Matcher match = p.matcher(password);

		return match.matches();
	}
}
