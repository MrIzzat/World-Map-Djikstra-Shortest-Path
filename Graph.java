import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Graph {

	private ArrayList<Vertex> vertices;
	private boolean isWeighted;
	private boolean isDirected;
	private Dictionary<Country, Dictionary<Country,Wrapper>> shortestPathsHash = new Hashtable(); //assuming we have quadratic space
	// stores all pairs of paths 
	private Dictionary<Country,Double> dis = new Hashtable(); //cretes resusable distance and path dictionaries
	private Dictionary<Country,Vertex> prev = new Hashtable();
	private int fir=0;
	private ArrayList<Vertex> trav = new ArrayList<Vertex>(); //keeps track of traversed vertices to help re intialize them
	private Dictionary<Country,Boolean> check = new Hashtable();// to check if a country has had it's values changed
	
	private Dictionary<Country,VertexInfo> info = new Hashtable();//saves space by storing the distance and previous in one object is reusable
																// also saves the edited check to check if it's value has been chagned
	public Graph(boolean isWeighted,boolean isDirected) {
		this.isWeighted= isWeighted;
		this.isDirected=isDirected;
		this.vertices = new ArrayList<Vertex>();

	}

	public boolean addVertex(Country C) {
		Vertex found = searchVertex(C.getName());

		if(found==null) {
			Vertex newVertex = new Vertex(C);
			this.vertices.add(newVertex);
			return true;
		}else {
			System.out.println("There is another vertex with the same name.");
			return false;
		}


	}

	public void addEdge(Vertex v1,Vertex v2,Double weight) {

		if(v1==null) {
			System.out.println("Could not add the edge because v1 is null.");
			return;
		}
		if(v2==null) {
			System.out.println("Could not add the edge because v2 is null.");
			return;
		}

		if(!this.isWeighted) {
			weight=null;
		}	

		v1.addEdge(v2,weight);
		if(!this.isDirected) {
			v2.addEdge(v1, weight);
		}

	}

	public void removeEdge(Vertex v1,Vertex v2) {
		v1.removeEdge(v2);

		if(!this.isDirected) {
			v2.removeEdge(v1);
		}

	}

	public void removeVertex(Vertex v) {

		if(!isDirected) {
			for(Edge e: v.getEdges()) {
				Vertex n = e.getEnd();
				n.removeEdge(v);
			}
		}
		this.vertices.remove(v);

	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	public void setWeighted(boolean isWeighted) {
		this.isWeighted = isWeighted;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}

	public Vertex searchVertex (String value) {
		for (Vertex v: this.vertices) {
			if (v.getData().getName().equals(value)) {
				return v;
			}
		}
		return null;
	}

	public void printVertices () {
		for (Vertex v: this. vertices ) {
			v.printEdges(isWeighted) ;
		}
	}

	public Vertex getStartingVertex() {
		return this.vertices.get(0);
	}

	public ArrayList<Vertex> preorderDFS(Vertex start,ArrayList<Vertex> visited){ // using recursion
		if(start==null) {
			start = getStartingVertex();
		}
		if(visited ==null) {
			visited = new ArrayList<Vertex>();
			visited.add(start);
		}

		for(Edge e: start.getEdges()) {
			Vertex n = e.getEnd(); //get a neighbor vertex
			if(!visited.contains(n)) {
				visited.add(n);
				preorderDFS(n,visited);
			}
		}

		return visited;
	}

	public ArrayList<Vertex> stackpreorderDFS(Vertex start, ArrayList<Vertex> visited){ //  pre order dpf using stack
		if(start==null) {
			start = getStartingVertex();
		}
		if(visited == null) {
			visited = new ArrayList<Vertex>();
		}

		Stack<Vertex> temp = new Stack<Vertex>();
		temp.push(start);

		while(!temp.isEmpty()) {
			Vertex t = temp.pop();
			if(!visited.contains(t)) {
				visited.add(t);
				for(int i = t.getEdges().size()-1;i>-1;i--) {
					Edge e=t.getEdges().get(i);
					Vertex n = e.getEnd();
					temp.push(n);
				}
			}
		}



		return visited;
	}

	public ArrayList<Vertex> postorderDFS(Vertex start,ArrayList<Vertex> visited,ArrayList<Vertex> out){ // using recursion
		if(start==null) {
			start = getStartingVertex();
		}
		if(visited ==null) {
			visited = new ArrayList<Vertex>();
			visited.add(start);
		}
		if(out==null) {
			out = new ArrayList<Vertex>();
		}

		for(Edge e: start.getEdges()) {
			Vertex n = e.getEnd(); //get a neighbor vertex
			if(!visited.contains(n)) {
				visited.add(n);
				postorderDFS(n,visited,out);	
			}
		}
		out.add(start);


		return out;
	}

	public ArrayList<Vertex> stackpostorderDPF(Vertex start, ArrayList<Vertex> visited){ //**WPS**  pre order dpf using stack 
		if(start==null) {
			start = getStartingVertex();
		}
		if(visited == null) {
			visited = new ArrayList<Vertex>();
		}

		Stack<Vertex> temp = new Stack<Vertex>();

		ArrayList<Vertex> out = new ArrayList<Vertex>();
		temp.push(start);

		while(!temp.isEmpty()) {
			Vertex t = temp.pop();
			if(!visited.contains(t)) {
				visited.add(t);

				for(int i = t.getEdges().size()-1;i>-1;i--) {
					Edge e=t.getEdges().get(i);
					Vertex n = e.getEnd();
					temp.push(n);
				}
				out.add(t);

			}
		}



		return out;
	}

	public ArrayList<Vertex> topDFS(Vertex start){ // using recursion

		ArrayList<Vertex> temp = postorderDFS(start,null,null);
		ArrayList<Vertex> out = new ArrayList<Vertex>();

		for(int i=temp.size()-1;i>-1;i--) {
			out.add(temp.get(i));
		}
		return out;
	}

	public ArrayList<Vertex> BFS(Vertex start , ArrayList<Vertex> visited,ArrayList<Vertex> out){

		if(start==null) {
			start = getStartingVertex();
		}
		if(visited == null) {
			visited = new ArrayList<Vertex>();
		}	
		if(out == null) {
			out = new ArrayList<Vertex>();
		}	

		Queue<Vertex> visitedq = new ConcurrentLinkedQueue<Vertex>();
		visitedq.add(start);
		visited.add(start);
		while(!visitedq.isEmpty()) {
			Vertex curr=visitedq.poll();
			out.add(curr);
			//			System.out.println(curr.getData());
			for(Edge e : curr.getEdges()) {
				Vertex n = e.getEnd();
				if(!visited.contains(n)) {
					visited.add(n);
					visitedq.add(n);
				}

			}
		}


		return out;

	}

	public Dictionary[] djikstra(Vertex start) {

		Dictionary<Country,Double> distances = new Hashtable<>(); 
		Dictionary<Country,Vertex> previous = new Hashtable<>(); 
		Queue<QueueObject> q = new PriorityQueue<QueueObject>();

		q.add(new QueueObject(start,0));

		for(Vertex v: getVertices()) {
			if(v!=start) {
				distances.put(v.getData(),Double.MAX_VALUE);
			}
			previous.put(v.getData(), new Vertex(new Country("NULL",0,0)));
		}

		distances.put(start.getData(), 0.0);

		while(!q.isEmpty()) {
			Vertex curr = q.poll().vertex;
			for(Edge e : curr.getEdges()){
				Double alt = distances.get(curr.getData()) + e.getWeight();
				Country n = e.getEnd().getData();
				if(alt<distances.get(n)) {
					distances.put(n,alt);
					previous.put(n,curr);
					q.add(new QueueObject(e.getEnd(),distances.get(n)));
				}
			}
		}

		return new Dictionary[]{distances,previous};
	}

	public void dijkstraResultPrinter(Dictionary[ ] d){
		System.out.println( "Distances : \n");
		for (Enumeration keys =d[0].keys(); keys.hasMoreElements();){
			String nextKey = keys.nextElement().toString () ;
			System.out.println (nextKey+": "+d[0].get(nextKey) ) ;
		}
		System.out.println ( "\nPrevious : \n") ;
		for (Enumeration keys = d[1].keys() ; keys.hasMoreElements();) {
			String nextKey = keys.nextElement().toString ();
			Vertex nextVertex = (Vertex) d[1].get (nextKey) ;
			System.out.println (nextKey +": " +nextVertex . getData ()) ;
		}
	}

	public  void shortestPathBetween(Vertex start,Vertex target) {

		Dictionary[] djikstra = djikstra(start);
		Dictionary distances = djikstra[0];
		Dictionary previous = djikstra[1];

		Double distance = (Double)distances.get(target.getData());
		if(distance == Double.MAX_VALUE) {
			System.out.println("They are disconnected");
		}else {
			System.out.println("The shortest path between "+start.getData().getName()+" and "+target.getData().getName()+" is: "+distance);
			ArrayList<Vertex> path = new ArrayList<Vertex>();
			Vertex v = target;

			while(v.getData().getName()!="Null") {
				path.add(0,v);
				v = (Vertex)previous.get(v.getData());
			}
			System.out.println("Shortest Path:");
			String t="";
			for(int i=0;i<path.size();i++) {

				t+=path.get(i).getData().getName();
				if(i!=path.size()-1) {
					t+="-->";
				}

			}
			System.out.println(t);

		}



	}

	public  Wrapper shortestPathBetween2(Vertex start,Vertex target) { // same as it's predesseccor but returns the path in a wrapper

		Wrapper w;
		Dictionary[] djikstra = djikstra(start);
		Dictionary distances = djikstra[0];
		Dictionary previous = djikstra[1];

		Double distance = (Double)distances.get(target.getData());
		if(distance == Double.MAX_VALUE) {
			return null;
		}else {
			ArrayList<Vertex> path = new ArrayList<Vertex>();
			Vertex v = target;

			while(!v.getData().getName().equals("NULL")) {
				path.add(0,v);
				v = (Vertex)previous.get(v.getData());
			}
			w = new Wrapper(distance,path);

			return w;

		}



	}

	private  void shortestPathBetween3(Vertex start,Vertex target) { // stores the shortest Paths into the hashtable

		Wrapper w;
		Dictionary[] djikstra = djikstra(start);
		Dictionary distances = djikstra[0];
		Dictionary previous = djikstra[1];

		Double distance = (Double)distances.get(target.getData());
		if(distance==Double.MAX_VALUE) {
			return;
		}else {

			ArrayList<Vertex> path = new ArrayList<Vertex>();
			Vertex v = target;

			while(!v.getData().getName().equals("NULL")) {
				path.add(0,v);
				v = (Vertex)previous.get(v.getData());
			}

			w = new Wrapper(distance,path);

			shortestPathsHash.get(start.getData()).put(target.getData(), w);

		}
	}

	public void findAllShortestPathsHash() { //passes all pairs of vertices into shortestpathbetween to calculate
		for(Vertex v1:vertices) {			// all paths and distances for all pairs of vertices and stores them into a hashtable
			shortestPathsHash.put(v1.getData(), new Hashtable());
		}


		for(Vertex v1:vertices) {
			for(Vertex v2:vertices) {
				shortestPathBetween3(v1,v2);
			}
		}
	}

	public Wrapper toShortestPath(Vertex start,Vertex target){ // returns a wrapper containing path and distance precalculated
		//by retrieving it from the hashtable
		Wrapper path;
		path = shortestPathsHash.get(start.getData()).get(target.getData());

		return path;

	}

	public Dictionary[] djikstra2(Vertex start,Vertex target) { //modified verison of djkistra that stops the search after finding the target

		Dictionary<Country,Double> distances = new Hashtable<>(); 
		Dictionary<Country,Vertex> previous = new Hashtable<>(); 
		Queue<QueueObject> q = new PriorityQueue<QueueObject>();

		q.add(new QueueObject(start,0));

		for(Vertex v: getVertices()) {
			if(v!=start) {
				distances.put(v.getData(),Double.MAX_VALUE);
			}
			previous.put(v.getData(), new Vertex(new Country("NULL",0,0)));
		}

		distances.put(start.getData(), 0.0);

		while(!q.isEmpty()) {
			Vertex curr = q.poll().vertex;
			if(curr.getData().getName().equals(target.getData().getName())) {
				continue;
			}
			for(Edge e : curr.getEdges()){
				Double alt = distances.get(curr.getData()) + e.getWeight();
				Country n = e.getEnd().getData();
				if(alt<distances.get(n)) {
					distances.put(n,alt);
					previous.put(n,curr);
					q.add(new QueueObject(e.getEnd(),distances.get(n)));
				}
			}
		}

		return new Dictionary[]{distances,previous};
	}

	public  Wrapper shortestPathBetween4(Vertex start,Vertex target) { // uses modified djkistra to find the shortestpath

		Wrapper w;
		Dictionary[] djikstra = djikstra2(start,target);
		Dictionary distances = djikstra[0];
		Dictionary previous = djikstra[1];

		Double distance = (Double)distances.get(target.getData());
		if(distance==Double.MAX_VALUE) {
			return null;
		}else {

			ArrayList<Vertex> path = new ArrayList<Vertex>();
			Vertex v = target;

			while(!v.getData().getName().equals("NULL")) {
				path.add(0,v);
				v = (Vertex)previous.get(v.getData());
			}

			w = new Wrapper(distance,path);

			return w;

		}
	}
	
	public void djikstra4(Vertex start,Vertex target) { //modified verison of djkistra that stops the search after finding the target
		// and reuses the same space 
		Queue<QueueObject> q = new PriorityQueue<QueueObject>();

		q.add(new QueueObject(start,0));

		if(fir==0) {
			fir++;
			for(Vertex v: getVertices()) {
				if(v!=start) {
					dis.put(v.getData(),Double.MAX_VALUE);
				}
				prev.put(v.getData(), new Vertex(new Country("NULL",0,0)));
				check.put(v.getData(), false);
			}
		}
		dis.put(start.getData(), 0.0);
		trav.add(start);
		while(!q.isEmpty()) {
			Vertex curr = q.poll().vertex;
			if(curr.getData().getName().equals(target.getData().getName())) {
				continue;
			}
			for(Edge e : curr.getEdges()){
				Double alt = dis.get(curr.getData()) + e.getWeight();
				Country n = e.getEnd().getData();
				if(alt<dis.get(n)) {
					dis.put(n,alt);
					prev.put(n,curr);

					if(!check.get(e.getEnd().getData())) {
						trav.add(e.getEnd());
						check.put(e.getEnd().getData(), true);
					}
					q.add(new QueueObject(e.getEnd(),dis.get(n)));
				}
			}
		}

	}
	
	public void djikstra3(Vertex start,Vertex target) { //modified verison of djkistra that stops the search after finding the target
		// and reuses the same space but uses only one hashtable for distances and previous
		Queue<QueueObject> q = new PriorityQueue<QueueObject>();

		q.add(new QueueObject(start,0));

		if(fir==0) {
			fir++;
			for(Vertex v: getVertices()) {
				VertexInfo x = new VertexInfo();
				if(v!=start) {
					x.setDistance(Double.MAX_VALUE);

					//dis.put(v.getData(),Double.MAX_VALUE);
				}
				x.setPrevious(new Vertex(new Country("NULL",0,0)));
				x.setEdited(false);
				info.put(v.getData(),x);
				//prev.put(v.getData(), new Vertex(new Country("NULL",0,0)));
			//	check.put(v.getData(), false);
			}
		}
		VertexInfo w = info.get(start.getData());
		w.setDistance(0.0);
		info.put(start.getData(), w);

		//dis.put(start.getData(), 0.0);
		trav.add(start);
		while(!q.isEmpty()) {
			Vertex curr = q.poll().vertex;
			if(curr.getData().getName().equals(target.getData().getName())) {
				continue;
			}
			for(Edge e : curr.getEdges()){
				w=info.get(curr.getData());
				Double alt = w.getDistance()+e.getWeight();
				Country n = e.getEnd().getData();
				//Double alt = dis.get(curr.getData()) + e.getWeight();
				//Country n = e.getEnd().getData();
				VertexInfo x=info.get(n); 
				if(alt<x.getDistance()) {
					x.setDistance(alt);
					x.setPrevious(curr);
					//dis.put(n,alt);
					//prev.put(n,curr);
					//check.get(e.getEnd().getData())
					if(!x.isEdited()) {
						trav.add(e.getEnd());
//						check.put(e.getEnd().getData(), true);
						x.setEdited(true);
					}
					//q.add(new QueueObject(e.getEnd(),dis.get(n)));
					q.add(new QueueObject(e.getEnd(),x.getDistance()));
				}
			}
		}

	}

	public  Wrapper shortestPathBetween5(Vertex start,Vertex target) { // uses modified djkistra3 to find the shortestpath

		Wrapper w;
		djikstra3(start,target);

		//Double distance = (Double)dis.get(target.getData());
		Double distance = (Double)info.get(target.getData()).getDistance();
		if(distance==Double.MAX_VALUE) {

			//resetPrevandDis();
			resetInfo();
			trav=new ArrayList<Vertex>();
			return null;

		}else {

			ArrayList<Vertex> path = new ArrayList<Vertex>();
			Vertex v = target;
			while(!v.getData().getName().equals("NULL")) {
				path.add(0,v);
				v = (Vertex)info.get(v.getData()).getPrevious();
			}

			w = new Wrapper(distance,path);


			//resetPrevandDis();
			resetInfo();
			trav=new ArrayList<Vertex>();
			return w;

		}


	}

	private void resetInfo() {

		for(Vertex v:trav) {
			VertexInfo x = new VertexInfo(Double.MAX_VALUE,new Vertex(new Country("NULL",0,0)),false);
			info.put(v.getData(),x);
			//check.put(v.getData(), false);
		}

	}

	private void resetDis() {

		for(Vertex v:trav) {
			dis.put(v.getData(), Double.MAX_VALUE);
		}


	}

	private void resetPrev() {


		for(Vertex v:trav) {
			prev.put(v.getData(), new Vertex(new Country("NULL",0,0)));
		}


	}

	private void resetPrevandDis() {

		for(Vertex v:trav) {
			prev.put(v.getData(), new Vertex(new Country("NULL",0,0)));
			dis.put(v.getData(), Double.MAX_VALUE);
			check.put(v.getData(), false);
		}

	}




}