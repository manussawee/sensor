package model;

import java.util.Random;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lib.IRenderableHolder;
import lib.IRenderableObject;

public class GameText implements IRenderableObject {
	
	private String text;
	private int x;
	private int y;
	private int defaultX;
	private int defaultY;
	private int lineWidth;
	private Color fontColor; 
	private Color strokeColor;
	private int nextShock;
	private int fontSize;
	private int shockX;
	private int shockY;
	private int shockEnd;
	private boolean shockFront;
	private boolean isShock;
	private Color shockColor;
	
	private int z;
	
	public GameText(String text, int x, int y, int z, int lineWidth, int fontSize, Color fontColor, Color strokeColor) {
		this.text = text;
		this.defaultX = x;
		this.defaultY = y;
		this.x = x;
		this.y = y;
		this.lineWidth = lineWidth;
		this.fontColor  = fontColor;
		this.strokeColor = strokeColor;
		this.z = z;
		this.fontSize = fontSize;
		this.nextShock = 0;
		this.shockX = 0;
		this.shockY = 0;
		this.shockEnd = -1000;
		this.shockFront = false;
		this.isShock = false;
		this.shockColor = Color.BLUE;
	}

	public int getDefaultX() {
		return defaultX;
	}

	public void setDefaultX(int defaultX) {
		this.defaultX = defaultX;
	}

	public int getDefaultY() {
		return defaultY;
	}

	public void setDefaultY(int defaultY) {
		this.defaultY = defaultY;
	}
	
	public void update(int counter) {
		Random rand = new Random();
		if(counter >= nextShock) {
			nextShock = counter + rand.nextInt(100) + 200;
			shockEnd = counter + rand.nextInt(60) + 30;
		}
		if(isShock = shockEnd >= counter) {
			if(counter % 3 == 0) {
				shockX = rand.nextInt(20) - 10;
				shockY = rand.nextInt(20) - 10;
				shockFront = rand.nextInt(3) == 0;
				shockColor = IRenderableHolder.randomColor();
			}
		}
		else {
			shockX = 0;
			shockY = 0;
		}
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setZ(int z) {
		this.z = z;
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

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}
	

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFont(Font.loadFont(IRenderableHolder.mainFontName, fontSize));
		gc.setStroke(strokeColor);
		gc.setLineWidth(lineWidth);
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.CENTER);
		
		if(!shockFront && isShock) {
			gc.setFill(shockColor);
			gc.fillText(text, x + shockX, y + shockY);
		}
	
		gc.setFill(fontColor);
		// draw
		gc.fillText(text, x, y);
		if(lineWidth != 0) {
			gc.strokeText(text, x, y);
		}
		
		if(shockFront && isShock) {
			gc.setGlobalAlpha(0.5);
			gc.setFill(shockColor);
			gc.fillText(text, x + shockX, y + shockY);
			gc.setGlobalAlpha(1);
		}
	}

}
