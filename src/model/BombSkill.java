package model;

import javafx.scene.input.KeyCode;
import lib.Skill;
import lib.UltimateSkill;
import logic.GameManager;

public class BombSkill extends Skill {

	public BombSkill(int coolDown, int x, int y, int z, Hero hero, KeyCode keyCode) {
		super(coolDown, x, y, z, hero, keyCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(int counter) {
		// TODO Auto-generated method stub
		if(counter - lastUse >= coolDown) {
			lastUse = counter;
			for(int i = hero.getY() - 4; i <= hero.getY() + 4; i++) {
				for(int j = hero.getX() - 4; j <= hero.getX() + 4; j++) {
					
					if(j == hero.getX() && i == hero.getY()) continue;
					
					if(Math.abs(hero.getY() - i) + Math.abs(hero.getX() - j) <= 4) {
						if(GameManager.map.getMapAt(j, i) == -1) {
							GameManager.map.setMapAt(j, i, 0);
							hero.getUltimateSkill().increaseUltimatePoint();
						}
					}
				}
			}
		}
	}
	
	@Override
	public void postAction(int counter) {
		
	}

}
