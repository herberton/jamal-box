package br.com.cruz.jamal.common.helper;


public final class StringHelper extends JamalHelper {

	private static final long serialVersionUID = 3895057146334873623L;

	public static final boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
}
