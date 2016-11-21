package logic;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Requester;
import model.Hero;
import model.Map;

public class GameManager extends DefaultManager {

	public static Hero myHero;
	public static Hero enemyHero;
	public static Hero[] heroes;
	public static Map map;
	private static boolean isReady;
	
	private int counter = 0;
	
	public GameManager() {
		
		heroes = new Hero[2];
		isReady = false;
		
		map = new Map(ConfigurableOption.mapWidth, ConfigurableOption.mapHeight, 0);
		IRenderableHolder.getInstance().add(map);
		counter = 0;
		
		Requester client = new Requester();
		client.run();
	}
	
	public static void createMyHero(String position) {
		int x,y; 
		if(position == "0") {
			x = 1;
			y = 1;
		}
		else {
			x = ConfigurableOption.mapWidth;
			y = ConfigurableOption.mapHeight;
		}
		myHero = new Hero(x, y, 2, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, Color.BLUE, Color.CORNFLOWERBLUE, 1);
		heroes[0] = myHero;
		IRenderableHolder.getInstance().add(myHero);
	}
	
	public static void createEnemyHero(String position) {
		int x,y;
		if(position == "0") {
			x = 1;
			y = 1;
		}
		else {
			x = ConfigurableOption.mapWidth;
			y = ConfigurableOption.mapHeight;
		}
		enemyHero = new Hero(x, y, 2, null, null, null, null, Color.RED, Color.INDIANRED, 2);
		heroes[1] = enemyHero;
		IRenderableHolder.getInstance().add(enemyHero);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(isReady) {
			myHero.move(counter);
			enemyHero.move(counter);
		}
		
		// Reset
		counter++;
		InputUtility.clearKeyTriggered();
	}

	public static boolean isReady() {
		return isReady;
	}

	public static void setReady(boolean isReady) {
		GameManager.isReady = isReady;
	}

	public static void enemyMove(String move) {
		// TODO Auto-generated method stub
		if(move == "DOWN") enemyHero.moveDown();
		else if(move == "UP") enemyHero.moveUp();
		else if(move == "LEFT") enemyHero.moveLeft();
		else if(move == "RIGHT") enemyHero.moveRight();
	}

}
