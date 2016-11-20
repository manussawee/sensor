package logic;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import model.Hero;
import model.Map;

public class GameManager extends DefaultManager {

	public static Hero myHero;
	public static Hero enemyHero;
	public static Map map;
	
	private int counter = 0;
	
	public GameManager() {
		
		myHero = new Hero(1, 1, 2, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, Color.BLUE, Color.CORNFLOWERBLUE, 1);
		enemyHero = new Hero(ConfigurableOption.mapWidth, ConfigurableOption.mapHeight, 2, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, Color.RED, Color.INDIANRED, 2);
		
		map = new Map(ConfigurableOption.mapWidth, ConfigurableOption.mapHeight, 0);
		IRenderableHolder.getInstance().add(myHero);
		IRenderableHolder.getInstance().add(enemyHero);
		IRenderableHolder.getInstance().add(map);
		counter = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		myHero.move(counter);
		enemyHero.move(counter);
		
		// Reset
		counter++;
		InputUtility.clearKeyTriggered();
	}

}
