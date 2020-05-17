package ssd;

public class Subtitle {
	private String name;
	private String ID;

	/**
	 * 
	 * @param name
	 * @param iD
	 */
	public Subtitle(String name, String iD) {
		super();
		this.name = name;
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return name;
	}
}
