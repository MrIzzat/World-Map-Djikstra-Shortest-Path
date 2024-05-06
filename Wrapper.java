import java.util.ArrayList;

public class Wrapper {
	
	private Double distance;
	private ArrayList<Vertex> v;
	private ArrayList<Country> c;
	private Double distancekm;
	private ArrayList<Double> sepDis;
	private ArrayList<Double> sepDisKM;
	
	public Wrapper (Double distance, ArrayList<Vertex> v) {
		this.distance=distance;
		this.v=v;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public ArrayList<Vertex> getV() {
		return v;
	}

	public void setV(ArrayList<Vertex> v) {
		this.v = v;
	}

	public Wrapper(Double distance, ArrayList<Vertex> v, Double distancekm) {
		this.distance = distance;
		this.v = v;
		this.distancekm = distancekm;
	}

	public Double getDistancekm() {
		return distancekm;
	}

	public void setDistancekm(Double distancekm) {
		this.distancekm = distancekm;
	}

	public ArrayList<Country> getC() {
		return c;
	}

	public void setC(ArrayList<Country> c) {
		this.c = c;
	}

	public ArrayList<Double> getSepDis() {
		return sepDis;
	}

	public void setSepDis(ArrayList<Double> sepDis) {
		this.sepDis = sepDis;
	}

	public ArrayList<Double> getSepDisKM() {
		return sepDisKM;
	}

	public void setSepDisKM(ArrayList<Double> sepDisKM) {
		this.sepDisKM = sepDisKM;
	}
	
	
	
	
	

}
