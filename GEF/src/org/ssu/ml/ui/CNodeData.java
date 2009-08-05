package org.ssu.ml.ui;

import java.awt.Color;
import java.util.Random;

public class CNodeData {
	float[] locxArry = null;
	float[] locyArry = null;
	String[] pointerName = null;
	int[] groups = null;
	
	
	int groupNum = 0;
	
	int pointCount = 0;
	int itemNum = 0;
	
	double pre_scale = 1;
	int padding = 10;
	
	public String toString(int index)
	{
		String result = "";
		
		if(index >= pointCount)
			result = "Index over the last index.";
		else
			result = pointerName[index]+" ["+locxArry[index]+", "+locyArry[index]+"]";
		return result;
	}
	
	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public double getPre_scale() {
		return pre_scale;
	}

	public void setPre_scale(double preScale) {
		pre_scale = preScale;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public void init(int pointCount)
	{
		locxArry = new float[pointCount];
    	locyArry = new float[pointCount];
    	pointerName = new String[pointCount];
    	groups = new int[pointCount];
	}
	
	public void init()
	{
		locxArry = new float[pointCount];
    	locyArry = new float[pointCount];
    	pointerName = new String[pointCount];
    	groups = new int[pointCount];
	}
	
	public int insertItem(String name, float locx, float locy)
	{
		locxArry[itemNum] = locx;
		locyArry[itemNum] = locy;
		pointerName[itemNum] = name;
		
		Random random = new Random();		//need more work.
		groups[itemNum] = random.nextInt(5);
		itemNum++;
		
		return itemNum;
	}
	
	public float[] getLocxArry() {
		return locxArry;
	}
	public void setLocxArry(float[] locxArry) {
		this.locxArry = locxArry;
	}
	public float[] getLocyArry() {
		return locyArry;
	}
	public void setLocyArry(float[] locyArry) {
		this.locyArry = locyArry;
	}
	public String[] getPointerNames() {
		return pointerName;
	}
	public void setPointerNames(String[] pointerName) {
		this.pointerName = pointerName;
	}
	
	public String getPointerName(int index){
		return pointerName[index];
	}
	
	public void setPointerName(int index, String name){
		this.pointerName[index] = name;
	}
	
	public int getPointCount() {
		return pointCount;
	}
	public void setPointCount(int pointCount) {
		this.pointCount = pointCount;
	}
	
	public void setGroup(int index, int group)
	{
		this.groups[index] = group;
	}
	
	public int getGroup(int index)
	{
		return groups[index];
	}
	
//	public static Color getColor(int group)
//	{
//		if(group == 0) return Color.yellow;
//		else if(group == 1) return Color.green;
//		else if(group == 2) return Color.red;
//		else if(group == 3) return Color.blue;
//		else if(group == 3) return Color.magenta;
//		return Color.black;
//	}
	
	public Color getColor(int index)
	{
		int group = groups[index];
		if(group == 0) return Color.yellow;
		else if(group == 1) return Color.green;
		else if(group == 2) return Color.red;
		else if(group == 3) return Color.blue;
		else if(group == 3) return Color.magenta;
		return Color.black;
	} 
}
