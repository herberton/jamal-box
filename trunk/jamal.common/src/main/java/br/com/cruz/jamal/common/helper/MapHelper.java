package br.com.cruz.jamal.common.helper;

import java.util.Map;

public final class MapHelper extends JamalHelper {

	private static final long serialVersionUID = -2099387405154591254L;

	public static final <K, V> boolean isNullOrEmpty(Map<K, V> map) {
		return map == null || map.size() <= 0;
	}
}
