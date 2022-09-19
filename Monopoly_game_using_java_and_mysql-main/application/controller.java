package application;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class controller  {
	
	@FXML
	private TextField p1,p2,p3,p4;
	@FXML
	private Button start_btn;

	
	static String name1,name2,name3,name4;
	private Stage stage_board;
	private Scene scene_board;
	
	String url = "jdbc:mysql://localhost:3306/business_db";
	String uname = "root";
	String password = "password";//ENTER YOUR MYSQL LOGIN PASSWORD
	
	public void insert_name(String[] names) {						//INSERT NAMES INTO THE DATABASE
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println(e);
		}
		
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			String query1 = "TRUNCATE owners";			//DELETE PREVIOUS GAME'S OWNERS
			String query2 = "TRUNCATE moves";			//DELETE PREVIOUS GAME'S MOVES
			String query3 = "DELETE FROM  players";		//DELETE PREVIOUS GAME'S PLAYERS
			String query4 = "INSERT INTO players(pid,pname,color,amount) VALUES(?,?,?,10000)";
			con.prepareStatement(query1).execute();
			con.prepareStatement(query2).execute();
			con.prepareStatement(query3).execute();
			String[] colors = {"","BLUE","GREEN","RED","YELLOW"};
			PreparedStatement statement = con.prepareStatement(query4);
			for(int i=1;i<=4;i++)
			{
				statement.setInt(1, i);
				statement.setString(2, names[i-1]);
				statement.setString(3, colors[i]);
				statement.addBatch();
			}
			statement.executeBatch();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void start_game(ActionEvent event) throws IOException {
		name1 = p1.getText();											//GETTING THE PLAYERS NAME
		name2 = p2.getText();
		name3 = p3.getText();
		name4 = p4.getText();
		
		if(name1=="" || name2=="" || name3=="" || name4=="") {			//TO CHECK IF ANY PLAYER NAME IS NOT ENTERED
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Name not entered");
			alert.setHeaderText("please make sure all the player names have been entered");
			alert.show();
		}
		else {
			String[] names = {name1,name2,name3,name4};
			insert_name(names);
			try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/board.fxml"));//GIVE THE PATH OF THE BOARD.FXML FILE IN YOUR PC
			stage_board = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene_board = new Scene(root);
			stage_board.setScene(scene_board);			
		}catch(Exception e)
			{System.out.println(e);
			}
	}
	}
}

