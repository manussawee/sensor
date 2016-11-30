package logic;

import java.net.*;

import application.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lib.ConfigurableOption;
import lib.DefaultManager;
import lib.IRenderableHolder;
import lib.InputUtility;
import lib.Pointable;
import model.Background;
import model.GameButton;
import model.GameText;
import ui.GameScreen;

public class StartManager extends DefaultManager {
	
	private Stage primaryStage;
	private Font font;
	private Background background;
	private GameText gameTitle;
	private GameButton startButton;
	private GameButton tutorialButton;
	private GameButton exitButton;
	
	private int counter;
	
	public StartManager() {
		
		IRenderableHolder.getInstance().getEntities().clear();
		
		this.primaryStage = Main.getPrimaryStage();
		
		font = Font.loadFont(IRenderableHolder.mainFontName, 100);
		
		background = new Background(0);
		gameTitle = new GameText("NONAME", ConfigurableOption.screenWidth / 2, 100, 1, 0, font, Color.WHITE, Color.WHITE);
		startButton = new GameButton("START", 50, 250, 1, 225, 72, 3, font, Color.RED, Color.BLACK);
		tutorialButton = new GameButton("TUTORIAL", 50, 350, 1, 350, 72, 3, font, Color.RED, Color.BLACK);
		exitButton = new GameButton("EXIT", 50, 450, 1, 150, 72, 3, font, Color.RED, Color.BLACK);
		
		counter = 0;
		IRenderableHolder.getInstance().add(background);
		IRenderableHolder.getInstance().add(gameTitle);
		IRenderableHolder.getInstance().add(startButton);
		IRenderableHolder.getInstance().add(tutorialButton);
		IRenderableHolder.getInstance().add(exitButton);
		IRenderableHolder.getInstance().sort();
	}
	
	public void openGameType() throws Exception {
		
		GridPane mainPane = new GridPane();
		mainPane.setPrefSize(300, 150);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(5));
		
		TextField ipField = new TextField();
		ipField.setPrefWidth(200);
		mainPane.add(ipField, 1, 1);
		
		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.CENTER);
		subPane.setPadding(new Insets(5));
		mainPane.add(subPane, 1, 2);
		
		Button hostButton = new Button("HOST");
		Button joinButton = new Button("JOIN");
		subPane.add(hostButton, 2, 1);
		subPane.add(joinButton, 1, 1);
		
		Scene scene = new Scene(mainPane);
		Stage stg = new Stage();
		stg.setScene(scene);
		stg.show();
		
		hostButton.setOnMouseClicked((MouseEvent e) -> {
			Main.changeManager(new GameManager("SERVER", null));
			stg.close();
		});
		
		joinButton.setOnMouseClicked((MouseEvent e) -> {
			Main.changeManager(new GameManager("CLIENT", ipField.getText()));
			stg.close();
		}); 
	}
	
	@Override
	public void update() {
		
		background.update(counter);
		gameTitle.update(counter);
		
		// mouse over
		Pointable pointObject = this.getTopMostTargetAt(InputUtility.getMouseX(), InputUtility.getMouseY());
		if(pointObject == startButton) {
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("START");
				try {
					openGameType();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
