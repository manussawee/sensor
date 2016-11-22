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
	
	public Map(int width, int height, int z) {
		this.width = width;
		this.height = height;
		this.z = z;
		
		table = new int[height + 1][width + 1];
	}
	
	public void setMapAt(int x, int y, int value) {
		table[y][x] = value;
	}
	
	public int getMapAt(int x, int y) {
		return table[y][x];
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
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect((1 - GameManager.myHero.getX()) * 50 + 50 * 7.5, (1 - GameManager.myHero.getY()) * 50 + 50 * 5.5, ConfigurableOption.mapWidth * 50, ConfigurableOption.mapHeight * 50);
		
		for(int i = 1; i <= this.height; i++) {
			for(int j = 1; j <= this.width; j++) {
				
				if(table[i][j] == 0) gc.setFill(Color.WHITE);
				if(table[i][j] == -1) gc.setFill(Color.DARKGRAY);
				else {
					for(IRenderableObject obj : IRenderableHolder.getInstance().getEntities()) {
						if(obj instanceof Hero && ((Hero) obj).getId() == table[i][j]) { 
							gc.setFill(((Hero) obj).getMapColor());
						}
					}
				}
				
				gc.fillRect((j - GameManager.myHero.getX()) * 50 + 50 * 7.5, (i - GameManager.myHero.getY()) * 50 + 50 * 5.5, 50, 50);
			}
		}
	}

}
