package com.example.bheemesh.myapplication.Utils;

import android.util.Log;

/****
 * Custom class for logging in debug build
 * 
 * @author Jayananda.sagar
 * 
 */
public class Logger {

	private static boolean mIsLoggerEnabled = true;

	public static void d(String aTag, String aMessage) {
		if (mIsLoggerEnabled) {
			Log.d(aTag, aMessage);
		}
	}

	public static void w(String aTag, String aMessage) {
		if (mIsLoggerEnabled) {
			Log.w(aTag, aMessage);
		}
	}

	public static void e(String aTag, String aMessage) {
		if (mIsLoggerEnabled) {
			Log.e(aTag, aMessage);
		}
	}

	public static void i(String aTag, String aMessage) {
		if (mIsLoggerEnabled) {
			Log.i(aTag, aMessage);
		}
	}

	public static void e(String aTag, String aMessage, Exception e) {
		if (mIsLoggerEnabled) {
			Log.e(aTag, aMessage, e);
		}
	}

	public static void w(String aTag, String aMessage, Exception e) {
		if (mIsLoggerEnabled) {
			Log.w(aTag, aMessage, e);
		}
	}

	public static void v(String aTag, String aMessage) {
		if (mIsLoggerEnabled) {
			Log.v(aTag, aMessage);
		}
	}
}
