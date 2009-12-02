package org.ssu.ml.base;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Vector;

public class CGridData {
	
	int gSpacing;
	int gWidth, gHeight;
	HashMap<Point, Vector<String>> gNodeBean;
	String delimeter = "\t";
	
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
	
	public String generateData()
	{
		StringBuffer result = new StringBuffer();
		Vector<Double> vector = new Vector<Double>();
		for(int count = 0 ; count < gWidth ; count++)
		{
			for(int count1 = 0 ; count1 < gHeight ; count1++)
			{
				Vector<String> curList = gNodeBean.get(new Point(count, count1));
				if(curList.size() > 0)
				{
					result.append("Grid "+count+","+count1+"\n");
					for(int nCount = 0 ; nCount < curList.size() ; nCount++)
						result.append(curList.get(nCount)+"\n");
				}
			}
		}
		return result.toString();
	}
	
	public String generateDataSquare()
	{
		StringBuffer result = new StringBuffer();
		boolean isHaveData = true;
		String cycleResult = "";
		for(int rowCnt = 0 ; isHaveData ; rowCnt++){
			isHaveData = false;
			cycleResult = Integer.toString(rowCnt);
			for(int count = 0 ; count < gWidth ; count++)
			{
				for(int count1 = 0 ; count1 < gHeight ; count1++)
				{
					Vector<String> curList = gNodeBean.get(new Point(count, count1));
					if(curList.size() == 0) continue;
					if(rowCnt == 0){
						isHaveData = true;
						if(cycleResult.equals(""))
							cycleResult = delimeter+"Grid_"+count+"_"+count1;
						else
							cycleResult += delimeter+"Grid_"+count+"_"+count1;
					}else{
						
						if(curList.size() > rowCnt-1)
						{
							isHaveData = true;
							cycleResult += delimeter+curList.get(rowCnt-1);
						}else{
							cycleResult += delimeter;
						}
					}
				}
			}
			if(isHaveData)
				result.append(cycleResult+"\n");
		}
		return result.toString();
	}
	
	public String generateDataSquareTrans()
	{
		StringBuffer result = new StringBuffer();
		Vector<Double> vector = new Vector<Double>();
		for(int count = 0 ; count < gWidth ; count++)
		{
			for(int count1 = 0 ; count1 < gHeight ; count1++)
			{
				Vector<String> curList = gNodeBean.get(new Point(count, count1));
				if(curList.size() > 0)
				{
					result.append("Grid "+count+","+count1+"\n");
					for(int nCount = 0 ; nCount < curList.size() ; nCount++)
						result.append(curList.get(nCount)+"\n");
				}
			}
		}
		return result.toString();
	}
	
}
