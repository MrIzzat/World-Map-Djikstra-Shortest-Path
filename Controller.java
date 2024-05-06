import java.util.ArrayList;

public class Controller {

	private Graph Countries = new Graph(true,false);

	public Graph getCountries() {
		return Countries;
	}

	public void setCountries(Graph countries) {
		Countries = countries;
	}

	public Controller(){

	}

	public boolean addCountry(Country C) {

		Countries.addVertex(C);

		C.setImagex(getImageX(C));
		C.setImagey(getImageY(C));
		return true;
	}

	public void printAllCountires() {
		Countries.printVertices();
	}

	public boolean addRoad(Country C1, Country C2) {

		Countries.addEdge(Countries.searchVertex(C1.getName()), Countries.searchVertex(C2.getName()), getDistance(C1,C2));

		return true;

	}

	public double getDistance(Country C1,Country C2) {

		double x1=C1.getX();
		double y1=C1.getY();

		double x2=C2.getX();
		double y2=C2.getY();

		double temp = Math.pow((x1-x2),2);
		double temp2 = Math.pow((y1-y2),2);

		double temp3 = temp+temp2;

		double Distance = Math.sqrt(temp3);

		return Distance;

	}

	public Country searchCountry(String name) {
		return Countries.searchVertex(name).getData();

	}

	public boolean removeCountry(String name) {

		Countries.removeVertex(Countries.searchVertex(name));
		return true;
	}

	public boolean removeRoad(String name,String name2) {

		Countries.removeEdge(Countries.searchVertex(name),Countries.searchVertex(name2));
		return true;
	}

	public double getImageX(Country C) {
		double x = C.getX();
		//scaling system for coordinates that go off map
		//x=scaleX(x);

		if(x<0) {
			x = 180-(x*-1);
		}else {
			x=180+x;
		}
		x=x*1.897222;
		x-=5;

		return x;
	}

	public double getImageY(Country C) {
		double y=C.getY(); //
		//scaling system for coordinates that go off map
		//		y=scaleY(y);


		if(y<0) {
			if(-5<y) {
				y=((90+(y*-1))*3.79444)+10;
			}else {
				if(y>-10&&-5>y) {
					y=((90+(y*-1))*3.79444)+30;
				}else {
					if(y<-10&&-20<y) {
						y=((90+(y*-1))*3.79444)+35;
					}else {
						if(y<-20&&-30<y) {
							y=((90+(y*-1))*3.79444)+50;
						}else {
							if(-80>y&&y>=-90) {
								y=((90+(y*-1))*3.79444)+6;
							}else {
								y=((90+(y*-1))*3.79444)+60;
							}

						}
					}
				}
			}

		}else 
		{
			if(y>0) {
				if(y<5) {
					y=((90-y)*3.79444)-10;
				}
				else{
					if(5<y&&y<10) {
						y=((90-y)*3.79444)-30;
					}else {
						if(y>10&&20>y) {
							y=((90-y)*3.79444)-35;
						}else {
							if(y>20&&30>y) {
								y=((90-y)*3.79444)-50;
							}else {
								if(80<y&&y<=90) {
									y=((90-y)*3.79444)-6;
								}else {
									y=((90-y)*3.79444)-60;
								}

							}
						}
					}

				}
			}
			if(y==0) {
				y=(90-y)*3.79444;
			}


		}

		return y;
	}

	public double scaleX(double x) {
		if(-180<=x&&x<=180) {
			return x;
		}

		if(x>180) {
			int i=0;
			while(x>180) {
				x-=180;
				i++;
			}
			if(i%2==1) {
				x=(180-x)*-1;
			}
		}
		if(x<-180) {
			int i=0;
			while(x<-180) {
				x+=180;
				i++;
			}
			if(i%2==1) {
				x=180+x;
			}

		}
		return x;

	}

	public double scaleY(double y) {

		if(-90<=y&y<=90) {
			return y;
		}
		if(90<y) {
			int i=0;
			while(y>90) {
				y-=90;
				i++;
			}
			if(i%2==1) {
				y=(90-y)*-1;
			}
		}
		if(y<-90) {
			int i=0;
			while(y<-90) {
				y+=90;
				i++;
			}
			if(i%2==1) {
				y=90+y;
			}

		}

		return y;
	}

	public double getDistanceKM(Country C1,Country C2) {// assuming the map is 2D

		double x1=C1.getX();
		double y1=C1.getY();

		double x2=C2.getX();
		double y2=C2.getY();

		double x = x1-x2;
		double y = y1-y2;

		//		x=x*111*Math.cos(x*(Math.PI*180));
		//		y=y*110.574;

		double temp = Math.pow((x),2);
		double temp2 = Math.pow((y),2);

		double temp3 = temp+temp2;

		double Distance = Math.sqrt(temp3);
		Distance*=111;

		return Distance;

	}

