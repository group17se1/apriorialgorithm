package APrioriMiner;

public class Item {
	
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