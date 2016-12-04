package model;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lib.ConfigurableOption;
import lib.IRenderableHolder;
import lib.IRenderableObject;
import logic.GameManager;

public class ScoreBar implements IRenderableObject {
	
	private boolean isVisible;
	private int x;
	private int y;
	private int z;
	private int startTime;
	private int remainingTime;
	private int matchTime;
	private int min;
	private int sec;
	private int[] scores = {0, 0}; 
	private boolean isStart;
	
	public ScoreBar(int matchTime, int x, int y, int z) {
		this.isVisible = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.matchTime = matchTime;
		this.isStart = false;
		this.remainingTime = matchTime;
		this.min = remainingTime / 60;
		this.sec = remainingTime % 60;
	}
	
	public void update(int counter) {
		
		int[] count = {0, 0};
		int countMap = 0;
		for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
			for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
				int mapType = GameManager.map.getMapAt(j, i); 
				if(mapType == 1) count[0]++;
				else if(mapType == 2) count[1]++;
				countMap++;
			}
		}
		
		for(int i = 0; i < 2; i++) {
			scores[i] = (int) Math.ceil((double) count[i] * 100 / (double) countMap);
		}
		
		if(GameManager.myHero.getId() == 2) {
			int tmp = scores[0];
			scores[0] = scores[1];
			scores[1] = tmp;
		}
		
		if(isStart) {
			int currentTime = (int) System.currentTimeMillis();
			remainingTime = matchTime - (int) Math.ceil((double) (currentTime - startTime) / 1000.0);
			if(remainingTime < 0) {
				isStart = false;
				min = 0;
				sec = 0;
				GameManager.gameEnd(count[0], count[1]);
				GameManager.setReady(false);
			} 
			else {
				min = remainingTime / 60;
				sec = remainingTime % 60;
			}
		}
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
		gc.setLineWidth(4);
		gc.setStroke(Color.WHITE);
		
		gc.setGlobalAlpha(0.5);
		// my score
		gc.setFill(GameManager.myHero.getBodyColor());
		gc.fillRoundRect(this.x - 100, this.y, 100, 50, 10, 10);
		gc.fillRect(this.x - 3, this.y, 3, 50);
		// enemy score
		gc.setFill(GameManager.enemyHero.getBodyColor());
		gc.fillRoundRect(this.x, this.y, 100, 50, 10, 10);
		gc.fillRect(this.x, this.y, 3, 50);
		
		gc.setGlobalAlpha(1);
		
		gc.strokeRoundRect(this.x - 100, this.y, 200, 50, 10, 10);
		
		gc.setFill(Color.BLACK);
		gc.fillOval(this.x - 40, this.y - 15, 80, 80);
		gc.strokeOval(this.x - 40, this.y - 15, 80, 80);
		
		gc.setFont(IRenderableHolder.mainFont[20]);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.WHITE);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText(scores[0] + "%", this.x - 70, this.y + 25);
		gc.fillText(scores[1] + "%", this.x + 70, this.y + 25);
		
		gc.setFont(IRenderableHolder.mainFont[26]);
		if(sec < 10) gc.fillText(min + ":0" + sec, this.x, this.y + 25);
		else gc.fillText(min + ":" + sec, this.x, this.y + 25);
		
	}

	public void setStart(boolean b) {
		// TODO Auto-generated method stub
		this.isStart = b;
		if(b) this.startTime = (int) System.currentTimeMillis();
	}

}
