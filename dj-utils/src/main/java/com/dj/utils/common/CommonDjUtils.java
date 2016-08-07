package com.dj.utils.common;

import java.io.File;

/**
 * Created by Darshan on 8/7/16.
 */
public class CommonDjUtils {
	public static final String SEP = File.separator;
	public static final String NEWLINE = System.lineSeparator();

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
}
