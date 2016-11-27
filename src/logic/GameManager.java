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
import model.BombSkill;
import model.DashSkill;
import model.GameText;
import model.Hero;
import model.Map;
import model.MiniMap;
import model.ScoreBar;
import model.SpeedSkill;
import model.TransparentBackground;

public class GameManager extends DefaultManager {

	public static Hero myHero;
	public static Hero enemyHero;
	public static Hero[] heroes;
	public static Map map;
	public static String gameType;
	public static SocketService socketService;
	public static MiniMap miniMap;
	public static ScoreBar scoreBar;
	
	private static GameText waitingText;
	private static TransparentBackground waitingBG;
	private static GameText endingText;
	private static GameText endingScore;
	private static boolean isReady;
	
	private static int counter = 0;
	
	public static int getCounter() {
		return counter;
	}

	public GameManager(String gameType, String ipAddress) {
		
		IRenderableHolder.getInstance().getEntities().clear();
		
		GameManager.gameType = gameType;
		heroes = new Hero[2];
		
		map = new Map(ConfigurableOption.mapWidth, ConfigurableOption.mapHeight, 0);
		scoreBar = new ScoreBar(3 * 60, ConfigurableOption.screenWidth / 2, 30, 5);
		IRenderableHolder.getInstance().add(scoreBar);
		IRenderableHolder.getInstance().add(map);
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
			scoreBar.setStart(true);
			socketService = new Requester(ipAddress);
			socketService.run();
		}
		
		miniMap = new MiniMap(ConfigurableOption.screenWidth - 120, ConfigurableOption.screenHeight - 120, 5, myHero);
		IRenderableHolder.getInstance().add(miniMap);
		IRenderableHolder.getInstance().sort();
	}
	
	public static boolean isReady() {
		return isReady;
	}
	
	public static double screenOffsetX() {
		return - GameManager.myHero.getDrawX() + ConfigurableOption.screenWidth / 2 - 25;
	}
	
	public static double screenOffsetY() {
		return - GameManager.myHero.getDrawY() + ConfigurableOption.screenHeight / 2 - 25;
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
			scoreBar.setStart(true);
		}
		else {
			scoreBar.setStart(false);
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
		
		DashSkill dashSkill = new DashSkill(60 * 5, 20, ConfigurableOption.screenHeight - 70, 5, myHero, KeyCode.Z);
		myHero.getSkills().add(dashSkill);
		BombSkill bombSkill = new BombSkill(60 * 5, 90, ConfigurableOption.screenHeight - 70, 5, myHero, KeyCode.X);
		myHero.getSkills().add(bombSkill);
		
		SpeedSkill speedSkill = new SpeedSkill(500, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight - 55, 5, myHero, KeyCode.C, 360);
		myHero.getSkills().add(speedSkill);
		myHero.setUltimateSkill(speedSkill);

		IRenderableHolder.getInstance().add(dashSkill);
		IRenderableHolder.getInstance().add(bombSkill);
		IRenderableHolder.getInstance().add(speedSkill);
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
		BombSkill bombSkill = new BombSkill(60 * 5, 125, ConfigurableOption.screenHeight - 100, 5, enemyHero, null);
		bombSkill.setVisible(false);
		enemyHero.getSkills().add(bombSkill);
		
		SpeedSkill speedSkill = new SpeedSkill(500, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight - 140, 5, enemyHero, null, 360);
		speedSkill.setVisible(false);
		enemyHero.getSkills().add(speedSkill);
		enemyHero.setUltimateSkill(speedSkill);

		IRenderableHolder.getInstance().add(dashSkill);
		IRenderableHolder.getInstance().add(bombSkill);
		IRenderableHolder.getInstance().add(speedSkill);
		IRenderableHolder.getInstance().add(enemyHero);
		IRenderableHolder.getInstance().sort();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(isReady) {
			myHero.update(counter);
			enemyHero.update(counter);
			scoreBar.update(counter);
		}
		
		// Reset
		counter++;
		InputUtility.clearKeyTriggered();
	}

	public static void enemyMove(String move) {
		// TODO Auto-generated method stub
		enemyHero.startMove(move);
	}

	public static void enemySkill(int index) {
		// TODO Auto-generated method stub
		enemyHero.getSkills().get(index).action(counter);
	}

	public static void gameEnd(int myScore, int enemyScore) {
		// TODO Auto-generated method stub
		String message;
		Color color = enemyHero.getBodyColor();
		if(myScore > enemyScore) {
			message = "VICTORY";
			color = myHero.getBodyColor();
		}
		else if(myScore < enemyScore) message = "DEFEAT";
		else message = "DRAW";
		
		endingText = new GameText(message, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2 - 50, 11, 1, Font.font("Tahoma", FontWeight.BOLD, 72), color, Color.WHITE);
		endingScore = new GameText(myScore + " - " + enemyScore, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2 + 20, 11, 0, Font.font("Tahoma", 48), Color.WHITE, Color.WHITE);
		waitingBG = new TransparentBackground(Color.color(0, 0, 0, 0.5), 10);
		
		IRenderableHolder.getInstance().add(waitingBG);
		IRenderableHolder.getInstance().add(endingText);
		IRenderableHolder.getInstance().addAndSort(endingScore);
	}

}
