package com.av.demosnakeladder;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DemoSnakeLadder {

	public static void main(String[] args) {
		
		Integer board[] = new Integer[101];
		/*
		* This is the board and I started from 1, since it will make my work easier in further calls.
		* No need to call -1 in every address manipulation
		*/
		for(int i=1; i<100; i++){
			board[i] = new Integer(i);
		}
		
		
		List<GroupInfo> laddersInfo = new ArrayList<GroupInfo>();
			laddersInfo.add(new GroupInfo(board[2], board[32]));
			laddersInfo.add(new GroupInfo(board[14], board[57]));
			laddersInfo.add(new GroupInfo(board[27], board[78]));
			laddersInfo.add(new GroupInfo(board[34], board[44]));
			laddersInfo.add(new GroupInfo(board[7], board[98]));
		//Ladder Configuration
		/*
		* This is the info of ladders. This means from where to where ladder exists.
		*/
		DirectedGraph<Integer, DefaultEdge> ladders = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
		/*
		 * Ladders are at 
		 * 2 -> 32
		 * 14 -> 57
		 * 27 -> 78
		 * 34 -> 44
		 * 7 -> 98
		 */
		for(GroupInfo gi : laddersInfo){
			ladders.addVertex(gi.getStartIndex());
			ladders.addVertex(gi.getEndIndex());
			ladders.addEdge(gi.getStartIndex(), gi.getEndIndex());
		}
		
		
		List<GroupInfo> snakesInfo = new ArrayList<GroupInfo>();
			snakesInfo.add(new GroupInfo(board[99], board[10]));
			snakesInfo.add(new GroupInfo(board[77], board[22]));
			snakesInfo.add(new GroupInfo(board[62], board[13]));
			snakesInfo.add(new GroupInfo(board[54], board[8]));
			snakesInfo.add(new GroupInfo(board[42], board[3]));
		
		//Snake Configuration
		/*
		* This is the info of snakes. This means from where to where snake exists.
		*/
		DirectedGraph<Integer, DefaultEdge> snakes = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
		/*
		 * Snakes are at
		 * 99 -> 10
		 * 77 -> 22
		 * 62 -> 13
		 * 54 -> 8
		 * 42 -> 3
		 */
		for(GroupInfo gi : snakesInfo){
			snakes.addVertex(gi.getStartIndex());
			snakes.addVertex(gi.getEndIndex());
			snakes.addEdge(gi.getStartIndex(), gi.getEndIndex());
		}
		
		/*
		 * These are the current positions of the player, I am considering 1 player.
		 */
		int player1 = 1;
		
		//The game goes till player reaches 100
		while(player1  != 100){
			//get the value of dice and 
			int nextValue = getNextDiceValue();
			System.out.println("Player is in " + player1 + " and got " + nextValue + " so next position is " + (player1 + nextValue));
			
			if((player1 + nextValue) <= 100){
				
				if((player1 + nextValue) == 100){
					System.out.println("!!!Congratulations you won!!! ");
					System.out.println("Bye, have a nice day");
					break;
				}
				
				//Now this is the current position of the player
				player1 += nextValue;
				
				Integer boardCurrentValue = board[player1];
				
				if(ladders.containsVertex(boardCurrentValue)){
					boardCurrentValue = ladders.getEdgeTarget(ladders.edgesOf(boardCurrentValue).iterator().next());
					System.out.println("Congratulations, you got ladder to " + boardCurrentValue);
					player1 = boardCurrentValue;
				}else if(ladders.containsVertex(boardCurrentValue)){
					boardCurrentValue = snakes.getEdgeTarget(snakes.edgesOf(boardCurrentValue).iterator().next());
					System.out.println("Oops, you get stung by snake and now you came to " + boardCurrentValue);
					player1 = boardCurrentValue;
				}
			}
		}
	}
	
	
	
	private static int getNextDiceValue(){
		//here randomize the dice for value between 1 & 6 and return the value that comes back
		
		int result = 0;
		
		while(result == 0){
			result = (int)(Math.random() * 10);
			
			if(result > 6){
				result -= 6;
			}
		}
		return result;
	}
}

class GroupInfo{
	private Integer startIndex;
	private Integer endIndex;

	public GroupInfo(Integer startIndex, Integer endIndex) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
}
