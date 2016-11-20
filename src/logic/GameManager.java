package logic;

import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import model.Hero;

public class GameManager extends DefaultManager {
	
	public static int[][] gameMap = new int[31][31];
	private Hero myHero;
	
	private int counter = 0;
	
	public GameManager() {
		
		for(int i = 1; i <= 30; i++) {
			for(int j = 1; j <= 30; j++) {
				gameMap[i][j] = 0;
			}
		}
		
		myHero = new Hero(1,1, 0);
		IRenderableHolder.getInstance().add(myHero);
		gameMap[myHero.getX()][myHero.getY()] = 1;
		counter = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		myHero.move(counter);
		// Reset
		counter++;
		InputUtility.clearKeyTriggered();
	}

}
