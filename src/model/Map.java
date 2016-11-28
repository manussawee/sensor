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
	private int[][] lastUpdate;
	
	public Map(int width, int height, int z) {
		this.width = width;
		this.height = height;
		this.z = z;
		
		lastUpdate = new int[height + 1][width + 1];
		table = new int[height + 1][width + 1];
	}
	
	public synchronized void setMapAt(int x, int y, int value) {
		if(1 > x || x > ConfigurableOption.mapWidth || 1 > y || y > ConfigurableOption.mapHeight) return;
		table[y][x] = value;
		lastUpdate[y][x] = GameManager.getCounter();
	}
	
	public synchronized int getMapAt(int x, int y) {
		if(1 > x || x > ConfigurableOption.mapWidth || 1 > y || y > ConfigurableOption.mapHeight) return -2;
		return table[y][x];
	}
	
	public synchronized int getLastUpdateAt(int x, int y) {
		return lastUpdate[y][x];
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
				
				if(table[i][j] == 0) gc.setFill(Color.BLACK);
				if(table[i][j] == -1) gc.setFill(Color.DARKGRAY);
				else {
					for(IRenderableObject obj : IRenderableHolder.getInstance().getEntities()) {
						if(obj instanceof Hero && ((Hero) obj).getId() == table[i][j]) { 
							gc.setFill(((Hero) obj).getMapColor());
						}
					}
				}
				
				gc.fillRect((j - 1) * 50 + GameManager.screenOffsetX(), (i - 1) * 50 + GameManager.screenOffsetY(), 50, 50);
			}
		}
	}

	public synchronized void setLastUpdateAt(int j, int i, int lastUpdate) {
		// TODO Auto-generated method stub
		this.lastUpdate[i][j] = lastUpdate;
	}

}
