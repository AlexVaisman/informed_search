
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.plaf.SliderUI;


public class Ex1 {
	
	private static String algo_name;
	private static Boolean time;
	private static Boolean open;
	private static int [][] start_pos; //[row][column]
	private static int [][] goal_pos;  //[row][column]
	
	

	
	
	// This function reads the input file and initialize  the Ex1 class variables.
	public static void read_file() {
		String file = "src\\input.txt";
		BufferedReader read = null;
		String line = "";
		int counter = 0;
		int row_read = 0;
		Boolean reading_start = false;
		Boolean reading_goal = false;
		try {
			read = new BufferedReader(new FileReader(file));
			while((line = read.readLine())!= null) {
				if(line.equals("Goal state:")) { // Switching flags to read goal state
					reading_start = false;
					reading_goal = true;
					row_read = 0;
				}
				else if(counter == 0) {
					algo_name = line;
				}
				else if(counter == 1) {
					if(line.equals("with time")) {
						time = true;
					}
					else {
						time = false;
					}
				}else if(counter == 2){
					if(line.equals("no open")) {
						open = false;
					}else {
						open = true;
					}
				}
				else if(counter == 3) { // reading matrix size
					String [] matrix_size = line.split("x");
					int row = Integer.parseInt(matrix_size[0]);
					int column = Integer.parseInt(matrix_size[1]);
					start_pos = new int[row][column];
					goal_pos = new int[row][column];
					reading_start = true;
				}
				else if(reading_start == true) { //reading start state
					String [] input_row = line.split(",");
					for(int i = 0 ; i<input_row.length;i++) {
						if(input_row[i].equals("_")) {
							start_pos[row_read][i] = 0;
						}else {
							start_pos[row_read][i] = Integer.parseInt(input_row[i]);
						}
					}
					row_read++;
				}else if(reading_goal == true) { // reading goal state
					String [] input_row = line.split(",");
					for(int i = 0 ; i<input_row.length;i++) {
						if(input_row[i].equals("_")) {
							goal_pos[row_read][i] = 0;
						}else {
							goal_pos[row_read][i] = Integer.parseInt(input_row[i]);
						}
					}
					row_read++;
				}
				counter++;
			}
		}catch (Exception e) {
			System.out.println("Bad input check file");
		}
	}
	
	

	public static void write_file(String moves, int cost, int id, long runtime)  {
		File file = new File("src\\output.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(moves);
			pw.println("Num: "+id);
			pw.println("Cost: "+cost);
			if(time) {
				pw.println(runtime*0.001+" seconds");
			}
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		read_file();
		Bfs b =  new Bfs();
		Dfid d = new Dfid();
		Astar a = new Astar();
		String moves ="";
		int id = 0;
		int cost = 0;
		long runtime = 0;
		State start = new State(start_pos);
		State goal = new State(goal_pos);
		
		
		if(algo_name.equals("BFS")) {
			long time_s = System.currentTimeMillis();
			b.bfs(start, goal);
			long time_f = System.currentTimeMillis();
			runtime = (time_f-time_s)/1000;
			moves = b.moves;
			id = b.id;
		    cost = b.cost;
		}
		if(algo_name.equals("DFID")) {
			long time_s = System.currentTimeMillis();
			d.dfid(start, goal);
			long time_f = System.currentTimeMillis();
			runtime = (time_f-time_s);
			moves = d.moves;
			id = d.id;
		    cost = d.cost;
		}
		if(algo_name.equals("A*")) {
			long time_s = System.currentTimeMillis();
			a.a_star(start, goal);
			long time_f = System.currentTimeMillis();
			runtime = (time_f-time_s);
			moves = a.moves;
			id = a.id;
		    cost = a.cost;
		}
		if(algo_name.equals("IDA*")) {
			
		}
		if(algo_name.equals("DFBnB")) {
			
		}
		
		write_file(moves,cost,id,runtime);
		
		

	}

}
