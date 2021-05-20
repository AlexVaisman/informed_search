import java.util.Hashtable;
import java.util.PriorityQueue;

public class Astar {
	String moves;
	int id;
	int cost;
	
	public Astar() {
		moves="";
		id=0;
		cost=0;
	}
	
	public boolean a_star(State start, State goal) {
		start.set_goal(goal.state);  //saving goal state for heuristic function
		PriorityQueue<State> pq = new PriorityQueue<State>();
		Hashtable<String, State> hash_table = new Hashtable<>();
		Hashtable<String, Integer> prices = new Hashtable<>();
		pq.add(start);
		
		while(!pq.isEmpty()) {
			State child = pq.poll();
			if(goal.equals(child)) {
				moves = child.moves;
				id =child.id;
				cost = child.cost;
				return true;
			}
			hash_table.put(child.toString(), child);
			child.allowed_operators(); //generating children
			while(!child.queue.isEmpty()) {//while there are children
				State op = child.queue.remove();
				if(!(hash_table.containsKey(op.toString())) && !(pq.contains(op))  ) {
					pq.add(op);
					prices.put(op.toString() , op.eva_cost); // saving prices to hash table
				}else if (pq.contains(op)) {//needs work
					int old = prices.get(op.toString());
					if(old>op.eva_cost) {
						prices.remove(op.toString());
						pq.remove(op);
					}
				}
			}
		}
		return false;
	}
	
}
