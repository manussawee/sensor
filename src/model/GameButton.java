package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lib.Pointable;
import lib.DrawingUtility;
import lib.IRenderableObject;

public class GameButton implements IRenderableObject, Pointable{
	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	private int defaultX;
	private int defaultY;
	private int lineWidth;
	private Font font;
	private Color fontColor; 
	private Color strokeColor;
	
	private boolean isPointerOver;

	private int z;
	
	public GameButton(String text, int x, int y, int z, int width, int height, int lineWidth, Font font, Color fontColor, Color strokeColor) {
		this.text = text;
		this.defaultX = x;
		this.defaultY = y;
		this.x = x;
		this.y = y;
		this.lineWidth = lineWidth;
		this.font = font;
		this.fontColor  = fontColor;
		this.strokeColor = strokeColor;
		this.width = width;
		this.height = height;
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
	public boolean contains(int x, int y) {
		return this.x <= x && x <= this.x + this.width && this.y - this.height <= y && y <= this.y;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}
	

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		DrawingUtility.drawGameButton(gc, text, x, y, lineWidth, font, fontColor, strokeColor, isPointerOver);
	}

}
