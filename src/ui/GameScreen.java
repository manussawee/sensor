package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import lib.ConfigurableOption;
import lib.IRenderableHolder;
import lib.IRenderableObject;
import lib.InputUtility;

public class GameScreen extends StackPane {
	
	private Canvas canvas;
	
	public GameScreen() {
		super();
		this.canvas = new Canvas(ConfigurableOption.screenWidth, ConfigurableOption.screenHeight);
		this.getChildren().add(this.canvas);
		this.addListener();
	}
	
	public void paintComponent() {
		
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

		// render 
		for(IRenderableObject renderable: IRenderableHolder.getInstance().getEntities()) {
			renderable.render(gc);
		}
	}
	
	public void requestFocusForCanvas(){
		this.requestFocus();
	}
	
	private void addListener() {
		// get mouse pointer (x,y)
		this.setOnMouseMoved((MouseEvent e) -> {
			if(InputUtility.isMouseOnScreen()) {
				InputUtility.setMouseX((int) e.getX());
				InputUtility.setMouseY((int) e.getY());
			}
		});
		
		this.setOnMouseEntered((MouseEvent e) -> {
			InputUtility.setMouseOnScreen(true);
		});
		
		this.setOnMouseExited((MouseEvent e) -> {
			InputUtility.setMouseOnScreen(false);
		});
		
		this.setOnMousePressed((MouseEvent e) -> {
			if(e.getButton() == MouseButton.PRIMARY) {
				InputUtility.setMouseLeftDown(true);
			}
		});
		
		this.setOnKeyPressed((KeyEvent e) -> {
			if (!InputUtility.getKeyPressed(e.getCode()))
				InputUtility.setKeyTriggered(e.getCode(), true);
			InputUtility.setKeyPressed(e.getCode(), true);
		});
		
		this.setOnKeyReleased((KeyEvent e) -> {
			InputUtility.setKeyPressed(e.getCode(), false);
		});
		
	}
}
