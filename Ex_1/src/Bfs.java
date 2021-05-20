import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
public class Bfs {
	
	String moves;
	int id;
	int cost;
	
	public Bfs() {
		moves="";
		id=0;
		cost=0;
	}
	
	public int bfs(State start , State goal) {
		//initializing queue and hash table, adding first node to queue
		Queue<State> queue = new LinkedList<>();                     
		Hashtable<String, State> hash_table = new Hashtable<>();
		queue.add(start);
		
		while(!queue.isEmpty()) {
			
			//while queue is not empty, taking out node from queue , adding it to hash
			//table and creating all allowed operators on the node
			State current_node = queue.remove();
			hash_table.put(current_node.toString(), current_node);
			current_node.allowed_operators();
			while(!current_node.queue.isEmpty()) { //while there is operators to work on.
				//getting the node that was created by an operator
				//checking that the node is not in hash table and queue,
				//if its not checking it it is equal to goal ,if it is Bfs stops
				//if its not adding node to queue
				State child_node = current_node.queue.remove();
				if(!(hash_table.containsKey(child_node.toString())) && !(queue.contains(child_node))  ) {
					if(goal.equals(child_node)) {
						moves = child_node.moves;
						id = child_node.id;
						cost =child_node.cost;
						return 0;
					}else {
						queue.add(child_node);
					}
				}
			}
		}
		return 0;
	}
	


}
