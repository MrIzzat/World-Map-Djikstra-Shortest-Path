public class QueueObject implements Comparable <QueueObject> {


	public Vertex vertex;
	public double priority;

	public QueueObject(Vertex v, double p){
		this.vertex = v;
		this.priority = p;
	}
	public int compareTo(QueueObject o) {
		if (this. priority == o.priority) {
			return 0;
		}else {
			if (this .priority > o.priority) {
				return 1;
			}
			else {
				return-1;
			}
		}

	}
}





