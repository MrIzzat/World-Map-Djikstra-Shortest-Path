import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.*;

public class Main extends Application implements EventHandler<ActionEvent> {

	static Controller C = new Controller();
	Button[] bs;
	int click=0;
	int drag=0;

	Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	TextArea srcinfo;
	TextArea destinfo;

	String ssrc = "";
	String sdest="";

	ComboBox<String> src;

	ComboBox<String> dest;

	Pane p2 = new Pane();

	int csrh;
	int s=0;
	int d=0;

	public static void main(String[] args) throws FileNotFoundException {
		readFromFile();
		//C.findAllShortestRoads(); //pre proccesses all pairs of vertices for hashmap version
		//C.printAllCountires();
		//test();
		launch(args);

	}

	@Override
	public void start(Stage start) throws Exception {
		start.setTitle("Shortest Distance Between Countries");
		HBox main = new HBox();

		HBox h1 = new HBox();
		HBox h2 = new HBox();

		VBox v1 = new VBox();
		VBox v2 = new VBox();
		StackPane stack = new StackPane();
		v1.getChildren().addAll(stack);
		v2.getChildren().addAll(h2);

		main.getChildren().addAll(v1,v2);

		ImageView img = new ImageView(new Image("C:\\Users\\MrIzzat\\eclipse-workspace\\algoprj3\\worldMap.jpg",683,683,false,false));
		h1.setAlignment(Pos.CENTER);

		v1.setAlignment(Pos.CENTER_LEFT);


		Button comp = new Button("Compute");
		comp.setPadding(new Insets(5,5,5,5));
		
		srcinfo = new TextArea();
		destinfo = new TextArea();

		Text srct = new Text("Source:");
		Text destt = new Text("Destination:");

		Button reset = new Button("Reset");
		reset.setPadding(new Insets(5,5,5,5));
		int i=0;
		String[] cnames = new String[C.getCountries().getVertices().size()];
		while(i<C.getCountries().getVertices().size()) {
			cnames[i]=C.getCountries().getVertices().get(i).getData().getName();
			i++;
		}
		sortArray(cnames);


		src = new ComboBox(FXCollections
				.observableArrayList(cnames));

		dest = new ComboBox(FXCollections
				.observableArrayList(cnames));


		src.setOnAction(e -> {
			if(src.getValue()!=null) {

				srcinfo.setText(C.getCountries().searchVertex(src.getValue()).getData().toString());
				ssrc= C.getCountries().searchVertex(src.getValue()).getData().getName();
				s= C.getCountries().getVertices().indexOf(C.getCountries().searchVertex(src.getValue()));
				bs[s].setStyle("-fx-background-color: #0000FF; ");


				click++;

			}

		});

		dest.setOnAction(e -> {
			if(dest.getValue()!=null) {

				destinfo.setText(C.getCountries().searchVertex(dest.getValue()).getData().toString());
				sdest= C.getCountries().searchVertex(dest.getValue()).getData().getName();
				d= C.getCountries().getVertices().indexOf(C.getCountries().searchVertex(dest.getValue()));
				bs[d].setStyle("-fx-background-color: #FF0000; ");
				click++;
			}

		});


		GridPane g = new GridPane();

		g.add(srct, 0, 0);
		g.add(src, 1, 0);
		g.add(srcinfo, 1, 1);

		g.add(destt, 0, 2);
		g.add(dest, 1, 2);
		g.add(destinfo, 1, 3);

		v2.getChildren().addAll(g);
		v2.setAlignment(Pos.CENTER_RIGHT);

		g.setAlignment(Pos.CENTER_RIGHT);

		g.setMargin(srct,new Insets(0,0,0,100));
		g.setMargin(destt,new Insets(0,0,0,100));

		g.setHgap(10);
		g.setVgap(5);

		g.setPrefWidth(500);

		srcinfo.setPrefHeight(50);
		g.setMargin(srcinfo, new Insets(0,0,10,0));

		destinfo.setPrefHeight(50);

		srcinfo.setEditable(false);
		destinfo.setEditable(false);

		g.add(comp, 1, 4);
		g.add(reset, 1, 7);
		//g.setMargin(reset, new Insets(0,0,0,-250));


		TextArea path = new TextArea();
		Text patht = new Text("Path:");
		path.setWrapText(true); // to prevent the horzontal scroll

		g.add(path, 1, 5);
		g.add(patht, 0, 5);
		g.setMargin(patht,new Insets(0,0,0,100));

		path.setPrefHeight(150);

		TextField dist = new TextField();
		Text distt = new Text("Distance:");

		g.add(dist, 1, 6);
		g.add(distt, 0, 6);
		g.setMargin(distt,new Insets(0,0,0,100));

		dist.setEditable(false);
		path.setEditable(false);

		Pane p = loadMap();

		stack.getChildren().addAll(img);  
		stack.getChildren().add(p2);
		stack.getChildren().add(p);

		for(csrh=0;csrh<bs.length;csrh++) {
			bs[csrh].setOnAction(this);
		}
		
		for(csrh=0;csrh<bs.length;csrh++) {
			
				int temp = csrh;
				Text t= new Text();
				bs[csrh].setOnMouseEntered(e ->{
					String name = C.getCountries().getVertices().get(temp).getData().getName();
					t.setText(name);
					t.setFont(Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 10));
					if(name.equals("New Zealand")) {
						t.setLayoutX(bs[temp].getLayoutX()-55);
						t.setLayoutY(bs[temp].getLayoutY());
					}else {
						if(name.equals("Chukotka")){
							t.setLayoutX(bs[temp].getLayoutX()-30);
							t.setLayoutY(bs[temp].getLayoutY());
						}else {
							if(name.equals("Antartica")) {
								t.setLayoutX(bs[temp].getLayoutX()-20);
								t.setLayoutY(bs[temp].getLayoutY());
							}else {
								t.setLayoutX(bs[temp].getLayoutX()-15);
								t.setLayoutY(bs[temp].getLayoutY()+35);
							}
							
						}
						
					}
					
					
					p2.getChildren().addAll(t);
					
					
				});
				
				bs[csrh].setOnMouseExited(e ->{
					
					p2.getChildren().remove(t);
					
					
				});
			
			
		}

