package logic;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Pointable;
import model.GameButton;
import model.GameText;

public class StartManager extends DefaultManager {
	
	private Stage primaryStage;
	private DefaultManager currentManager;
	
	private Font font = new Font("Tahoma", 72);
	
	private GameText gameTitle;
	private GameButton startButton;
	private GameButton tutorialButton;
	private GameButton exitButton;
	
	private int counter;
	
	public StartManager(Stage primaryStage, DefaultManager currentManager) {
		
		this.primaryStage = primaryStage;
		this.currentManager = currentManager;
		
		gameTitle = new GameText("NONAME", ConfigurableOption.screenWidth / 2, 100, 1, 3, font, Color.ORANGE, Color.BLACK);
		startButton = new GameButton("START", 100, 250, 1, 225, 72, 3, font, Color.RED, Color.BLACK);
		tutorialButton = new GameButton("TUTORIAL", 100, 350, 1, 350, 72, 3, font, Color.RED, Color.BLACK);
		exitButton = new GameButton("EXIT", 100, 450, 1, 150, 72, 3, font, Color.RED, Color.BLACK);
		
		
		counter = 0;
		IRenderableHolder.getInstance().add(gameTitle);
		IRenderableHolder.getInstance().add(startButton);
		IRenderableHolder.getInstance().add(tutorialButton);
		IRenderableHolder.getInstance().add(exitButton);
	}
	
	@Override
	public void update() {
		
		// mouse over
		Pointable pointObject = this.getTopMostTargetAt(InputUtility.getMouseX(), InputUtility.getMouseY());
		if(pointObject == startButton) {
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("START");
				IRenderableHolder.getInstance().getEntities().clear();
				currentManager = null;
			}
		}
		else if(pointObject == tutorialButton) {
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("TUTORIAL");
			}
		}
		else if(pointObject == exitButton) {
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("EXIT");
				primaryStage.close();
			}
		}
		
		// Reset
		counter++;
		InputUtility.setMouseLeftDown(false);
	}
}
