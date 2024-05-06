import java.util.ArrayList;

public class Vertex {

	private Country data;
	private ArrayList<Edge> edges;

	public Vertex(Country input) {
		this.data=input;
		this.edges = new ArrayList<Edge>();
	}

	public void addEdge(Vertex endVertex, Double weight) {
		this.edges.add(new Edge(this,endVertex,weight));

	}

	public void removeEdge(Vertex endVertex) {
		this.edges.removeIf(edge -> edge.getEnd().equals(endVertex));
	}

	public Country getData() {
		return data;
	}

	public void Country(Country data) {
		this.data = data;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public void printEdges (boolean showWeight) {

		String message = "";

		if (this.edges.size() ==0) {
			System.out.println (this.data.toString() +" --> no roads :("); // Vertex does not have edges
			return;
		}

		for(int i =0; i < this.edges.size() ; i++) {
			if (i == 0) {
				message += this.edges.get (i).getStart ().data.toString() + " -->  ";
			}
			message += this.edges.get(i).getEnd().data.toString();
			if (showWeight) {
				message += " ("+ this.edges.get(i).getWeight()+")";
			}
			if (i != this.edges.size() -1) {
				message += ", ";
			}
		}
		System.out.println (message) ;
	}

	public String toString(boolean showWeight) {
		String message = "";

		if (this.edges.size() ==0) {
			System.out.println (this.data.toString() +" --> no edges :("); // Vertex does not have edges
			return "";
		}

		for(int i =0; i < this.edges.size() ; i++) {
			if (i == 0) {
				message += this.edges.get (i).getStart ().data.toString() + " -->  ";
			}
			message += this.edges.get(i).getEnd().data.toString();
			if (showWeight) {
				message += " ("+ this.edges.get(i).getWeight()+")";
			}
			if (i != this.edges.size() -1) {
				message += ", ";
			}
		}
		return message;
		
	}
	
public Edge searchEdge(Vertex v) {
		
		for(Edge e: edges) {
			if(e.getEnd()==v) {
				return e;
			}
		}
		
		
		return null;
	}
}

