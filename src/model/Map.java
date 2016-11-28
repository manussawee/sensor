package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.IRenderableHolder;
import lib.IRenderableObject;
import logic.GameManager;

public class Map implements IRenderableObject {
	
	private int width;
	private int height;
	private int z;
	
	private int[][] table;
	private Planet[][] planetTable;
	
	public Map(int width, int height, int z) {
		this.width = width;
		this.height = height;
		this.z = z;
		table = new int[height + 1][width + 1];
		
		planetTable = new Planet[height + 1][width + 1];
		for(int i = 1; i <= height; i++) {
			for(int j = 1; j <= width; j++) {
				planetTable[i][j] = new Planet(j, i, z+1, 0);
			}
		}
	}
	
	public synchronized void setMapAt(int x, int y, int value) {
		if(1 > x || x > ConfigurableOption.mapWidth || 1 > y || y > ConfigurableOption.mapHeight) return;
		table[y][x] = value;
		planetTable[y][x].changeType(value);
	}
	
	public synchronized int getMapAt(int x, int y) {
		if(1 > x || x > ConfigurableOption.mapWidth || 1 > y || y > ConfigurableOption.mapHeight) return -2;
		return table[y][x];
	}
	
	public void update(int counter) {
		for(int i = 1; i <= this.height; i++) {
			for(int j = 1; j <= this.width; j++) {
				planetTable[i][j].update(counter);
			}
		}
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.strokeRect(GameManager.screenOffsetX(), GameManager.screenOffsetY(), ConfigurableOption.mapWidth * 50, ConfigurableOption.mapHeight * 50);
		
		for(int i = 1; i <= this.height; i++) {
			for(int j = 1; j <= this.width; j++) {
				if(planetTable[i][j].isVisible()) planetTable[i][j].render(gc);
			}
		}
	}

}
