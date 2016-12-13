// settings screen

package ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lib.ConfigurableOption;

public class OptionsScreen extends Stage {
	
	private GridPane mainPane;
	private Label[] keys;
	private Button[] buttons;
	private String currentKeyName;
	private Label currentKeyLabel;
	private Button closeButton;
	private Scene scene;
	
	public OptionsScreen() {
		super();
		this.setTitle("OPTIONS");
		mainPane = new GridPane();
		mainPane.setVgap(10);
		mainPane.setHgap(10);
		mainPane.setPadding(new Insets(10));
		
		currentKeyName = "";
		currentKeyLabel = new Label();
		keys = new Label[7];
		buttons = new Button[7];
		addKey("UP", ConfigurableOption.up, 0);
		addKey("LEFT", ConfigurableOption.left, 1);
		addKey("DOWN", ConfigurableOption.down, 2);
		addKey("RIGHT", ConfigurableOption.right, 3);
		addKey("1st SKILL", ConfigurableOption.firstSkill, 4);
		addKey("2nd SKILL", ConfigurableOption.secondSkill, 5);
		addKey("ULTIMATE SKILL", ConfigurableOption.ultimateSkill, 6);
		
		closeButton = new Button("CLOSE");
		closeButton.setPrefWidth(75);
		mainPane.add(closeButton, 2, 7);
		
		scene = new Scene(mainPane);
		this.setScene(scene);
		
		Platform.runLater(() -> {
			this.showAndWait();
		});
		
		addAction();
	}
	
	private void addAction() {
		closeButton.setOnMouseClicked((MouseEvent event) -> {
			super.close();
		});
		
		scene.setOnKeyPressed((KeyEvent keyEvent) -> {
			if(!keyEvent.getCode().toString().equals("ESCAPE")) {
				ConfigurableOption.setKey(currentKeyName, keyEvent.getCode());
				currentKeyLabel.setText(keyEvent.getCode().toString());
			}
			currentKeyLabel.setStyle("-fx-background-color: #00FF00;");
			currentKeyLabel = new Label();
			currentKeyName = "";
			enable();
		});
	}
	
	private void addKey(String keyName, KeyCode keyCode, int row) {
		
		Label label = new Label(keyName + " KEY");
		Label key = new Label(keyCode.toString());
		key.setAlignment(Pos.BASELINE_RIGHT);
		key.setPrefWidth(120);
		key.setStyle("-fx-background-color: #00FF00;");
		Button button = new Button("CHANGE");
		button.setPrefWidth(75);
		
		keys[row] = key;
		buttons[row] = button;
		
		mainPane.add(label, 0, row);
		mainPane.add(key, 1, row);
		mainPane.add(button, 2, row);
		
		button.setOnMouseClicked((MouseEvent mouseEvent) -> {
			disable();
			key.setStyle("-fx-background-color: #22F;");
			currentKeyName = keyName;
			currentKeyLabel = key;
		}); 
	}
	
	private void disable() {
		for(Button button : buttons) {
			button.setDisable(true);
		}
	}
	
	private void enable() {
		for(Button button : buttons) {
			button.setDisable(false);
		}
	}
}
