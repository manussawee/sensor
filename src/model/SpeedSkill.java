package model;

import javafx.scene.input.KeyCode;
import lib.UltimateSkill;

public class SpeedSkill extends UltimateSkill {

	public SpeedSkill(int maxUltimatePoint, int x, int y, int z, Hero hero, KeyCode keyCode, int ultimateDuration) {
		super(maxUltimatePoint, x, y, z, hero, keyCode, ultimateDuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action(int counter) {
		// TODO Auto-generated method stub
		if(!isUse && ultimatePoint >= maxUltimatePoint) {
			ultimatePoint = 0;
			isUse = true;
			lastUse = counter;
			hero.setMoveInterval(3);
		}
	}

	@Override
	public void postAction(int counter) {
		// TODO Auto-generated method stub
		if(isUse && counter - lastUse >= ultimateDuration) {
			isUse = false;
			hero.setMoveInterval(10);
		}
		else if(counter%12 == 0){
			increaseUltimatePoint();
		}
	}

}