		comp.setOnAction(e ->{

			if(ssrc==null||sdest==null) { //incase the string has been reinitilzed and is empty
				Alert a = new Alert(AlertType.NONE);
				a.setAlertType(AlertType.INFORMATION);
				a.setTitle("Invalid Input");
				a.setContentText("The Source or the Destinantion has not been selected, make sure to re-select the source and destination.");
				a.show();
			}else{
				if(ssrc.isEmpty()||sdest.isEmpty()) {// if the strings are empty
					Alert a = new Alert(AlertType.NONE);
					a.setAlertType(AlertType.INFORMATION);
					a.setTitle("Invalid Input");
					a.setContentText("The Source or the Destinantion has not been selected, make sure to re-select the source and destination.");
					a.show();

				}else {


					double distance =0;
					dist.setText(distance+"");
					Wrapper w = C.shortestRoads5(C.searchCountry(ssrc), C.searchCountry(sdest)); //switching to shortestRoads() will
					if(w == null) {																//will use non space complexity shortestPath
						Alert a = new Alert(AlertType.NONE);									//function but slower
						a.setAlertType(AlertType.INFORMATION);
						a.setTitle("Invalid Path");
						a.setContentText("These Countries are Disconnected");
						a.show();

						path.setText("");
						dist.setText(Double.MAX_VALUE+"");

					}else {
						//ArrayList<Country> t = w.getC();
						//ArrayList<Country> t = C.allRoads(C.searchCountry("Alaska"));


//						path.setText(C.pathToString(t,w.getSepDis(),w.getSepDisKM()));
						path.setText(C.pathToString(w,false));

						double d =  Math.round(w.getDistance()*1000.0)/1000.0;
						double dkm =  Math.round(w.getDistancekm()*1000.0)/1000.0;

						dist.setText(d+" or "+dkm+" km");

						stack.getChildren().remove(p2);
						stack.getChildren().remove(p);

						p2 = new Pane();
						p2 = addLines(p2,w.getC());

						stack.getChildren().add(p2);
						stack.getChildren().add(p);

					}

				}
			}			
		}); 


