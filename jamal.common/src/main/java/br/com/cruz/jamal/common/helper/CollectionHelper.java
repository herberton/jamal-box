package br.com.cruz.jamal.common.helper;

import java.util.Collection;

public final class CollectionHelper extends JamalHelper {

	private static final long serialVersionUID = 5564514635549011361L;

	public static final <T> boolean isNullOrEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}
	
	public static final <T> boolean isNullOrEmpty(T[] array) {
		return array == null || array.length <= 0;
	}
}
