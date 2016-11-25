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
import lib.SocketService;
import model.DashSkill;
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
	public static SocketService socketService;
	
	private static GameText waitingText;
	private static TransparentBackground waitingBG;
	private static boolean isReady;
	
	private static int counter = 0;
	
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
			socketService = new Provider();
			socketService.run();
		}
		else {
			isReady = true;
			
			createMyHero("1");
			createEnemyHero("0");
			socketService = new Requester(ipAddress);
			socketService.run();
		}
	}
	
	public static boolean isReady() {
		return isReady;
	}

	public static void setReady(boolean isReady) {
		GameManager.isReady = isReady;
		GameManager.counter = 0;
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
		
		DashSkill dashSkill = new DashSkill(60 * 5, 50, ConfigurableOption.screenHeight - 100, 5, myHero, KeyCode.Z);
		myHero.getSkills().add(dashSkill);

		IRenderableHolder.getInstance().add(dashSkill);
		IRenderableHolder.getInstance().add(myHero);
		IRenderableHolder.getInstance().sort();
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
		
		DashSkill dashSkill = new DashSkill(60 * 5, 0, 0, 0, enemyHero, null);
		dashSkill.setVisible(false);
		enemyHero.getSkills().add(dashSkill);

		IRenderableHolder.getInstance().add(dashSkill);
		IRenderableHolder.getInstance().add(enemyHero);
		IRenderableHolder.getInstance().sort();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(isReady) {
			myHero.update(counter);
			enemyHero.update(counter);
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

	public static void enemySkill(int index) {
		// TODO Auto-generated method stub
		enemyHero.getSkills().get(index).action(counter);
	}

}
