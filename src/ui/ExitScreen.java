package ui;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ExitScreen extends Alert {

	public ExitScreen() {
		super(AlertType.CONFIRMATION);
		this.setTitle("EXITING CONFIRMATION");
		this.setHeaderText(null);
		this.setGraphic(null);
		this.setContentText("ARE YOU SURE TO LEAVE THE GAME?");

		Platform.runLater(() -> {
			Optional<ButtonType> result = this.showAndWait();
			if (result.get() == ButtonType.OK){
			    System.exit(0);
			} else {
			    this.close();
			}
		});
	}
}
