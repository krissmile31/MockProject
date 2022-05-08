package com.krissmile31.mockproject.utils;
import android.util.Log;

import com.krissmile31.mockproject.BuildConfig;

public class LogUtils {

    public static void d(String message) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        if (BuildConfig.DEBUG) {
            Log.d("class " + stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName()
                    + " at line " + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void e(String message) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        if (BuildConfig.DEBUG) {
            Log.e("class " + stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName()
                    + " at line " + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void i(String message) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        if (BuildConfig.DEBUG) {
            Log.i("class " + stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName()
                    + " at line " + stackTraceElement.getLineNumber(), message);
        }

    }

}