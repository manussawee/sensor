package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.IRenderableObject;
import logic.GameManager;

public class MiniMap implements IRenderableObject{
	
	private boolean visible;
	private Hero hero;
	private int x;
	private int y;
	private int z;
	
	public MiniMap(int x, int y, int z, Hero hero) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.hero = hero;
		this.visible = true;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
		gc.setFill(Color.color(0, 0, 0, 0.5));
		gc.fillRoundRect(x-1, y-1, 101, 101, 10, 10);
		
		for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
			for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
				int mapType = GameManager.map.getMapAt(j, i);
				if(mapType == 0) {
					
				}
				else if(mapType == -1) {
					gc.setFill(Color.DARKGRAY);
					gc.fillRect(x + j * 5 - 5, y + i * 5 - 5, 5, 5);
				}
				else if(mapType == hero.getId()) {
					gc.setFill(hero.getBodyColor());
					gc.fillRect(x + j * 5 - 5, y + i * 5 - 5, 5, 5);
				}
				else {
					gc.setFill(GameManager.heroes[hero.getId()%2].getBodyColor());
					gc.fillRect(x + j * 5 - 5, y + i * 5 - 5, 5, 5);
				}
			}
		}
		
		gc.setFill(Color.WHITE);
		gc.fillRect(x + hero.getX() * 5 - 5, y + hero.getY() * 5 - 5, 5, 5);
		
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.strokeRoundRect(x-1, y-1, 101, 101, 10, 10);
	}
}
