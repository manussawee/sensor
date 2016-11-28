package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import lib.IRenderableHolder;
import lib.Skill;
import lib.UltimateSkill;
import logic.GameManager;

public class BombSkill extends Skill {
	
	private int bombFrame;
	private int bombX;
	private int bombY;

	public BombSkill(int coolDown, int x, int y, int z, Hero hero, KeyCode keyCode) {
		super(coolDown, x, y, z, hero, keyCode);
		// TODO Auto-generated constructor stub
		bombFrame = -1;
	}

	@Override
	public void action(int counter) {
		// TODO Auto-generated method stub
		if(counter - lastUse >= coolDown) {
			lastUse = counter;
			bombX = hero.getX();
			bombY = hero.getY();
		}
	}
	
	@Override
	public void postAction(int counter) {
		if(counter - lastUse < 12) {
			
			this.bombFrame = (int) Math.floor((double) (counter - lastUse) / 2);
			int diff = (int) Math.ceil((double) this.bombFrame / 2);
			
			for(int i = hero.getY() - diff; i <= hero.getY() + diff; i++) {
				for(int j = hero.getX() - diff; j <= hero.getX() + diff; j++) {
					
					if(Math.abs(hero.getY() - i) + Math.abs(hero.getX() - j) <= diff) {
						
						if(GameManager.map.getMapAt(j, i) != -2 && GameManager.map.getMapAt(j, i) != 0) {
							
							GameManager.map.setMapAt(j, i, 0);
							if(GameManager.map.getMapAt(j, i) != hero.getId()) hero.getUltimateSkill().increaseUltimatePoint();
						}
					}
				}
			}
		}
		else this.bombFrame = -1;
	}
	
	@Override
	public void renderAnimation(GraphicsContext gc) {
		if(bombFrame != -1) {
			gc.drawImage(IRenderableHolder.explosionFrames[hero.getId() - 1][bombFrame], (bombX - 4) * 50 + GameManager.screenOffsetX(), (bombY - 4) * 50 + GameManager.screenOffsetY());
		}
	}

}
