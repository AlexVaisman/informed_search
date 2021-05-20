import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class State implements Comparable<State> {
	
	int[][] state;
	int number_of_zeros;
	int operators_num;
	static int id = 0;
	String moves;
	Point first_zero;
	Point second_zero;
	Queue<State> queue;
	String last_move = "";
	int cost;
	int eva_cost;
	static int[][] goal;
	
	



	
	//constructor
	public State(int[][] data){
		state = new int[0][0];
		goal = new int[0][0];
		moves ="";
		this.state =  data;
		this.queue = new LinkedList<>();
		this.number_of_zeros = 0;
		this.operators_num = 0;
		first_zero = new Point(-1,-1);
		second_zero = new Point(-1,-1);
		cost = 0;
		eva_cost = 0;
	}
	//copy constructor
	public State(State copy) {		
		queue = new LinkedList<>();
		number_of_zeros = 0;
		operators_num = 0;
		first_zero = new Point(-1,-1);
		second_zero = new Point(-1,-1);
		state = new int[copy.state.length][copy.state[0].length];
		cost += copy.cost;
		//copying matrix
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				state[i][j] = copy.state[i][j];
			}
		}
		//saving moves
		if(copy.moves =="") {
			moves = copy.moves;
		}else {
			moves = copy.moves+"-";
		}
		//saving the last move
		last_move = copy.last_move;
		//number of states made
		id++;
		//evaluation cost
		eva_cost = eva_cost + cost;
	}


	public int allowed_operators() {
		operators_num = 0;
		number_of_zeros = 0;
		//counting number of zeros, and saving there location.
		for(int i = 0 ;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				if(state[i][j] == 0) {
					if(number_of_zeros == 0) {
						first_zero.x = i;
						first_zero.y = j;
					}else if(number_of_zeros == 1) {
						second_zero.x = i;
						second_zero.y = j;
					}
					number_of_zeros++;
				}
			}	
		}
		//if there is 1 zero , will make a queue of allowed operators with 1 zero.
		//if there is 2 zeros' will make a queue with special cases of 2 moves at a time.
		if(number_of_zeros == 1) {
			one_zero_operators(first_zero);
		}else if(number_of_zeros == 2) {
			two_zero_operators();
		}
		return operators_num;	
	}

	private void one_zero_operators(Point zero) {
	int row = state.length-1;
	int column = state[0].length-1;
	String oposite_move;
	//checking what moves are possible
	//for each of the moves checking if the zero is on the edge , and checking if the next
	//move is not an opposite to the one before it 
	//example moving left then moving the same number right
	if(zero.y != column ) {//not at the right edge of the board, there is an element to the right of zero.
		oposite_move = "R"+this.state[zero.x][zero.y+1];
		if(!this.last_move.equals(oposite_move) && (this.state[zero.x][zero.y+1] != 0) && !this.last_move.equals("RR")) {
			left_one(zero.x,zero.y,"L");
		}

	}
	if(zero.x != row ) {   //not at the bottom edge of the board, there is an element below zero.
		oposite_move = "D"+this.state[zero.x+1][zero.y];
		if(!this.last_move.equals(oposite_move) && (this.state[zero.x+1][zero.y] != 0) && !this.last_move.equals("DD")) {
			up_one(zero.x,zero.y,"U");
		}

	}
	if(zero.y != 0 ) {     //not at the left edge of the board, there is an element to the left of zero.
		oposite_move = "L"+this.state[zero.x][zero.y-1];
		if(!this.last_move.equals(oposite_move) && (this.state[zero.x][zero.y-1] != 0 && !this.last_move.equals("LL"))) {
			right_one(zero.x,zero.y,"R");
		}

	}
	if(zero.x != 0) {     //not at the top edge of the board,there is an element above zero.
		oposite_move = "U"+this.state[zero.x-1][zero.y];
		if(!this.last_move.equals(oposite_move) && (this.state[zero.x-1][zero.y] != 0 && !this.last_move.equals("UU"))) {
			down_one(zero.x,zero.y,"D");
		}
	}
}

	
	
	//this function checks what moves can be made with two zeros and sends them in the correct order
	//to the functions that will create the child states.
	private void two_zero_operators() {
		int row = state.length-1;
		int column = state[0].length-1;

		if(first_zero.x == second_zero.x) {
			if(Math.abs(first_zero.y-second_zero.y) == 1) {//zeros are horizontal and near each other
				if(first_zero.x != row && !this.last_move.equals("DD")) {//checking if double zeros not at bottom edge of the board, and previous move was not 2down
					up_two();
				}
				if(first_zero.x != 0 && !this.last_move.equals("UU")) { //checking if double zeros not at top edge of the board, and previous move was not 2up
					down_two();
				}	
			}
		}
		if(first_zero.y == second_zero.y) {
			if(Math.abs(first_zero.x-second_zero.x) == 1) {//zeros are vertical and near each other
				if(first_zero.y != column && !this.last_move.equals("RR")){//checking if double zeros not at right edge of the board, and previous move was not 2right
					left_two();
				}
				if(first_zero.y != 0 && !this.last_move.equals("LL")) {//checking if double zeros not at left edge of the board, and previous move was not 2left
					right_two();
				}
			}
		}
		
		if(first_zero.x<second_zero.x) {//first zero is higher
			one_zero_operators(first_zero);
			one_zero_operators(second_zero);
		}
		if(first_zero.x>second_zero.x) {//second zero is higher
			one_zero_operators(second_zero);
			one_zero_operators(first_zero);
		}
		
		if(first_zero.x == second_zero.x) {//both zeros at same level
			if(first_zero.y<second_zero.y) {//first zero is left most
				one_zero_operators(first_zero);
				one_zero_operators(second_zero);
			}
			if(first_zero.y>second_zero.y) {//second zero is left most
				one_zero_operators(second_zero);
				one_zero_operators(first_zero);
			}
		}
	
		
	}
	//creates a new state with 1 left move
	//updates queue and number of operations
	private void left_one(int x, int y,String movment) {
		
		State child = new State(this);
		child.moves += child.state[x][y+1]+"L";
		child.state[x][y] =child.state[x][y+1];
		child.state[x][y+1] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = movment + child.state[x][y];;
		child.cost = child.cost +5;
		child.manhattan();
	}
	//creates a new state with 1 right move
	//updates queue and number of operations
	private void right_one(int x, int y,String movment) {
		State child = new State(this);
		child.moves += child.state[x][y-1]+"R";
		child.state[x][y] =child.state[x][y-1];
		child.state[x][y-1] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = movment + child.state[x][y];;
		child.cost = child.cost +5;
		child.manhattan();
	}
	
	//creates a new state with 1 up move
	//updates queue and number of operations
	private void up_one(int x, int y,String movment) {
		State child = new State(this);
		child.moves += child.state[x+1][y]+"U";		
		child.state[x][y] =child.state[x+1][y];
		child.state[x+1][y] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = movment + child.state[x][y];
		child.cost = child.cost +5;
		child.manhattan();
	}
	//creates a new state with 1 down move
	//updates queue and number of operations
	private void down_one(int x, int y,String movment) {
		State child = new State(this);
		child.moves += child.state[x-1][y]+"D";			
		child.state[x][y] =child.state[x-1][y];
		child.state[x-1][y] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = movment + child.state[x][y];
		child.cost = child.cost +5;
		child.manhattan();
	}
	
	private void left_two() {
		State child = new State(this);
		//saving moves
		child.moves += child.state[first_zero.x][first_zero.y+1]+"&";
		child.moves += child.state[second_zero.x][second_zero.y+1]+"L";
		//moving first zero
		child.state[first_zero.x][first_zero.y] =child.state[first_zero.x][first_zero.y+1];
		child.state[first_zero.x][first_zero.y+1] = 0;
		//moving second zero
		child.state[second_zero.x][second_zero.y] =child.state[second_zero.x][second_zero.y+1];
		child.state[second_zero.x][second_zero.y+1] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = "LL";
		child.cost = child.cost +6;
		child.manhattan2();

	}
	
	private void right_two() {
		State child = new State(this);
		//saving moves
		child.moves += child.state[first_zero.x][first_zero.y-1]+"&";
		child.moves += child.state[second_zero.x][second_zero.y-1]+"R";
		//moving first zero
		child.state[first_zero.x][first_zero.y] =child.state[first_zero.x][first_zero.y-1];
		child.state[first_zero.x][first_zero.y-1] = 0;
		//moving second zero
		child.state[second_zero.x][second_zero.y] =child.state[second_zero.x][second_zero.y-1];
		child.state[second_zero.x][second_zero.y-1] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = "RR";
		child.cost = child.cost +6;
		child.manhattan2();
	}
	
	private void up_two() {
		State child = new State(this);
		//saving moves
		child.moves += child.state[first_zero.x+1][first_zero.y]+"&";
		child.moves += child.state[second_zero.x+1][second_zero.y]+"U";
		//moving first zero
		child.state[first_zero.x][first_zero.y] =child.state[first_zero.x+1][first_zero.y];
		child.state[first_zero.x+1][first_zero.y] = 0;
		//moving second zero
		child.state[second_zero.x][second_zero.y] =child.state[second_zero.x+1][second_zero.y];
		child.state[second_zero.x+1][second_zero.y] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = "UU";
		child.cost = child.cost +7;
		child.manhattan3();
		
	}
	
	private void down_two() {
		State child = new State(this);
		//saving moves
		child.moves += child.state[first_zero.x-1][first_zero.y]+"&";
		child.moves += child.state[second_zero.x-1][second_zero.y]+"D";
		//moving first zero
		child.state[first_zero.x][first_zero.y] =child.state[first_zero.x-1][first_zero.y];
		child.state[first_zero.x-1][first_zero.y] = 0;
		//moving second zero
		child.state[second_zero.x][second_zero.y] =child.state[second_zero.x-1][second_zero.y];
		child.state[second_zero.x-1][second_zero.y] = 0;
		queue.add(child);
		operators_num++;
		//saving move and cost
		child.last_move = "DD";
		child.cost = child.cost +7;
		child.manhattan3();

	}

	//this function calculates the Manhattan distance 
	//of the number to move.
	//the goal is received as an input from file
	//its being saved as a static class member
	private void manhattan() {
		int target;
		int moves = 0;
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				target = state[i][j];
				if(target != 0) {
					moves += find_distance(target , i,j);
					}
				}
			}
		this.eva_cost +=  moves*5;
	}
	//special case for double movement left, or right
	private void manhattan2() {
		int target;
		int moves = 0;
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				target = state[i][j];
				if(target != 0) {
					moves += find_distance(target , i,j);
					}
				}
			}
		this.eva_cost +=  (moves-4)*5;
	}
	//special case for double movement up, or down
	private void manhattan3() {
		int target;
		int moves = 0;
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				target = state[i][j];
				if(target != 0) {
					moves += find_distance(target , i,j);
					}
				}
			}
		this.eva_cost += (moves-3)*5;
	}
	
	
	
	private int find_distance(int target , int x_target , int y_target) {
		for(int i = 0;i<goal.length;i++) {
			for(int j = 0;j<goal[0].length;j++) {
				if(goal[i][j] == target) {
					return (Math.abs(x_target-i)+Math.abs(y_target-j));
				}
			}
		}
		return 0;
	}
	public void set_goal(int [][] target) {
		eva_cost = 0;
		cost = 0;
		goal = new int[state.length][state[0].length];
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				goal[i][j] = target[i][j];
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 0;i<state.length;i++) {
			for(int j = 0;j<state[0].length;j++) {
				s=s+this.state[i][j]+", ";
			}
			s=s+"\n";
		}
		return s;
	}
	//this function compares the states of the nodes.
	 @Override
	 public boolean equals(Object o) {
		State check = (State)o;
		boolean flag = true;
		for(int i = 0;i<this.state.length;i++) {
			for(int j = 0;j<this.state[0].length;j++) {
				if(this.state[i][j] != check.state[i][j]) {
					flag = false;
				}
			}
		}
		return flag;
	}
	 //his function compare the eve_cost (evaluation cost) of this node
	@Override
	public int compareTo(State o) {
		if(this.eva_cost == o.eva_cost) {
			return 0;
		}else if(this.eva_cost>o.eva_cost) {
			return 1;
		}else {
			return -1;
		}
	}

}
