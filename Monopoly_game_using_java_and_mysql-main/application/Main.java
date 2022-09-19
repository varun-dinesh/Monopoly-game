package application;
	
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.Image;
import java.sql.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/proj.fxml"));//GIVE THE PATH OF THE PROJ.FXML FILE IN YOUR PC
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Business Board Game");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/icon.png")));//GIVE THE PATH OF THE ICON.PNG FILE IN YOUR PC
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		launch(args);
	}
}
