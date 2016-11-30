package model;

import javafx.scene.input.KeyCode;
import lib.UltimateSkill;
import logic.GameManager;

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
		if(isUse) {
			if(counter - lastUse >= ultimateDuration) {
				isUse = false;
				hero.setMoveInterval(10);
			}
			else {
				int[] addX = {0, 1, 0, -1};
				int[] addY = {-1, 0, 1, 0};
				for(int i = 0; i < 4; i++) {
					if((hero.getDirection() + i)%2 == 0 && i != hero.getDirection()) continue;
					if(GameManager.map.getMapAt(hero.getX() + addX[i], hero.getY() + addY[i]) == -1) {
						GameManager.map.setMapAt(hero.getX() + addX[i], hero.getY() + addY[i], 0);
					}
				}
			}
		}
		else if(counter%12 == 0){
			increaseUltimatePoint();
		}
		readyText.update(counter);
	}

}
