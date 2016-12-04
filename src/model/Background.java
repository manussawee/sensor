package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import lib.IRenderableHolder;
import lib.IRenderableObject;

public class Background implements IRenderableObject {
	
	private int offsetX;
	private int z;
	private int[][] delays;
	private int framesCounter;
	private int frameHeight;
	private int frameWidth;
	private boolean isVisible;
	private int moveDelay;
	private boolean fastOffset;
	
	public Background(int z) {
		this.offsetX = 0;
		this.z = z;
		this.framesCounter = 0;
		this.frameHeight = (int) IRenderableHolder.backgroundFrames[0].getHeight();
		this.frameWidth = (int) IRenderableHolder.backgroundFrames[0].getWidth();
		this.isVisible = true;
		this.moveDelay = 4;
		this.fastOffset = false;
		
		delays = new int[4][4];
		Random rand = new Random();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				delays[i][j] = (rand.nextInt(20) + 10);
			}
		}
	}
	
	public void update(int counter) {
		if(counter % this.moveDelay == 0 && !this.fastOffset) {
			this.offsetX = (this.offsetX + 1) % (int) IRenderableHolder.backgroundFrames[0].getWidth();
		}
		else if(this.fastOffset) {
			this.offsetX = (this.offsetX + 10) % (int) IRenderableHolder.backgroundFrames[0].getWidth();
		}
		this.framesCounter = counter;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return this.isVisible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				gc.drawImage(IRenderableHolder.backgroundFrames[(this.framesCounter / this.delays[i][j]) % 6], j * frameWidth - this.offsetX, i * frameHeight);
			}
		}
	}

	public void setFastOffset(boolean b) {
		// TODO Auto-generated method stub
		this.fastOffset = b;
	}

}
