package ua.nure.antonov.st4.support;

import java.sql.Date;
import java.util.Random;

public class NameGenerator {

	private static String[] beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru", "Ray", "Bre", "Zed", "Drak", "Mor", "Jag",
			"Mer", "Jar", "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro", "Mar", "Luk" };
	private static String[] middle = { "air", "ir", "mi", "sor", "mee", "clo", "red", "cra", "ark", "arc", "miri",
			"lori", "cres", "mur", "zer", "marac", "zoir", "slamar", "salmar", "urak" };
	private static String[] end = { "d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred",
			"mur" };
	private static String [] boo = { "true", "false"};
	
	private static String [] locale = { "en", "ru"};
	
	private static int [] role = { 0, 1};
	
	private static Random rand = new Random();

	private NameGenerator() {
	}

	public static int generateRandomTill(int limit) {
		return rand.nextInt(limit-1) + 1;
	}
	
	public static String generateName() {

		return beginning[rand.nextInt(beginning.length)] + middle[rand.nextInt(middle.length)]
				+ end[rand.nextInt(end.length)];
	}

	public static String generatePassword () {
		return end[rand.nextInt(end.length)] + middle[rand.nextInt(middle.length)]
				+ beginning[rand.nextInt(beginning.length)] + rand.nextInt(9999);
	}

	public static int generateBallance () {
		return rand.nextInt(999);
	}

	public static Boolean generateStatus () {
		return Boolean.valueOf(boo[rand.nextInt(boo.length)]);
	}
	
	public static String generateLocale () {
		return locale[rand.nextInt(locale.length)];
	}
	
	public static int generateRole () {
		return role[rand.nextInt(role.length)];
	}
	
	@SuppressWarnings("deprecation")
	public static Date generateDate () {
		return new Date((rand.nextInt(50)+69), rand.nextInt(30), rand.nextInt(12));
	}
}