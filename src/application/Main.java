package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lib.DefaultManager;
import logic.StartManager;
import javafx.scene.Scene;
import ui.GameScreen;

public class Main extends Application {
	
	public static Main instance;
	private static Stage primaryStage;
	private static Scene gameScene;
	private static GameScreen gameScreen;
	private static DefaultManager currentManager;
	public static AnimationTimer animationTimer;
	
	@Override
	public void start(Stage primaryStage) {
		instance = this;
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("STARBRAWL");
		Main.primaryStage.setResizable(false);
		Main.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		animationTimer = new AnimationTimer() {
           
			@Override
            public void handle(long now) {
				currentManager.update();
            	gameScreen.paintComponent();
            }
        };
		
        StartManager startManager = new StartManager();
        currentManager = startManager;
		
		// create game scene
		Main.gameScreen = new GameScreen();
		Main.gameScene = new Scene(Main.gameScreen);
		Main.gameScreen.requestFocusForCanvas();
        
        animationTimer.start();
        
		Main.primaryStage.setScene(Main.gameScene);
		Main.primaryStage.show();
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		Main.primaryStage = primaryStage;
	}

	public static void changeManager(DefaultManager manager) {
		currentManager = manager;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void drawGameScreen(){
		gameScreen.paintComponent();
	}

	public static DefaultManager getCurrentManager() {
		// TODO Auto-generated method stub
		return currentManager;
	}
	
}
