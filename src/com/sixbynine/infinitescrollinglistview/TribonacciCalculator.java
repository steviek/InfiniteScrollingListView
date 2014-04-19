package com.sixbynine.infinitescrollinglistview;


public class TribonacciCalculator {

	public static String[] getTribonacci(){
		long[] tribonacciValues = new long[70];
		String[] results = new String[70];
		tribonacciValues[0] = 1;
		tribonacciValues[1] = 1;
		tribonacciValues[2] = 2;
		results[0] = "1";
		results[1] = "1";
		results[2] = "2";
		
		for(int i = 3; i < 70; i ++){
			tribonacciValues[i] = tribonacciValues[i-3] + tribonacciValues[i-2] + tribonacciValues[i-1];
			results[i] = String.valueOf(tribonacciValues[i]);
		}
		
		return results;
	}
}
