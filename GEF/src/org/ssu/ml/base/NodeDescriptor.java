package org.ssu.ml.base;

public class NodeDescriptor {
	String name = "";
	int group = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	
	public String toString()
	{
		return name;
	}
	
	
}
