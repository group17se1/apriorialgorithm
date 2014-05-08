package edu.uwec.cs.cs355.group17.aPrioriFinal.server;

import java.io.Serializable;

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/***************
	 * VARIABLES *
	 ***************/
	private String name;
	
	/******************
	 * CONSTRUCTORS *
	 ******************/
	public Item(String name) {
		this.name = name;
	}
	
	/*************
	 * METHODS *
	 *************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Item otherItem) {
		return equals((Object) otherItem);
	}
	
	public String toString(){
		return name;
	}
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		return name.equals(((Item)o).getName());
	}
}