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
		if(counter - lastUse >= coolDown) lastUse = counter;
	}
	
	@Override
	public void postAction(int counter) {
		if(counter - lastUse < 4) {
			int lastMove = hero.getLastMove();
			int newX = hero.getX() + hero.addX[hero.getDirection()];
			int newY = hero.getY() + hero.addY[hero.getDirection()];
			if(hero.startMove("STAY")) {
				hero.setX(newX);
				hero.setY(newY);
			}
			hero.setLastMove(lastMove);
		}
	}
	

}
