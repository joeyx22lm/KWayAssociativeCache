package com.joeyorlando;

public class Log {
	
	private static int DEBUG_LEVEL = 0;
	
	private static void print(String msg){
		System.out.println(msg);
	}
	
	private static String getTimestamp(){
		return (System.currentTimeMillis() / 1000L) + "";
	}
	public static void error(Throwable e){
		e.printStackTrace();
		error(e.getMessage());
	}
	
	public static void error(String msg, int exitCode){
		print(getTimestamp() + " ERROR: " + msg);
		if(exitCode != 0) System.exit(exitCode);
	}
	
	public static void error(String msg){
		error(msg, 0);
	}
	
	public static void info(String msg){
		print(getTimestamp() + " INFO: " + msg);
	}
	
	public static void debug(String msg){
		if(DEBUG_LEVEL > 0) print(getTimestamp() + " DEBUG: " + msg);
	}
}
