package lib;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.GameManager;
import model.Hero;

public abstract class Skill implements IRenderableObject {
	
	protected int lastUse;
	protected int coolDown;
	
	protected int x;
	protected int y;
	protected int z;
	protected boolean isVisible;
	
	protected Hero hero;
	protected KeyCode keyCode;
	
	public Skill(int coolDown,int x, int y, int z, Hero hero, KeyCode keyCode) {
		this.coolDown = coolDown;
		this.lastUse = -10000000;
		this.x = x;
		this.y = y;
		this.z = z;
		this.hero = hero;
		this.keyCode = keyCode;
		this.isVisible = true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public abstract void action(int counter);
	public abstract void postAction(int counter);
	public abstract void renderAnimation(GraphicsContext gc);

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return isVisible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		renderAnimation(gc);
		
		gc.setFill(Color.color(1, 1, 1, 0.5));
		gc.setLineWidth(2);
		gc.setStroke(Color.WHITE);
		
		int height = (int) (50 * (GameManager.getCounter() - lastUse) / coolDown);
		if(height > 50) height = 50;
		
		gc.fillRoundRect(x, y + 50 - height, 50, height, 10, 10);
		gc.strokeRoundRect(x, y, 50, 50, 10, 10);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(null, 48));
		gc.setFill(Color.WHITE);
		gc.fillText(keyCode.toString(), x + 25, y + 25);
	}

	public KeyCode getkeyCode() {
		// TODO Auto-generated method stub
		return keyCode;
	}
}
