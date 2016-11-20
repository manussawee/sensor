package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.DrawingUtility;
import lib.IRenderableObject;
import lib.InputUtility;

public class Hero implements IRenderableObject {
	
	private int x;
	private int y;
	private int z;
	
	private int lastMove;
	
	private int radius = 50;

	public Hero(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		lastMove = -5;
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
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		DrawingUtility.drawHero(gc, this.x, this.y, this.radius, Color.BLUE);
	}

	public void move(int counter) {
		// TODO Auto-generated method stub
//		if(counter - lastMove >= 0) {
			if(InputUtility.getKeyPressed(KeyCode.UP)) {
				this.y--;
//				lastMove = counter;
			}
			else if(InputUtility.getKeyPressed(KeyCode.DOWN)) {
				this.y++;
//				lastMove = counter;
			}
			else if(InputUtility.getKeyPressed(KeyCode.LEFT)) {
				this.x--;
//				lastMove = counter;
			}
			else if(InputUtility.getKeyPressed(KeyCode.RIGHT)) {
				this.x++;
//				lastMove = counter;
			}
//		}
	}
}
