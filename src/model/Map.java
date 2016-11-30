package model;

import java.util.Random;

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
	private double lineWidth;
	private double alpha;
	private int breathingInterval;
	private Color borderColor;
	
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
		this.lineWidth = 2;
		this.alpha = 1;
		this.breathingInterval = 120;
		this.borderColor = Color.WHITE;
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
		updatePlanets(counter);
		breathBorder(counter);
	}
	
	private void updatePlanets(int counter) {
		for(int i = 1; i <= this.height; i++) {
			for(int j = 1; j <= this.width; j++) {
				planetTable[i][j].update(counter);
			}
		}
	}
	
	private void breathBorder(int counter) {
		this.lineWidth = (double) (counter % breathingInterval + 1) / breathingInterval * 4;
		this.alpha = (double) (breathingInterval - (counter % breathingInterval + 1)) / breathingInterval;
		if((counter % breathingInterval + 1) == breathingInterval) {
			borderColor = randomColor();
		}
	}
	
	private Color randomColor() {
		Random rand = new Random();
		double r = rand.nextDouble();
		double g = rand.nextDouble();
		double b = rand.nextDouble();
		return Color.color(r, g, b);
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
		
		gc.setStroke(borderColor);
		gc.setLineWidth(lineWidth);
		gc.setGlobalAlpha(alpha);
		gc.strokeRect(GameManager.screenOffsetX() - 5, GameManager.screenOffsetY() - 5, ConfigurableOption.mapWidth * 50 + 5, ConfigurableOption.mapHeight * 50 + 5);
		gc.setGlobalAlpha(1);
		
		for(int i = 1; i <= this.height; i++) {
			for(int j = 1; j <= this.width; j++) {
				if(planetTable[i][j].isVisible()) planetTable[i][j].render(gc);
			}
		}
	}

}