	public double getDistanceKM2(Country C1,Country C2) { //would be used if the map was treated as speherical

		double R = 6371; //radius of the earth in km

		double x1=C1.getX(); // coordinates of c1
		double y1=C1.getY();

		double x2=C2.getX();// coordinates of c2
		double y2=C2.getY();

		double Y =y1-y2; //distance between the lattiudes in degrees
		double X =x1-x2;//distance between the longitudes in degrees

		double temp3 = Y*(Math.PI/180); // converting the distances to radians
		double temp4 = X*(Math.PI/180);

		double temp6 = y1*(Math.PI/180); // converting the lattitudes into radians
		double temp7 = y2*(Math.PI/180);

		double a=Math.sin(temp3/2) * Math.sin(temp3/2) +  // Haversine formula
				Math.cos(temp6) * Math.cos(temp7) * 
				Math.sin(temp4/2) * Math.sin(temp4/2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 

		double d = R * c; // distance in KM


		return d;
	}

	public Wrapper shortestRoads(Country C1,Country C2){ //finds the shortest roads between 2 countries using base djikstra

		Wrapper w = Countries.shortestPathBetween2(Countries.searchVertex(C1.getName()), Countries.searchVertex(C2.getName()));
		if(w==null) {
			return w;
		}
		ArrayList<Vertex> path = w.getV();
		double dkm =0;
		for(int i=0;i<path.size()-1;i++) {
			dkm+= getDistanceKM2(path.get(i).getData(),path.get(i+1).getData());
		}
		w.setDistancekm(dkm);
		ArrayList<Country> p = new ArrayList<Country>();
		for(int i=0;i<path.size();i++) {
			p.add(path.get(i).getData());
		}
		w.setC(p);

		return w;
	}

	public String pathToString(Wrapper w,boolean showSepDis) {//converts a path arraylist to a string to be printed 
		String s ="";
		ArrayList<Country> p = w.getC();
		ArrayList<Double> sepDis = new ArrayList<Double>();
		ArrayList<Double> sepDisKM = new ArrayList<Double>();
		if(showSepDis) {
			sepDis = w.getSepDis();
			sepDisKM = w.getSepDisKM();
		}

		for(int i=0;i<p.size();i++) {
			s+=p.get(i).getName();
			if(i!=p.size()-1) {
				s+="-->";//"("+sepDis.get(i)+" or "+sepDisKM.get(i)+"km)"
				if(showSepDis) {
					s+="("+sepDis.get(i)+" or "+sepDisKM.get(i)+"km)";
				}
			}
		}

		return s;
	}

	public ArrayList<Country> allRoads(Country C){ //Traverses every vertex and every edge end and places them in pairs in an allroads
		// problem is that it will connect unconnected vertexes DOES NOT WORK AS INTENDED
		ArrayList<Vertex> ver =Countries.BFS(Countries.searchVertex(C.getName()), null,null);
		ArrayList<Country> c = new ArrayList<Country>();

		for(Vertex v:ver) {
			c.add(v.getData());
		}

		return c;
	}

	public Wrapper shortestRoads2(Country C1,Country C2){ //finds the shortest roads between 2 countries using hash
															// after pre proccessing all pairs of countries
		Wrapper w = Countries.toShortestPath(Countries.searchVertex(C1.getName()), Countries.searchVertex(C2.getName()));
		if(w==null) {
			return w;
		}
		ArrayList<Vertex> path = w.getV();
		double dkm =0;
		for(int i=0;i<path.size()-1;i++) {
			dkm+= getDistanceKM2(path.get(i).getData(),path.get(i+1).getData());
		}
		w.setDistancekm(dkm);
		ArrayList<Country> p = new ArrayList<Country>();
		for(int i=0;i<path.size();i++) {
			p.add(path.get(i).getData());
		}
		w.setC(p);

		return w;
	}

	public void findAllShortestRoads() {
		Countries.findAllShortestPathsHash();
	}

	public Wrapper shortestRoads3(Country C1,Country C2){ //finds the shortest roads between 2 countries using modified djkstra
		Wrapper w = Countries.shortestPathBetween4(Countries.searchVertex(C1.getName()), Countries.searchVertex(C2.getName()));
		if(w==null) {
			return w;
		}
		ArrayList<Vertex> path = w.getV();
		double dkm =0;
		for(int i=0;i<path.size()-1;i++) {
			dkm+= getDistanceKM2(path.get(i).getData(),path.get(i+1).getData());
		}
		w.setDistancekm(dkm);
		ArrayList<Country> p = new ArrayList<Country>();
		for(int i=0;i<path.size();i++) {
			p.add(path.get(i).getData());
		}
		w.setC(p);

		return w;
	}
	
	public Wrapper shortestRoads5(Country C1,Country C2){ //finds the shortest roads between 2 countries using modified djkstra3
															//small space and faster calculations
		Wrapper w = Countries.shortestPathBetween5(Countries.searchVertex(C1.getName()), Countries.searchVertex(C2.getName()));
		if(w==null) {
			return w;
		}
		ArrayList<Vertex> path = w.getV();
		ArrayList<Double> sepDis = new ArrayList<Double>();
		ArrayList<Double> sepDisKM = new ArrayList<Double>();
		for(int i=0;i<path.size()-1;i++) {
			double d =  Math.round((getDistance(path.get(i).getData(),path.get(i+1).getData())*1000.0)/1000.0);
			double dkm =  Math.round(getDistanceKM2(path.get(i).getData(),path.get(i+1).getData())*1000.0)/1000.0;
			sepDis.add(d);
			sepDisKM.add(dkm);
		}
		w.setSepDis(sepDis);
		w.setSepDisKM(sepDisKM);
		
		double dkm =0;
		for(int i=0;i<path.size()-1;i++) {
			dkm+= getDistanceKM2(path.get(i).getData(),path.get(i+1).getData());
		}
		w.setDistancekm(dkm);
		ArrayList<Country> p = new ArrayList<Country>();
		for(int i=0;i<path.size();i++) {
			p.add(path.get(i).getData());
		}
		w.setC(p);

		return w;
	}
}
