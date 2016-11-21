package model;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lib.IRenderableObject;

public class GameText implements IRenderableObject {
	
	private String text;
	private int x;
	private int y;
	private int defaultX;
	private int defaultY;
	private int lineWidth;
	private Font font;
	private Color fontColor; 
	private Color strokeColor;
	
	private int z;
	
	public GameText(String text, int x, int y, int z, int lineWidth, Font font, Color fontColor, Color strokeColor) {
		this.text = text;
		this.defaultX = x;
		this.defaultY = y;
		this.x = x;
		this.y = y;
		this.lineWidth = lineWidth;
		this.font = font;
		this.fontColor  = fontColor;
		this.strokeColor = strokeColor;
		this.z = z;
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

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
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
		// set property
		gc.setFont(font);
		gc.setFill(fontColor);
		gc.setStroke(strokeColor);
		gc.setLineWidth(lineWidth);
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.CENTER);
				
		// draw
		gc.fillText(text, x, y);
		gc.strokeText(text, x, y);
	}

}
