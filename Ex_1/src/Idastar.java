import java.util.Hashtable;
import java.util.Stack;

public class Idastar {

	String moves;
	int id;
	int cost;
	
	public Idastar() {
		moves="";
		id=0;
		cost=0;
	}
	
	public boolean ida_star(State start , State goal) {
		Hashtable<String,State> H = new Hashtable<>();
		Stack<State> L = new Stack<State>();
		int t = 5;//start.set_start_eva_cost();
		while(t<4000) {
			int minf = Integer.MAX_VALUE;   //minf = infinity
			L.add(start);                   //adding start to stack
			H.put(start.toString(), start); //adding start to hash
			while(!L.isEmpty()) {           //while stack is not empty
				State n = new State(L.pop());
				if(n.out == true) {         //n marked as out
					H.remove(n.toString()); //removing n from hash
				}
				else {
					n.out = true;           //mark n as out
					L.add(n);               //add to stack
					n.allowed_operators();  //creating allowed operators
					while(!n.queue.isEmpty()) {
						State g = new State(n.queue.remove());
						if(g.eva_cost>t) {
							minf = Math.min(g.eva_cost, minf);
							if(!n.queue.isEmpty()) {
								g = n.queue.remove();
							}
							continue;
						}
						else if(H.containsKey(g.toString())) {
							System.out.println("hope?");
							if(H.get(g.toString()).out) {
								g = n.queue.remove();
							}if(!(H.get(g.toString()).out)) {
								if(H.get(g.toString()).eva_cost>g.eva_cost) {
									H.remove(g.toString());
									L.remove(g);
									
								}else {
									g = n.queue.remove();
								}
							}
						}if(goal.equals(g)) {
							moves=g.moves;
							id=g.id;
							cost=g.cost;
							return true;
						}
						L.add(g);
						H.put(g.toString(), g);
						
					}
				}
			}
			t = minf;
			System.out.println(t);
		}
		return false;
	}
}
