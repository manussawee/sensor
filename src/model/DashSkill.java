package model;

import javafx.scene.input.KeyCode;
import lib.InputUtility;
import lib.Skill;
import logic.GameManager;

public class DashSkill extends Skill {

	public DashSkill(int coolDown, int x, int y, int z, Hero hero, KeyCode keyCode) {
		super(coolDown, x, y, z, hero, keyCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(int counter) {
		// TODO Auto-generated method stub
		if(counter - lastUse >= coolDown) {
			lastUse = counter;
			int[] addX = {0, 1, 0, -1};
			int[] addY = {-1, 0, 1, 0};
			for(int i = 1; i <= 4; i++) {
				
				int newX = hero.getX() + addX[hero.getDirection()];
				int newY = hero.getY() + addY[hero.getDirection()];
				int mapType = GameManager.map.getMapAt(newX, newY);
				
				if(mapType >= 0) {
					GameManager.map.setMapAt(newX - addX[hero.getDirection()], newY - addY[hero.getDirection()], hero.getMapChange());
					GameManager.map.setMapAt(newX, newY, -1);
					if(mapType == 0) hero.setMapChange(hero.getId());
					else if(mapType != hero.getId()) hero.setMapChange(-1);
					hero.setX(newX);
					hero.setY(newY);
					hero.getUltimateSkill().increaseUltimatePoint();
				}
				else break;
			}
		}
	}
	
	@Override
	public void postAction(int counter) {
		
	}
	

}
