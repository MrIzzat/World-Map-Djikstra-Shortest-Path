
public class Country {
	
	private double x=0; //longitude
	private double y=0; //lattitude
	private String name="";
	private double imagex=0; //make 2 new doubles for image coord
	private double imagey=0;
	public Country(String name,double x,double y) {
		this.x=x;
		this.y=y;
		this.name=name;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Name: "+name+" Longitude: "+x+" Lattitude: "+y+" ";
	}

	public double getImagex() {
		return imagex;
	}

	public void setImagex(double imagex) {
		this.imagex = imagex;
	}

	public double getImagey() {
		return imagey;
	}

	public void setImagey(double imagey) {
		this.imagey = imagey;
	}


	
	

}
