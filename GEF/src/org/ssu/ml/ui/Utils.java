package org.ssu.ml.ui;

import javax.servlet.http.HttpServletRequest;



public class Utils {
	
	public static int requestIntParameter(HttpServletRequest request, String name, int initVal)
	{
		if(request.getParameter(name) == null)
			return initVal;
		else
			return Integer.parseInt(request.getParameter(name));
	}
	
	public static String requestStringParameter(HttpServletRequest request, String name, String initVal)
	{
		if(request.getParameter(name) == null)
			return initVal;
		else
			return (String)request.getParameter(name);
	}
	
	public static int maxIndex(float[] args)
	{
		float maxValue = -1000000;
		int maxCount = -1;
		Class type;
		
		for(int count = 0 ; count < args.length ; count++)
		{
			if(maxValue < args[count])
			{
				maxValue = args[count];
				maxCount = count;
			}
		}
		return maxCount;
	}
	
	public static int minIndex(float[] args)
	{
		float minValue = 1000000;
		int minCount = -1;
		Class type;
		
		for(int count = 0 ; count < args.length ; count++)
		{
			if(minValue > args[count])
			{
				minValue = args[count];
				minCount = count;
			}
		}
		return minCount;
	}
	
	public static float minValue(float[] args)
	{
		return args[minIndex(args)];
	}
	
	public static float maxValue(float[] args)
	{
		return args[maxIndex(args)];
	}
	
	public static void main(String[] argv)
	{
		Object a = (float)0.01;
		if(a instanceof Float)
		{
			System.out.println("??");
		}
	}
	
}
