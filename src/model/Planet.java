package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import lib.IRenderableHolder;
import lib.IRenderableObject;
import logic.GameManager;

public class Planet implements IRenderableObject {
	
	private int x;
	private int y;
	private int type;
	private boolean isVisible;
	private int rotateFrame;
	private int sizeFrame;
	private int planetIndex;
	private int state;
	private int z;
	private double size;
	private double positionOffset;
	private int rotateDirection;
	private final int sizeDelay = 13;
	
	public Planet(int x, int y, int z, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.isVisible = false;
		this.rotateFrame = 0;
		this.state = 1;
		if(isVisible == true) {
			if(type == -1) this.planetIndex = 0;
			else this.planetIndex = this.type;
		}
		randomRotateDirection();
	}
	
	private void randomRotateDirection() {
		Random rand = new Random();
		if(rand.nextBoolean()) rotateDirection = 1;
		else rotateDirection = -1;
	}
	
	public void changeType(int type) {
		if(type == this.type) return;
		this.type = type;
		this.rotateFrame = 0;
		this.state = 2;
		this.sizeFrame = this.sizeDelay;
		randomRotateDirection();
	}
	
	public void update(int counter) {
		if(this.state == 2) {
			this.sizeFrame--;
			if(this.sizeFrame == 0) {
				
				this.state = 0;
				this.isVisible = true;
				
				if(type == 0) this.isVisible = false;
				else if(type == -1) this.planetIndex = 0;
				else this.planetIndex = this.type;
			}
		}
		else if(this.state == 1) {
			this.rotateFrame = (this.rotateFrame + 1) % 360;
		}
		else if(this.state == 0) {
			this.sizeFrame++;
			if(this.sizeFrame == this.sizeDelay) this.state = 1;
		}
		
		this.size = (double) this.sizeFrame / this.sizeDelay * 50;
		this.positionOffset = (double) (50 - this.size) / 2;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return isVisible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
		gc.save();
		Rotate r = new Rotate((this.rotateDirection * this.rotateFrame + 360) % 360, (this.x - 1) * 50 + GameManager.screenOffsetX() + 25, (this.y - 1) * 50 + GameManager.screenOffsetY() + 25);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(IRenderableHolder.planet[this.planetIndex], (this.x - 1) * 50 + GameManager.screenOffsetX() + this.positionOffset, (this.y - 1) * 50 + GameManager.screenOffsetY() + this.positionOffset, this.size, this.size);
		gc.restore();
	}

}
