package com.formigone.life.client;

public class Organism {
	private boolean life;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Organism(boolean life, int x, int y, int width, int height) {
		this.life = life;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Organism() {
		this(false, 0, 0, 10, 10);
	}
	
	public boolean isAlive() {
		return life;
	}
	
	public void setLife(boolean life) {
		this.life = life;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
