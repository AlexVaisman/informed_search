import java.util.Hashtable;



public class Dfid {
	String moves;
	int id;
	int cost;
	
	public Dfid() {
		moves="";
		id=0;
		cost=0;
	}
	
	public boolean dfid(State start ,State goal) {
		int depth = 1;
		while(depth<300) {
			//reseting root node variable
			start.number_of_zeros = 0;
			//initializing hash map and calling limited dfs
			Hashtable<String, State> hash_table = new Hashtable<>();
			String result = limited_dfs(start, goal, depth, hash_table);
			if(!result.equals("cutoff")){
				System.out.println(result+" depth: "+depth);
				return false;
			}
			depth++;
		}
		return false;
	}

	private String limited_dfs(State node, State goal, int limit, Hashtable<String, State> hash_table) {
		//if goal is found save the moves ,cost and how many nodes it took
		if(goal.equals(node)) {
			moves = node.moves;
			id = node.id;
			cost = node.cost;
			return "found";
		//limit reached zero return cutoff
		}else if(limit == 0) {
			return "cutoff";
		}else {
			//adding node to hash map and finding all creating all allowed nodes.
			hash_table.put(node.toString(), node);
			boolean iscutoff = false;
			node.allowed_operators();
			while(!node.queue.isEmpty()) { //while there is operators to work on.
				State child_node = (node.queue.remove());
				if((hash_table.containsKey(child_node.toString())) && !node.queue.isEmpty()){ //if the node already in hash map take next one
					 child_node = node.queue.remove();
				}
				String result = limited_dfs(child_node,goal,limit-1,hash_table);  //calling limited dfs with limit-1
				if(result.equals("cutoff")) {
					iscutoff = true;
				}else if(!result.equals("fail")) {
					return result;
				}
			}
			hash_table.remove(node.toString(),node);
			if(iscutoff) {
				return "cutoff";
			}else {
				return "fail";
			}
		}
	}
	
	
}
