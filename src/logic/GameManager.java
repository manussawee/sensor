package logic;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Provider;
import lib.Requester;
import model.GameText;
import model.Hero;
import model.Map;
import model.TransparentBackground;

public class GameManager extends DefaultManager {

	public static Hero myHero;
	public static Hero enemyHero;
	public static Hero[] heroes;
	public static Map map;
	public static String gameType;
	
	private static GameText waitingText;
	private static TransparentBackground waitingBG;
	private static boolean isReady;
	
	private int counter = 0;
	
	public GameManager(String gameType, String ipAddress) {
		
		IRenderableHolder.getInstance().getEntities().clear();
		
		GameManager.gameType = gameType;
		heroes = new Hero[2];
		
		map = new Map(ConfigurableOption.mapWidth, ConfigurableOption.mapHeight, 0);
		IRenderableHolder.getInstance().addAndSort(map);
		counter = 0;
		
		if(gameType == "SERVER") {
			isReady = false;
			waitingText = new GameText("WAITING FOR PLAYER", ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2, 11, 0, Font.font(null, FontWeight.BOLD, 42), Color.WHITE, Color.WHITE);
			waitingBG = new TransparentBackground(Color.color(0, 0, 0, 0.5), 10);
			IRenderableHolder.getInstance().addAndSort(waitingText);
			IRenderableHolder.getInstance().addAndSort(waitingBG);
			
			createMyHero("0");
			createEnemyHero("1");
			Provider server = new Provider();
			server.run();
		}
		else {
			isReady = true;
			
			createMyHero("1");
			createEnemyHero("0");
			Requester client = new Requester(ipAddress);
			client.run();
		}
	}
	
	public static boolean isReady() {
		return isReady;
	}

	public static void setReady(boolean isReady) {
		GameManager.isReady = isReady;
		if(isReady) {
			if(IRenderableHolder.getInstance().getEntities().contains(waitingText)) {
				IRenderableHolder.getInstance().getEntities().remove(waitingText);
			}
			if(IRenderableHolder.getInstance().getEntities().contains(waitingBG)) {
				IRenderableHolder.getInstance().getEntities().remove(waitingBG);
			}
		}
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
		IRenderableHolder.getInstance().addAndSort(myHero);
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
		IRenderableHolder.getInstance().addAndSort(enemyHero);
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

	public static void enemyMove(String move) {
		// TODO Auto-generated method stub
		if(move.equals("DOWN")) enemyHero.moveDown();
		else if(move.equals("UP")) enemyHero.moveUp();
		else if(move.equals("LEFT")) enemyHero.moveLeft();
		else if(move.equals("RIGHT")) enemyHero.moveRight();
	}

}
