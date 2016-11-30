package logic;

import application.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
import sun.font.TrueTypeFont;

public class StartManager extends DefaultManager {
	
	private Stage primaryStage;
	private Background background;
	private GameText gameTitle;
	private GameButton startButton;
	private GameButton settingsButton;
	private GameButton exitButton;
	
	private int counter;
	
	public StartManager() {
		
		IRenderableHolder.getInstance().getEntities().clear();
		
		this.primaryStage = Main.getPrimaryStage();
		
		background = new Background(0);
		gameTitle = new GameText("NONAME", ConfigurableOption.screenWidth / 2, 100, 1, 0, 100, Color.ORANGE, Color.WHITE);
		startButton = new GameButton("PLAY", 50, 285, 1, 200, 75, 3, 75, Color.WHITE, Color.BLACK);
		settingsButton = new GameButton("SETTINGS", 50, 360, 1, 400, 75, 3, 75, Color.WHITE, Color.BLACK);
		exitButton = new GameButton("EXIT", 50, 435, 1, 180, 75, 3, 75, Color.WHITE, Color.BLACK);
		
		counter = 0;
		IRenderableHolder.getInstance().add(background);
		IRenderableHolder.getInstance().add(gameTitle);
		IRenderableHolder.getInstance().add(startButton);
		IRenderableHolder.getInstance().add(settingsButton);
		IRenderableHolder.getInstance().add(exitButton);
		IRenderableHolder.getInstance().sort();
	}
	
	public void openGameType() throws Exception {
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(5));
		
		Label ipLabel = new Label("IP ADDRESS");
		ipLabel.setPadding(new Insets(5));
		mainPane.add(ipLabel, 1, 1);
		
		TextField ipField = new TextField();
		ipField.setPrefWidth(150);
		mainPane.add(ipField, 2, 1);
		
		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.CENTER);
		subPane.setPadding(new Insets(5));
		mainPane.add(subPane, 3, 1);
		
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
		
		// mouse over
		Pointable pointObject = this.getTopMostTargetAt(InputUtility.getMouseX(), InputUtility.getMouseY());
		if(pointObject == startButton) {
			startButton.setPoint(true, counter);
			settingsButton.setPoint(false, counter);
			exitButton.setPoint(false, counter);
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
		else if(pointObject == settingsButton) {
			settingsButton.setPoint(true, counter);
			startButton.setPoint(false, counter);
			exitButton.setPoint(false, counter);
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("TUTORIAL");
			}
		}
		else if(pointObject == exitButton) {
			exitButton.setPoint(true, counter);
			startButton.setPoint(false, counter);
			settingsButton.setPoint(false, counter);
			// click
			if(InputUtility.isMouseLeftDown()) {
				System.out.println("EXIT");
				primaryStage.close();
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