		reset.setOnAction(e ->{
			ssrc="";
			sdest="";

			dist.setText("");
			path.setText("");
			srcinfo.setText("");
			destinfo.setText("");
			src.valueProperty().set(null);
			dest.valueProperty().set(null);


			stack.getChildren().remove(p2);
			stack.getChildren().remove(p);

			p2 = new Pane();

			stack.getChildren().add(p2);
			stack.getChildren().add(p);

			click=0;
			if(s!=-1) {
				bs[s].setStyle(null);
				
				s=-1;
				
			}
			if(d!=-1) {
				bs[d].setStyle(null);
				d=-1;
			}

		});




		main.setStyle("-fx-background-color: #13acd2;-fx-border-color:#228B22 ; -fx-border-width: 5px");




		Scene S = new Scene(main,screenSize.getWidth(),screenSize.getHeight());	
		start.setScene(S);	
		start.setMaximized(true);
		start.show();


	}

	public static void test() {
		C.findAllShortestRoads();
		Wrapper w = C.shortestRoads2(C.searchCountry("Mexico City"), C.searchCountry("Otwa"));



		if(w==null) {
			System.out.println("They are disconnected :(");
		}else {
			ArrayList<Vertex> v=  w.getV();
			String p ="";
			for(int i=0;i<v.size();i++) {
				p+=v.get(i).getData().getName();
				if(i!=v.size()-1) {
					p+="-->";
				}
			}
			System.out.println(p);
			System.out.println("the distance is: "+w.getDistance());
			System.out.println("the distance in km is: "+w.getDistancekm()+" km");
		}



		//		System.out.println("distance is: "+C.getDistanceKM(C.searchCountry("Nigeria"), C.searchCountry("Washington DC")));

		//	C.printAllCountires();
	}

	public static void readFromFile() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("Countries.txt"));

		if(sc.hasNextLine()==false) {
			System.out.println("File is empty!");
			return;
		}
		String counter = sc.nextLine();
		String[] ctrs = counter.split(",");
		int numc = Integer.parseInt(ctrs[0]);
		int numr = Integer.parseInt(ctrs[1]);

		for(int i=0;i<numc;i++) {
			String cinfo = sc.nextLine();
			String[] cinfos= cinfo.split(",");
			C.addCountry(new Country(cinfos[0],Double.parseDouble(cinfos[1]),Double.parseDouble(cinfos[2])));

		}

		ArrayList<Edge> added = new ArrayList<Edge>();
		for(int i=0;i<numr;i++) {
			String rinfo = sc.nextLine();
			String[] rinfos= rinfo.split(",");
			C.addRoad(C.searchCountry(rinfos[0]), C.searchCountry(rinfos[1]));
			added.add(new Edge(C.getCountries().searchVertex(rinfos[0]),C.getCountries().searchVertex(rinfos[1]),null));
		}

		//	randomizeRoads(numc,numr,added);
	}

	public static void randomizeRoads(int numc,int numr,ArrayList<Edge> added) throws FileNotFoundException {
		ArrayList<Vertex> v = C.getCountries().getVertices();
		//		ArrayList<Edge> added = new ArrayList<Edge>();
		Random r = new Random();

		for(int i=0;i<420;i++) {
			int n = r.nextInt(numc);
			int m= r.nextInt(numc);
			if(m==n) {
				i--;
				continue;
			}
			Vertex a = v.get(n);
			Vertex b = v.get(m);
			Edge e=new Edge(a,b,null);
			boolean flag=false;
			for(int k=0;k<added.size();k++) {
				Edge x = added.get(k);
				if((x.getEnd()==e.getEnd()&&x.getStart()==e.getStart())
						||(x.getStart()==e.getEnd()&&x.getEnd()==e.getStart())) {
					flag =true;
					break;
				}	
			}
			if(flag) {
				i--;
				continue;
			}else {
				C.addRoad(a.getData(),b.getData());
				added.add(e);
			}
		}
		writeIntoFile(numc,numr);
	}

	public static void writeIntoFile(int numc,int numr) throws FileNotFoundException {
		PrintWriter write = new PrintWriter(new File("Countries.txt"));
		write.write(numc+","+numr+"\n");
		ArrayList<Vertex> v = C.getCountries().getVertices();
		ArrayList<Edge> added = new ArrayList<Edge>();
		for(int i=0;i<numc;i++) {
			Country c = v.get(i).getData();
			write.write(c.getName()+","+c.getX()+","+c.getY()+"\n");
		}
		for(int i=0;i<numc;i++) {
			Vertex a = v.get(i);
			for(Edge e: a.getEdges()) {			
				boolean flag=false;
				for(int k=0;k<added.size();k++) {
					Edge x = added.get(k);
					if((x.getEnd()==e.getEnd()&&x.getStart()==e.getStart())
							||(x.getStart()==e.getEnd()&&x.getEnd()==e.getStart())) {
						flag =true;
						break;
					}	
				}

				if(!flag) {
					added.add(e);
					write.write(a.getData().getName()+","+e.getEnd().getData().getName()+"\n");
				}

			}

		}

		write.close();

	}
	public Pane loadMap() {
		Pane pane = new Pane();
		bs = new Button[C.getCountries().getVertices().size()];
		for(int i=0;i<C.getCountries().getVertices().size();i++) {
			Country cn = C.getCountries().getVertices().get(i).getData();
			bs[i] = new Button();
			bs[i].setPrefSize(10,10);
			bs[i].setShape(new Circle(1));
			bs[i].setLayoutX(cn.getImagex());
			bs[i].setLayoutY(cn.getImagey());
			bs[i].setPadding(Insets.EMPTY);
			bs[i].setPadding(new Insets(1,1,1,1));
			pane.getChildren().add(bs[i]);
		}


		return pane;
	}

	public void handle(ActionEvent event) {
		for(csrh=0;csrh<bs.length;csrh++) {
			if(event.getSource()==bs[csrh]) {

				if(click>1) { // resets source and destination if buttons are clicked more than twice or more
					srcinfo.setText("");
					destinfo.setText("");
					ssrc="";
					sdest="";
					src.valueProperty().set(null);
					dest.valueProperty().set(null);
					click=0;
					if(s!=-1) {
						bs[s].setStyle(null);		
						s=-1;
					}
					if(d!=-1) {
						bs[d].setStyle(null);
						d=-1;
					}
				}
				if(click%2==0) {
					if(s!=-1&&d!=-1) {
						bs[s].setStyle(null);
						bs[d].setStyle(null);
					}
					srcinfo.setText(C.getCountries().getVertices().get(csrh).getData().toString());
					ssrc=C.getCountries().getVertices().get(csrh).getData().getName();		
					bs[csrh].setStyle("-fx-background-color: #0000FF; ");
					
					s=csrh;
					click--;
					src.valueProperty().set(C.getCountries().getVertices().get(csrh).getData().getName());

				}else {
					if(click%2==1) {
						destinfo.setText(C.getCountries().getVertices().get(csrh).getData().toString());
						sdest=C.getCountries().getVertices().get(csrh).getData().getName();	
						bs[csrh].setStyle("-fx-background-color: #FF0000; ");
						d=csrh;
						click--;
						dest.valueProperty().set(C.getCountries().getVertices().get(csrh).getData().getName());
					}

				}	
				click++;
			}

		}

	}
	
	public void handle(MouseEvent event) {
		
	}

	public Pane addLines(Pane p,ArrayList<Country> v) {

		if(v.isEmpty()) {
			return new Pane();
		}else {
			int s = v.size();

			Line[] l = new Line[s];

			for(int i=0;i<s-1;i++) {
				l[i] = new Line();
			}


			for(int i=0;i<s-1;i++) {
				Country c = v.get(i);
				Country c2 = v.get(i+1);

				l[i].setStartX(C.searchCountry(c.getName()).getImagex()+7);
				l[i].setStartY(C.searchCountry(c.getName()).getImagey()+7);

				l[i].setEndX(C.searchCountry(c2.getName()).getImagex()+7);
				l[i].setEndY(C.searchCountry(c2.getName()).getImagey()+7);

//				l[i].setStrokeWidth(3);
				l[i].setStrokeWidth(5);
				p.getChildren().add(l[i]);

			}



		}



		return p;
	}

	public void sortArray(String[] countries) {
		int size=countries.length;
		for(int i = 0; i<size-1; i++)   
		{  
			for (int j = i+1; j<countries.length; j++)   
			{  
				//compares each elements of the array to all the remaining elements  
				if(countries[i].compareTo(countries[j])>0)   
				{  
					//swapping array elements  
					String temp = countries[i];  
					countries[i] = countries[j];  
					countries[j] = temp;  
				}  
			}  
		}  
	}
}