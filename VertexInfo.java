
public class VertexInfo {
	
	private Double distance=0.0;
	private Vertex previous;
	private boolean edited;
	
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Vertex getPrevious() {
		return previous;
	}
	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}
	public VertexInfo(Double distance, Vertex previous) {
		
		this.distance = distance;
		this.previous = previous;
	}
	public VertexInfo() {
	
	}
	public boolean isEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public VertexInfo(Double distance, Vertex previous, boolean edited) {
		this.distance = distance;
		this.previous = previous;
		this.edited = edited;
	}
	
	
	
	
	

}
