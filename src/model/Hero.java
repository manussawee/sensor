package model;

import java.util.ArrayList;

import javax.security.auth.login.Configuration;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.IRenderableObject;
import lib.InputUtility;
import lib.Provider;
import lib.Requester;
import lib.Skill;
import lib.UltimateSkill;
import logic.GameManager;

public class Hero implements IRenderableObject {
	
	public static final int[] addX = {0, 1, 0, -1};
	public static final int[] addY = {-1, 0, 1, 0};
	
	private int x;
	private int y;
	private double drawX;
	private double drawY;
	private int z;
	private KeyCode up;
	private KeyCode down;
	private KeyCode left;
	private KeyCode right;
	private Color bodyColor;
	private Color mapColor;
	private int id;
	private int mapChange;
	
	private int direction; // up=0, right=1, down=2, left=3
	private int lastMove;
	private int moveInterval;
	
	private ArrayList<Skill> skills;
	private UltimateSkill ultimateSkill;
	
	public UltimateSkill getUltimateSkill() {
		return ultimateSkill;
	}

	public void setUltimateSkill(UltimateSkill ultimateSkill) {
		this.ultimateSkill = ultimateSkill;
	}

	public Hero(int x, int y, int z, KeyCode up, KeyCode down, KeyCode left, KeyCode right, Color bodyColor, Color mapColor, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.bodyColor = bodyColor;
		this.mapColor = mapColor;
		this.id = id;
		this.mapChange = id;
		this.direction = 2;
		this.skills = new ArrayList<Skill>();
		this.moveInterval = 10;
		this.lastMove = -100;
		
		this.drawX = (this.x - 1) * 50; 
		this.drawY = (this.y - 1) * 50;
	}
	
	public Color getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(Color bodyColor) {
		this.bodyColor = bodyColor;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public int getId() {
		return id;
	}
	
	public Color getMapColor() {
		return mapColor;
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
		gc.setFill(this.bodyColor);
		gc.fillRect(this.drawX + GameManager.screenOffsetX(), this.drawY + GameManager.screenOffsetY(), 50, 50);
	}
	
	public double getDrawX() {
		return drawX;
	}

	public void setDrawX(double drawX) {
		this.drawX = drawX;
	}

	public double getDrawY() {
		return drawY;
	}

	public void setDrawY(double drawY) {
		this.drawY = drawY;
	}

	public void update(int counter) {
		
		// calculate skill
		for(Skill skill: skills) {
			if(skill.getkeyCode() != null && InputUtility.getKeyTriggered(skill.getkeyCode())) {
				skill.action(counter);
				GameManager.socketService.sendSkill(skills.indexOf(skill));
			}
		}
		
		// move
		move(counter);
		
		// calculate position
		this.drawX = (this.x - 1) * 50; 
		this.drawY = (this.y - 1) * 50;
				
		if(counter - this.lastMove <= this.moveInterval) {
			this.drawX += addX[this.direction] * (double) (counter - this.lastMove) / this.moveInterval * 50;
			this.drawY += addY[this.direction] * (double) (counter - this.lastMove) / this.moveInterval * 50;
			if(counter - this.lastMove == this.moveInterval) {
				
				int mapType = GameManager.map.getMapAt(this.x, this.y);
				if(mapType == 0) GameManager.map.setMapAt(this.x, this.y, this.id);
				else if(mapType != this.id && mapType > 0) GameManager.map.setMapAt(this.x, this.y, -1);
				
				this.x += addX[this.direction];
				this.y += addY[this.direction];
			}
		}
				
		for(Skill skill: skills) skill.postAction(counter);
		
	}
	
	public boolean startMove(String directionString) {
		if(directionString.equals("UP")) this.direction = 0;
		else if(directionString.equals("RIGHT")) this.direction = 1;
		else if(directionString.equals("DOWN")) this.direction = 2;
		else if(directionString.equals("LEFT")) this.direction = 3;
		
		int newX = this.x + addX[this.direction];
		int newY = this.y + addY[this.direction];
		int mapType = GameManager.map.getMapAt(newX, newY);
		
		if(mapType == 0) {
			this.ultimateSkill.increaseUltimatePoint();
			this.lastMove = GameManager.getCounter();
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
		}
		else if(mapType == this.id) {
			this.lastMove = GameManager.getCounter();
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
		}
		else if(mapType > 0) {
			this.ultimateSkill.increaseUltimatePoint();
			this.lastMove = GameManager.getCounter();
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = -1;
		}
		else return false;
		
		return true; 
	}

	public void move(int counter) {
		// TODO Auto-generated method stub
		if(counter - lastMove > moveInterval) {
			if(InputUtility.getKeyPressed(up)) {
				startMove("UP");
				GameManager.socketService.sendMove("UP");
			}
			else if(InputUtility.getKeyPressed(down)) {
				startMove("DOWN");
				GameManager.socketService.sendMove("DOWN");
			}
			else if(InputUtility.getKeyPressed(left)) {
				startMove("LEFT");
				GameManager.socketService.sendMove("LEFT");
			}
			else if(InputUtility.getKeyPressed(right)) {
				startMove("RIGHT");
				GameManager.socketService.sendMove("RIGHT");
			}
		}
	}

	public int getMapChange() {
		return mapChange;
	}

	public void setMapChange(int mapChange) {
		this.mapChange = mapChange;
	}

	public void setMoveInterval(int moveInterval) {
		// TODO Auto-generated method stub
		this.moveInterval = moveInterval;
	}

	public int getMoveInterval() {
		// TODO Auto-generated method stub
		return this.moveInterval;
	}

	public int getLastMove() {
		// TODO Auto-generated method stub
		return this.lastMove;
	}

	public void setLastMove(int lastMove) {
		// TODO Auto-generated method stub
		this.lastMove = lastMove;
	}
}
