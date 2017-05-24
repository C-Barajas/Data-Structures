/* Christian Barajas
   masc0319
*/

import data_structures.*;

public class MazeSolver {
	private MazeGrid maze;
	private Stack<GridCell> stck;
	private Queue<GridCell> que;
	private int dimension;
	
	public static void main(String[] args) {
		new MazeSolver(25);
	}
	public MazeSolver(int dimension){
		maze = new MazeGrid(this, dimension);
		stck = new Stack<GridCell>();
		que = new Queue<GridCell>();
		this.dimension = dimension;
	}
	
	//The constructor. Takes a single argument, the number of rows and columns in the grid. Suggested values are 25 .. 50.
	public void mark(){
		GridCell startCell = maze.getCell(0, 0);
		startCell.setDistance(0);
		que.enqueue(maze.getCell(0,0));
		maze.markDistance(startCell);
		
		while(!que.isEmpty()){
			GridCell cell = que.dequeue();
			GridCell rightCell = (maze.getCell(cell.getX()+1,cell.getY()));
			GridCell lowerCell = (maze.getCell(cell.getX(),cell.getY()+1));
			GridCell leftCell = (maze.getCell(cell.getX()-1,cell.getY()));
			GridCell upperCell = (maze.getCell(cell.getX(),cell.getY()-1));
			int distance = cell.getDistance();
			
			if(maze.isValidMove(rightCell) && !rightCell.wasVisited()){
				rightCell.setDistance(distance+1);
				maze.markDistance(rightCell);
				que.enqueue(rightCell);
			}
			if(maze.isValidMove(lowerCell) && !lowerCell.wasVisited()){
				lowerCell.setDistance(distance+1);
				maze.markDistance(lowerCell);
				que.enqueue(lowerCell);
			}
			if(maze.isValidMove(leftCell) && !leftCell.wasVisited()){
				leftCell.setDistance(distance+1);
				maze.markDistance(leftCell);
				que.enqueue(leftCell);
			}
			if(maze.isValidMove(upperCell) && !upperCell.wasVisited()){
				upperCell.setDistance(distance+1);
				maze.markDistance(upperCell);
				que.enqueue(upperCell);
			}
		}
	}
	//This method runs the breadth first traversal, and marks each reachable cell in the grid with its distance from the origin.
	public boolean move(){
		GridCell terminalCell = maze.getCell(dimension-1, dimension-1);
		int distance = terminalCell.getDistance();
		if(distance == -1)
			return false;
		
		stck.push(terminalCell);
		while(distance != 0){
			GridCell cell = stck.peek();
			GridCell rightCell = (maze.getCell(cell.getX()+1,cell.getY()));
			GridCell lowerCell = (maze.getCell(cell.getX(),cell.getY()+1));
			GridCell leftCell = (maze.getCell(cell.getX()-1,cell.getY()));
			GridCell upperCell = (maze.getCell(cell.getX(),cell.getY()-1));
			int cellDistance = cell.getDistance();
			
			if(maze.isValidMove(rightCell) && (rightCell.getDistance() < distance)){
				stck.push(rightCell);
				distance = rightCell.getDistance();
				continue;
			}
			if(maze.isValidMove(lowerCell) && (lowerCell.getDistance() < distance)){
				stck.push(lowerCell);
				distance = lowerCell.getDistance();
				continue;
			}
			if(maze.isValidMove(leftCell) && (leftCell.getDistance() < distance)){
				stck.push(leftCell);
				distance = leftCell.getDistance();
				continue;
			}
			if(maze.isValidMove(upperCell) && (upperCell.getDistance() < distance)){
				stck.push(upperCell);
				distance = upperCell.getDistance();
				continue;
			}
		}
		while(!stck.isEmpty()){
			GridCell cell = stck.pop();
			maze.markMove(cell);
		}
		return true;
	}
	//Does part two, indicates in the GUI the shortest path found.
	public void reset(){
		stck.makeEmpty();
		que.makeEmpty();
	}
	//Reinitializes the puzzle. Clears the stack and queue (calls makeEmpty() ).
}
