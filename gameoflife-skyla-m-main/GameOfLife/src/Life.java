import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

/**

	Represents a Game Of Life grid.

	Coded by: Skyla Ma
	Modified on: 4/4/2021

*/

public class Life {

	// Add a 2D array field to represent the Game Of Life grid.
	private boolean[][] grid;
	private boolean [][] newGrid;
	private boolean [][] newGrid2;
	private static int currentStep = 0;
	
	
	/**
	 * Initialized the Game of Life grid to an empty 20x20 grid.
	 */
	public Life() {
		//grid = new boolean [20][20];
		newGrid = new boolean [20][20];
		newGrid2 = new boolean [20][20];
		grid = newGrid;
		currentStep = 0;
	}

	
	
	/**
	 * Initializes the Game of Life grid to a 20x20 grid and fills it with data from the file given.
	 * 
	 * @param filename The path to the text file.
	 */
	public Life(String filename) {
		//grid = new boolean[20][20];
		newGrid = new boolean[20][20];
		newGrid2 = new boolean[20][20];
		this.readData(filename, newGrid);
		grid = newGrid;
		currentStep = 0;
		
	}

	private void doStep() {
		boolean[][]from = null;
		boolean[][]to = null;
		if (currentStep%2 == 0) {
			from = newGrid;
			to = newGrid2;
		} else {
			to = newGrid;
			from = newGrid2;	
			
		}
		grid = to;
		for(int r=0; r<from.length; r++) {
			for(int c=0; c<from[0].length; c++) {
				int count = doCountNeighbors (r,c, from);
				if(count==3) {
					to[r][c] = true;
				}
				else if(count<=1 || count>=4) {
					to[r][c] = false;
				}
				else {
					to[r][c] = from[r][c];
				}
			} 
			System.out.println();
		}		
	}
	
	/**
	 * Runs a single step within the Game of Life by applying the Game of Life rules on
	 * the grid.
	 */
	
	public void step() {
		doStep();
		currentStep++;
	}
	
	/*public void step() {
		int count = 0;
		for(int i=0; i<grid.length;i++) {
			for(int j=0; j<grid[0].length; j++) {
				newGrid[i][j] = grid[i][j];
			}
		}
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[0].length; c++) {
				count = countNeighbors (r,c);
				if(count==3) {
					newGrid[r][c] = true;
				}
				else if(count<=1 || count>=4) {
					newGrid[r][c] = false;
				} 
			}
			System.out.println();
		}
		for(int i=0; i<grid.length;i++) {
			for(int j=0; j<grid[0].length; j++) {
				grid[i][j] = newGrid[i][j];
			}
		}
	}
	
	
	public int countNeighbors(int r, int c) {
		int count = 0;
		for(int i=r-1; i<=r+1; i++) {
			for(int j=c-1; j<=c+1; j++) {
				if(i>=0 && i<grid.length && j>=0 && j<grid[0].length && !(i==r && j==c) && grid[i][j]) {
					count++;
				}
			}
		}
		return count;
	}*/

	public int doCountNeighbors(int r, int c, boolean[][] from) {
		int count = 0;
		for(int i=r-1; i<=r+1; i++) {
			for(int j=c-1; j<=c+1; j++) {
				if(i>=0 && i<from.length && j>=0 && j<from[0].length && !(i==r && j==c) && from[i][j]) {
					count++;
				}
			}
		}
		return count;
	}	
	
	/**
	 * Runs n steps within the Game of Life.
	 * @param n The number of steps to run.
	 */
	public void step(int n) {
		for(int i=0; i<n; i++) {
			step();
		}
	}
	
	
	
	/**
	 * Formats this Life grid as a String to be printed (one call to this method returns the whole multi-line grid)
	 * 
	 * @return The grid formatted as a String.
	 */
	public String toString() {
		String output = "";
		for(int r=0; r<grid.length; r++) {
			for(int c=0; c<grid[0].length; c++) {
				if(grid[r][c]) {
					output+="*";
				}
				else {
					output+="-";
				}
			}
			output+="\n";
		}
		return output;
	}
	
	
	
	/**
	 * (Graphical UI)
	 * Draws the grid on a PApplet.
	 * The specific way the grid is depicted is up to the coder.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 */
	public void draw(PApplet marker, float x, float y, float width, float height) {
		marker.noFill();
		
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[0].length; j++) {
				float rectWidth = width / grid[0].length;
				float rectHeight = height / grid.length;
				float rectX = x + j*rectWidth;
				float rectY = y + i*rectHeight;
				if(grid[i][j]) {
					marker.fill(255, 255, 0);
				}
				
				if(!(grid[i][j])) {
					marker.noFill();
				}
				marker.rect(rectX, rectY, rectWidth, rectHeight);
			}
		}
	}
	
	
	
	/**
	 * (Graphical UI)
	 * Determines which element of the grid matches with a particular pixel coordinate.
	 * This supports interaction with the grid using mouse clicks in the window.
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the game of life grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		float rectWidth = width / grid[0].length;
		float rectHeight = height / grid.length;
		
		float i = (p.y - y) / rectHeight;
		float j = (p.x - x) / rectWidth;
		
		Point coordinates = new Point((int)i,(int)j);
		//System.out.println(i);
		//System.out.println(j);
		if((int)i < 0 || (int)i >= grid[0].length || (int)j < 0 || (int)j >= grid.length) {
			return null;
		}
		return coordinates;
	}
	
	/**
	 * (Graphical UI)
	 * Toggles a cell in the game of life grid between alive and dead.
	 * This allows the user to modify the grid.
	 * 
	 * @param i The x coordinate of the cell in the grid.
	 * @param j The y coordinate of the cell in the grid.
	 */
	public void toggleCell(int i, int j) {
		//One line of code that swaps the value of the cell at i,j in the grid.
		if(grid[i][j]) {
			grid[i][j]=false;
		}
		else {
			grid[i][j]=true;
		}
	}

	

	// Reads in array data from a simple text file containing asterisks (*)
	public void readData (String filename, boolean[][] gameData) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
				reader = new FileReader(dataFile);
				in = new Scanner(reader);

				while (in.hasNext()) {
					String line = in.nextLine();
					for(int i = 0; i < line.length(); i++)
						if (count < gameData.length && i < gameData[count].length && line.charAt(i)=='*')
							gameData[count][i] = true;

					count++;
				}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}

		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
	}



	
	
}