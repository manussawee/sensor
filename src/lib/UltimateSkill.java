package lib;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Hero;

public abstract class UltimateSkill extends Skill {
	
	protected int maxUltimatePoint;
	protected int ultimatePoint;
	protected boolean isUse;
	protected int ultimateDuration;
	
	public UltimateSkill(int maxUltimatePoint, int x, int y, int z, Hero hero, KeyCode keyCode, int ultimateDuration) {
		super(0, x, y, z, hero, keyCode);
		// TODO Auto-generated constructor stub
		this.maxUltimatePoint = maxUltimatePoint;
		this.ultimatePoint = 0;
		this.isUse = false;
		this.ultimateDuration = ultimateDuration;
	}
	
	public void increaseUltimatePoint() {
		if(!isUse) {
			this.ultimatePoint++;
			if(this.ultimatePoint > this.maxUltimatePoint) this.ultimatePoint = this.maxUltimatePoint;
		}
	}
	
	@Override
	public void render(GraphicsContext gc) {
		
		gc.setFont(new Font(null, 48));
		gc.setLineWidth(8);
		
		if(ultimatePoint < maxUltimatePoint) gc.setFill(Color.color(0, 0, 0, 0.5));
		else gc.setFill(Color.color(1, 1, 1, 0.5));
		gc.fillOval(x - 35, y - 35, 70, 70);
		
		gc.setStroke(Color.color(1, 1, 1, 0.5));
		gc.strokeOval(x - 35, y - 35, 70, 70);
		
		gc.setStroke(Color.WHITE);
		gc.strokeArc(x - 35, y - 35, 70, 70, 90 - ultimatePoint * 360 / maxUltimatePoint, ultimatePoint * 360 / maxUltimatePoint, ArcType.OPEN);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.WHITE);
		
		if(ultimatePoint < maxUltimatePoint) {
			gc.setFont(new Font(null, 30));
			gc.fillText((ultimatePoint * 100 / maxUltimatePoint) + "%", x, y);
		}
		else {
			gc.setFont(new Font(null, 48));
			gc.fillText(keyCode.toString(), x, y);
		}
	}
	
	@Override
	public void renderAnimation(GraphicsContext gc) {
		
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return isUse;
	}

}
