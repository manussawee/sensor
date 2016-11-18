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
	private Stage primaryStage;
	private Scene gameScene;
	private GameScreen gameScreen;
	private DefaultManager currentManager;
	
	@Override
	public void start(Stage primaryStage) {
		instance = this;
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("NONAME");
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		StartManager startManager = new StartManager(primaryStage, currentManager);
		currentManager = startManager;
		
		// create game scene
		this.gameScreen = new GameScreen();
		this.gameScene = new Scene(this.gameScreen);
		
		new AnimationTimer() {
            @Override
            public void handle(long now) {
            	currentManager.update();
            	gameScreen.paintComponent();
            }
        }.start();
        
		this.primaryStage.setScene(this.gameScene);
		this.primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public synchronized void changeScene(String sceneName) {
		//
	}
	
	public void drawGameScreen(){
		this.gameScreen.paintComponent();
	}
}
