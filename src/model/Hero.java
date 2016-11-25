package model;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.IRenderableObject;
import lib.InputUtility;
import lib.Provider;
import lib.Requester;
import lib.Skill;
import logic.GameManager;

public class Hero implements IRenderableObject {
	
	private int x;
	private int y;
	private int z;
	private KeyCode up;
	private KeyCode down;
	private KeyCode left;
	private KeyCode right;
	private Color bodyColor;
	private Color mapColor;
	private int id;
	private int mapChange;
	
	private ArrayList<Skill> skills;
	
	private int direction; // up=0, right=1, down=2, left=3

	private int lastMove;

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
		lastMove = -5;
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
		gc.fillRect((this.x - GameManager.myHero.getX()) * 50 + 50 * 7.5, (this.y - GameManager.myHero.getY()) * 50 + 50 * 5.5, 50, 50);
	}
	
	public void update(int counter) {
		move(counter);
		for(Skill skill: skills) {
			if(skill.getkeyCode() != null && InputUtility.getKeyTriggered(skill.getkeyCode())) {
				skill.action(counter);
				GameManager.socketService.sendSkill(skills.indexOf(skill));
			}
		}
	}
	
	public boolean moveUp() {
		
		int newX = this.x;
		int newY = this.y;
		
		this.direction = 0;
		
		if(this.y != 1) newY--;
		else return false;
		
		if(GameManager.map.getMapAt(newX, newY) == 0 || GameManager.map.getMapAt(newX, newY) == this.id) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		else if(GameManager.map.getMapAt(newX, newY) != -1) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = -1;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		
		return false;
	}
	
	public boolean moveDown() {
		
		int newX = this.x;
		int newY = this.y;
		
		this.direction = 2;
		
		if(this.y != ConfigurableOption.mapHeight) newY++;
		else return false;
		
		if(GameManager.map.getMapAt(newX, newY) == 0 || GameManager.map.getMapAt(newX, newY) == this.id) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		else if(GameManager.map.getMapAt(newX, newY) != -1) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = -1;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		
		return false;
	}
	
	public boolean moveLeft() {
		
		int newX = this.x;
		int newY = this.y;
		
		this.direction = 3;
		
		if(this.x != 1) newX--;
		else return false;
		
		if(GameManager.map.getMapAt(newX, newY) == 0 || GameManager.map.getMapAt(newX, newY) == this.id) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		else if(GameManager.map.getMapAt(newX, newY) != -1) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = -1;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		
		return false;
	}
	
	public boolean moveRight() {
		
		int newX = this.x;
		int newY = this.y;
		
		this.direction = 1;
		
		if(this.x != ConfigurableOption.mapWidth) newX++;
		else return false;
		
		if(GameManager.map.getMapAt(newX, newY) == 0 || GameManager.map.getMapAt(newX, newY) == this.id) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = this.id;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		else if(GameManager.map.getMapAt(newX, newY) != -1) {
			GameManager.map.setMapAt(this.x, this.y, this.mapChange);
			this.mapChange = -1;
			this.x = newX;
			this.y = newY;
			GameManager.map.setMapAt(this.x, this.y, -1);
			return true;
		}
		
		return false;
	}

	public void move(int counter) {
		// TODO Auto-generated method stub
		if(counter - lastMove >= 10) {
			if(InputUtility.getKeyPressed(up)) {
				moveUp();
				lastMove = counter;
				GameManager.socketService.sendMove("UP");
			}
			else if(InputUtility.getKeyPressed(down)) {
				moveDown();
				lastMove = counter;
				GameManager.socketService.sendMove("DOWN");
			}
			else if(InputUtility.getKeyPressed(left)) {
				moveLeft();
				lastMove = counter;
				GameManager.socketService.sendMove("LEFT");
			}
			else if(InputUtility.getKeyPressed(right)) {
				moveRight();
				lastMove = counter;
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
}
