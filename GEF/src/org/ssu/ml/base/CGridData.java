package org.ssu.ml.base;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Vector;

public class CGridData {
	
	int gSpacing;
	int gWidth, gHeight;
	HashMap<Point, Vector<String>> gNodeBean;
	
	public CGridData(int width, int height, int spacing)
	{
		gSpacing = spacing;
		gWidth = (int)Math.ceil((double)width/spacing)+1;
		gHeight = (int)Math.ceil((double)height/spacing)+1;
		gNodeBean = new HashMap<Point, Vector<String>>();
		for(int count = 0 ; count < gWidth ; count++)
			for(int count1 = 0 ; count1 < gHeight ; count1++)
				gNodeBean.put(new Point(count, count1), new Vector<String>());
	}
	
	public void addData(int width, int height, String name)
	{
		int w = (int)(width/gSpacing);
		int h = (int)(height/gSpacing);		
		gNodeBean.get(new Point(w, h)).add(name);
	}
	
	public StringBuffer generateData()
	{
		StringBuffer result = new StringBuffer();
		Vector<Double> vector = new Vector<Double>();
		for(int count = 0 ; count < gWidth ; count++)
		{
			for(int count1 = 0 ; count1 < gHeight ; count1++)
			{
				
			}
		}
		
		return result;
	}
	
}
