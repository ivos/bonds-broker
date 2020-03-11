package com.bonds4all.utils;

import java.net.URI;

public class RestUtils {

	public static URI location(Long id) {
		return URI.create(String.valueOf(id));
	}
}
