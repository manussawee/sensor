// game manager

package logic;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Provider;
import lib.Requester;
import lib.SocketService;
import model.Background;
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
	public static String ipAddress;
	public static MiniMap miniMap;
	public static ScoreBar scoreBar;
	public static Background background;
	
	private static GameText waitingText;
	private static TransparentBackground waitingBG;
	private static GameText endingText;
	private static GameText endingScore;
	private static GameText endingSubText;
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
		scoreBar = new ScoreBar(ConfigurableOption.matchTime, ConfigurableOption.screenWidth / 2, 30, 5);
		IRenderableHolder.getInstance().add(scoreBar);
		IRenderableHolder.getInstance().add(map);
		IRenderableHolder.getInstance().sort();
		counter = 0;
		
		if(gameType == "SERVER") {
			isReady = false;
			waitingText = new GameText("WAITING FOR PLAYER", ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2, 11, 0, 42, Color.WHITE, Color.WHITE);
			waitingBG = new TransparentBackground(Color.color(0, 0, 0, 0.5), 10);
			endingSubText = new GameText("PRESS ESC TO EXIT", ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight - 120, 13, 0, 20, Color.WHITE, Color.WHITE);
			IRenderableHolder.getInstance().addAndSort(endingSubText);
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
			GameManager.ipAddress = ipAddress;
			socketService = new Requester(ipAddress);
			socketService.run();
		}
		
		miniMap = new MiniMap(ConfigurableOption.screenWidth - 120, ConfigurableOption.screenHeight - 120, 5, myHero);
		background = new Background(-1);
		
		IRenderableHolder.getInstance().add(background);
		IRenderableHolder.getInstance().add(miniMap);
		IRenderableHolder.getInstance().sort();
		
		IRenderableHolder.stopAllSounds();
		IRenderableHolder.gameSound.play();

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
			if(IRenderableHolder.getInstance().getEntities().contains(endingSubText)) {
				IRenderableHolder.getInstance().getEntities().remove(endingSubText);
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

		myHero = new Hero(x, y, 2, ConfigurableOption.up, ConfigurableOption.down, ConfigurableOption.left, ConfigurableOption.right, Color.BLUE, Color.CORNFLOWERBLUE, 1);
		heroes[0] = myHero;
		
		DashSkill dashSkill = new DashSkill(60 * 5, 20, ConfigurableOption.screenHeight - 70, 5, myHero, ConfigurableOption.firstSkill);
		myHero.getSkills().add(dashSkill);
		BombSkill bombSkill = new BombSkill(60 * 5, 90, ConfigurableOption.screenHeight - 70, 5, myHero, ConfigurableOption.secondSkill);
		myHero.getSkills().add(bombSkill);
		
		SpeedSkill speedSkill = new SpeedSkill(500, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight - 55, 5, myHero, ConfigurableOption.ultimateSkill, 360);
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
		
		DashSkill dashSkill = new DashSkill(60 * 5, 1000, 1000, 5, enemyHero, null);
		enemyHero.getSkills().add(dashSkill);
		BombSkill bombSkill = new BombSkill(60 * 5, 1000, 1000, 5, enemyHero, null);
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
			if(myHero.getUltimateSkill().isActive()) background.setFastOffset(true);
			else background.setFastOffset(false);
		}
		else {
			checkExit();
		}
		
		map.update(counter);
		background.update(counter);

		if(endingText != null) endingText.update(counter);
		if(waitingText != null) waitingText.update(counter);
		
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
		
		endingText = new GameText(message, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2 - 50, 15, 1, 100, color, Color.WHITE);
		endingScore = new GameText(myScore + " - " + enemyScore, ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight / 2 + 10, 14, 0, 48, Color.WHITE, Color.WHITE);
		endingSubText = new GameText("PRESS ESC TO EXIT", ConfigurableOption.screenWidth / 2, ConfigurableOption.screenHeight - 120, 13, 0, 20, Color.WHITE, Color.WHITE);
		waitingBG = new TransparentBackground(Color.color(0, 0, 0, 0.5), 10);
		
		IRenderableHolder.getInstance().add(waitingBG);
		IRenderableHolder.getInstance().add(endingText);
		IRenderableHolder.getInstance().add(endingSubText);
		IRenderableHolder.getInstance().add(endingScore);
		IRenderableHolder.getInstance().sort();
	}

	private static void checkExit() {
		// TODO Auto-generated method stub
		if(InputUtility.getKeyTriggered(KeyCode.ESCAPE)) {
			System.exit(0);
		}
	}

}
