package org.ssu.ml.ui;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class CEdgeData {
	float[] weight = null;
	String[] srcName = null;
	String[] destName = null;
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
			result = srcName[index]+", "+destName[index]+" ["+weight[index]+"]";
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
		weight = new float[pointCount];    	
    	srcName = new String[pointCount];
    	destName = new String[pointCount];
    	groups = new int[pointCount];
	}

	public void init()
	{
		weight = new float[pointCount];    	
    	srcName = new String[pointCount];
    	destName = new String[pointCount];
    	groups = new int[pointCount];
	}
	
	public int insertItem(String srcName, String destName, float weight)
	{
		this.srcName[itemNum] = srcName;
		this.destName[itemNum] = destName;
		this.weight[itemNum] = weight;
		
		Random random = new Random();		//need more work.
		groups[itemNum] = random.nextInt(5);
		itemNum++;
		
		return itemNum;
	}
	
	
	public String[] getPointerNames() {
		return srcName;
	}
	public void setPointerNames(String[] pointerName) {
		this.srcName = pointerName;
	}
	
	public String getPointerName(int index){
		return srcName[index];
	}
	
	public void setPointerName(int index, String name){
		this.srcName[index] = name;
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
	
	public float[] getWeight() {
		return weight;
	}

	public void setWeight(float[] weight) {
		this.weight = weight;
	}

	public String[] getSrcName() {
		return srcName;
	}

	public void setSrcName(String[] srcName) {
		this.srcName = srcName;
	}

	public String[] getDestName() {
		return destName;
	}

	public void setDestName(String[] destName) {
		this.destName = destName;
	}

	public int[] getGroups() {
		return groups;
	}

	public void setGroups(int[] groups) {
		this.groups = groups;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

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
