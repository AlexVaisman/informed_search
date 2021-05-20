import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dfbnb {

	String moves;
	int id;
	int cost;
	
	public Dfbnb() {
		moves="";
		id=0;
		cost=0;
	}
	
	public boolean dfbnb_algo(State start , State goal) {
//		Hashtable<String,State> H = new Hashtable<>();
//		Stack<State> L = new Stack<State>();
//		L.add(start);
//		int t = 1000;
//		while(!L.isEmpty()) {
//			State n = new State(L.pop());
//			if(n.out == true) {
//				H.remove(n.toString());
//			}else {
//				n.out = true;
//				H.put(n.toString(), n);
//				n.allowed_operators();
//				PriorityQueue<State> N = new PriorityQueue<State>();
//				while(!n.queue.isEmpty()) {
//					N.add(n.queue.poll());
//				}
//				while(!N.isEmpty()) {
//					State g = N.peek();
//					if(g.eva_cost>=t) {
//						N.remove(g);
//					}else if(H.containsKey(g.toString())) {
//						if(H.get(g.toString()).out) {
//							N.remove(g);
//						}
//					}else if(H.containsKey(g.toString())) {
//						if(!(H.get(g.toString()).out)) {
//							if(H.get(g.toString()).eva_cost<=g.eva_cost) {
//								N.remove(g);
//							}else {
//								H.remove(g.toString());
//								L.remove(g);
//								}
//							}
//					}else if(goal.equals(g)) {
//						t = g.cost;
//						moves=g.moves;
//						id=g.id;
//						cost=g.cost;
//						N.remove(g);
//					}
//				}
//				while(!N.isEmpty()) {
//					State temp = N.poll();
//					L.add(temp);
//					H.put(temp.toString(), temp);
//				}
//			}
//			
//		}
		return false;
	}
}
