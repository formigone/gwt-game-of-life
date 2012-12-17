package com.formigone.life.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Grid {
	private List<Organism> cells;
	private int width;
	private int height;
	
	public Grid(int width, int height, int cellWidth, int cellHeight) {
		this.width = width;
		this.height = height;
		
		cells = new ArrayList<Organism>(width * height);
		for (int i = 0; i < width * height; i++)
			cells.add(organismFactory(i, cellWidth, cellHeight));
	}
	
	private Organism organismFactory(int i, int width, int height) {
		return organismFactory(i, width, height, false);
	}
	
	private Organism organismFactory(int i, int width, int height, boolean life) {
		return new Organism(life, i % this.width, i / this.height, width, height);
	}
	
	public int getNeighborCount(int i) {
		int total = 0;
		Organism org = getCell(i);
		int x = org.getX();
		int y = org.getY();

		// Top left
		if (getCell(x - 1, y - 1) != null)
			total += (getCell(x - 1, y - 1).isAlive() ? 1 : 0);

		// Top center
		if (getCell(x, y - 1) != null)
			total += (getCell(x, y - 1).isAlive() ? 1 : 0);
		
		// Top right
		if (getCell(x + 1, y - 1) != null)
			total += (getCell(x + 1, y - 1).isAlive() ? 1 : 0);
		
		// Left
		if (getCell(x - 1, y) != null)
			total += (getCell(x - 1, y).isAlive() ? 1 : 0);
		
		// Right
		if (getCell(x + 1, y) != null)
			total += (getCell(x + 1, y).isAlive() ? 1 : 0);
		
		// Bottom left
		if (getCell(x - 1, y + 1) != null)
			total += (getCell(x - 1, y + 1).isAlive() ? 1 : 0);
		
		// Bottom center
		if (getCell(x, y + 1) != null)
			total += (getCell(x, y + 1).isAlive() ? 1 : 0);
		
		// Bottom right
		if (getCell(x + 1, y + 1) != null)
			total += (getCell(x + 1, y + 1).isAlive() ? 1 : 0);
		
		return total;
	}
	
	public void nextGeneration() {
		List<Organism> nextGen = new ArrayList<Organism>(width * height);
		int cellWidth = cells.get(0).getWidth();
		int cellHeight = cells.get(0).getHeight();
		int neighbors;
		boolean isAlive;
		
		for (int i = 0; i < width * height; i++) {
			neighbors = 0;
			neighbors = getNeighborCount(i);
			isAlive = cells.get(i).isAlive();
			
			// Any live cell with fewer than two live neighbors dies
			if (isAlive && neighbors < 2)
				nextGen.add(organismFactory(i, cellWidth, cellHeight, false));
			
			// Any live cell with two or three live neighbors lives
			else if (isAlive && (neighbors == 2 || neighbors == 3))
				nextGen.add(organismFactory(i, cellWidth, cellHeight, true));
			
			// Any live cell with more than three live neighbors dies
			else if (isAlive && neighbors > 3)
				nextGen.add(organismFactory(i, cellWidth, cellHeight, false));
			
			// Any dead cell with exactly three live neighbors becomes alive
			else if (!isAlive && neighbors == 3)
				nextGen.add(organismFactory(i, cellWidth, cellHeight, true));
			
			else
				nextGen.add(organismFactory(i, cellWidth, cellHeight, false));
		}

		// Reset grid
		cells = nextGen;
	}
	
	private Organism getCell(int x, int y) {
		int index = width * y + x;

		if (index < 0 || index > width * height || x < 0 || y < 0 || x > width - 1 || y > height - 1)
			return null;
		
		return cells.get(index);
	}
	
	public Organism getCell(int i) {
		return getCell(getXCoord(i), getYCoord(i));
	}

	private int getXCoord(int i) {
		return i % width;
	}
	
	private int getYCoord(int i) {
		return i / height;
	}

	public void seedGrid() {
		Random rand = new Random();
		Organism cell;

		for (int i = 0; i < width * height; i++) {
			cell = getCell(i);
			cell.setLife(rand.nextBoolean());
		}
	}

	public void seedGrid(List<Boolean> states) {
		// In case the list passed in is greater than the grid, iterate over the
		// smaller list of cells
		int len = states.size() > cells.size() ? cells.size() : states.size();
		Organism cell;
		
		for (int i = 0; i < len; i++) {
			cell = getCell(i);
			cell.setLife(states.get(i));
		}
	}
	
	public void setGridAt(boolean life, int i) {
		getCell(i).setLife(life);
	}
	
	public void setGridAt(boolean life, int x, int y) {
		getCell(x, y).setLife(life);
	}

	public String getGrid() {
		String gridStatus = "";
		Organism cell;

		for (int i = 0; i < width * height; i++) {
			cell = getCell(i);
			gridStatus += (cell.isAlive() ? "#" : " ") + "";

			if ((i + 1) % width == 0)
				gridStatus += "<br/>";
		}

		return gridStatus;
	}
	
	public void getGridStates(List<Boolean> states) {
		for (Organism org : cells)
			states.add(org.isAlive());
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
