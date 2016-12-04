package ui;

import application.Main;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.GameManager;

public class ConnectionScreen extends Stage {

	public ConnectionScreen() {
		super();
		this.setTitle("CREATING MATCH");
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
		this.setScene(scene);
		
		Platform.runLater(() -> {
			this.showAndWait();
		});
		
		hostButton.setOnMouseClicked((MouseEvent e) -> {
			Main.changeManager(new GameManager("SERVER", null));
			this.close();
		});
		
		joinButton.setOnMouseClicked((MouseEvent e) -> {
			Main.changeManager(new GameManager("CLIENT", ipField.getText()));
			this.close();
		}); 
	}
}
