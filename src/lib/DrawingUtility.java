package lib;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class DrawingUtility {

	public static void drawGameText(GraphicsContext gc, String text, int x, int y, int lineWidth,
			Font font, Color fontColor, Color strokeColor) {
		// TODO Auto-generated method stub
		
		// set property
		gc.setFont(font);
		gc.setFill(fontColor);
		gc.setStroke(strokeColor);
		gc.setLineWidth(lineWidth);
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.CENTER);
		
		// draw
		gc.fillText(text, x, y);
		gc.strokeText(text, x, y);
	}
	
	public static void drawGameButton(GraphicsContext gc, String text, int x, int y, int lineWidth,
			Font font, Color fontColor, Color strokeColor, boolean isPointerOver) {
		// TODO Auto-generated method stub
		
		// set property
		gc.setFont(font);
		gc.setFill(fontColor);
		gc.setStroke(strokeColor);
		gc.setLineWidth(lineWidth);
		gc.setTextBaseline(VPos.BASELINE);
		gc.setTextAlign(TextAlignment.LEFT);
		
		// draw
		gc.fillText(text, x, y);
		gc.strokeText(text, x, y);
		
	}

}
