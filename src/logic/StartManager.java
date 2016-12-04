package logic;

import application.Main;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Pointable;
import model.Background;
import model.GameButton;
import model.GameText;
import ui.ConnectionScreen;
import ui.ExitScreen;
import ui.OptionsScreen;

public class StartManager extends DefaultManager {
	
	private Background background;
	private GameText gameTitle;
	private GameButton startButton;
	private GameButton settingsButton;
	private GameButton exitButton;
	
	private int counter;
	
	public StartManager() {
		
		IRenderableHolder.getInstance().getEntities().clear();
		
		background = new Background(0);
		gameTitle = new GameText("STARBRAWL", ConfigurableOption.screenWidth / 2, 100, 1, 0, 100, Color.ORANGE, Color.WHITE);
		startButton = new GameButton("PLAY", 50, 285, 1, 200, 75, 3, 75, Color.WHITE, Color.BLACK);
		settingsButton = new GameButton("OPTIONS", 50, 360, 1, 320, 75, 3, 75, Color.WHITE, Color.BLACK);
		exitButton = new GameButton("EXIT", 50, 435, 1, 180, 75, 3, 75, Color.WHITE, Color.BLACK);
		
		counter = 0;
		IRenderableHolder.getInstance().add(background);
		IRenderableHolder.getInstance().add(gameTitle);
		IRenderableHolder.getInstance().add(startButton);
		IRenderableHolder.getInstance().add(settingsButton);
		IRenderableHolder.getInstance().add(exitButton);
		IRenderableHolder.getInstance().sort();
		
		IRenderableHolder.stopAllSounds();
		IRenderableHolder.startSound.play();
	}
	
	@Override
	public void update() {
		
		// mouse over
		Pointable pointObject = this.getTopMostTargetAt(InputUtility.getMouseX(), InputUtility.getMouseY());
		if(pointObject == startButton) {
			// reset
			startButton.setPoint(true, counter);
			settingsButton.setPoint(false, counter);
			exitButton.setPoint(false, counter);
			
			// click
			if(InputUtility.isMouseLeftDown()) {
				new ConnectionScreen();
				IRenderableHolder.mouseClickSound.play();
			}
		}
		else if(pointObject == settingsButton) {
			// reset
			settingsButton.setPoint(true, counter);
			startButton.setPoint(false, counter);
			exitButton.setPoint(false, counter);
			
			// click
			if(InputUtility.isMouseLeftDown()) {
				new OptionsScreen();
				IRenderableHolder.mouseClickSound.play();
			}
		}
		else if(pointObject == exitButton) {
			// reset
			exitButton.setPoint(true, counter);
			startButton.setPoint(false, counter);
			settingsButton.setPoint(false, counter);
			
			// click
			if(InputUtility.isMouseLeftDown()) {
				new ExitScreen();
				IRenderableHolder.mouseClickSound.play();
			}
		}
		else {
			startButton.setPoint(false, counter);
			settingsButton.setPoint(false, counter);
			exitButton.setPoint(false, counter);
		}
		
		background.update(counter);
		gameTitle.update(counter);
		startButton.update(counter);
		settingsButton.update(counter);
		exitButton.update(counter);
		
		// Reset
		counter++;
		InputUtility.setMouseLeftDown(false);
	}
}
