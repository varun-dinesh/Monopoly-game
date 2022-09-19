package application;

import java.net.URL;
import java.sql.*;
import java.util.*;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.transform.Rotate;

public class board_controller implements Initializable{

	@FXML
	  Label dp1,dp2,dp3,dp4,amt1,amt2,amt3,amt4,to_roll,landed_on;
	@FXML
	ImageView dice1,dice2;
	@FXML
	Label pr1,pr2,pr3,pr4;
	@FXML
	Label cid2,cid4,cid6,cid7,cid9,cid10,cid12,cid13,cid14,cid15,cid16,cid17,cid19,cid20,cid22,cid24,cid25,cid26,cid27,cid28,cid29,cid30,cid32,cid33,cid35,cid36,cid38,cid40;
	@FXML
	Button buybtn,roll_dice_btn;

	
	 int[] player_pos = new int[5]; //array to store the current position of the 4 players
	 int turn = 1,move_no=0;			//turn to find which player should play the next turn
	int[] pos = new int[2];			//storing the x and y co-ordinates of a particular land
	int d1,d2,bought=0;
	String[] colors = {"","#02a2ff","#06ff27","#fb0202","#fffb02"};
	
	static String url = "jdbc:mysql://localhost:3306/business_db";
	static String uname = "root";
	static String password = "password";//ENTER YOUR MYSQL LOGIN PASSWORD
	
	
	public void roll_dice(ActionEvent event) {
		//if a land is not bought and the player landed on it doesn't want to buy it then they won't press the 
		//buy button so we are updating the turn to the next player since update_turn is not called in buy_land() function
		if(bought==1) {update_turn();bought=0;}	
		buybtn.setDisable(true);
		
		Random rand = new Random();
		RotateTransition rotate1 = new RotateTransition();		//animation for rotating the dice
		rotate1.setNode(dice1);
		rotate1.setAxis(Rotate.X_AXIS);
		rotate1.setByAngle(360);
		rotate1.setCycleCount(5);
		rotate1.play();
		d1 = rand.nextInt(5)+1;
		
		RotateTransition rotate2 = new RotateTransition();		//animation for rotating the dice
		rotate2.setNode(dice2);
		rotate2.setAxis(Rotate.X_AXIS);
		rotate2.setByAngle(360);
		rotate2.setCycleCount(5);
		rotate2.play();
		d2 = rand.nextInt(5)+1;

		//update dice images
		Image dice_img1 = new Image(getClass().getResourceAsStream("/application/images/dice"+d1+".png"));
		Image dice_img2 = new Image(getClass().getResourceAsStream("/application/images/dice"+d2+".png"));
		dice1.setImage(dice_img1);
		dice2.setImage(dice_img2);
		
		player_pos[turn] = (player_pos[turn]+d1+d2)%40;		//updating players position
		move_player();
		check_bankruptcy();
	}
	
	public void check_bankruptcy() {
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement b = con.createStatement();
			ResultSet br = b.executeQuery("SELECT pname,amount FROM players");
			while(br.next())
			{
				if(br.getInt(2)<=0)	//if any player doesn't have any money we end the game and display the winners
				{
					buybtn.setDisable(true);
					roll_dice_btn.setDisable(true);
					Statement b1 = con.createStatement();
					//selecting the top 3 players according to who has more money
					ResultSet res = b1.executeQuery("SELECT pname FROM players ORDER BY amount DESC LIMIT 3");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("game ended");
					String to_dis = br.getString(1)+" is bankrupt\nWinners\n";
					res.next();
					to_dis+="1st place "+res.getString(1);res.next();
					to_dis+="\n2nd place "+res.getString(1);res.next();
					to_dis+="\n3rd place "+res.getString(1);
					alert.setHeaderText(to_dis);
					alert.show();
					break;
				}
			}
		}catch(Exception e) {System.out.println(e);}
	}
	
	public void move_player() {
		pos = fetch_pos(player_pos[turn]+1);	//getting co ordinates to move the player to that position
		switch(turn) {
		case 1:pr1.relocate((double)pos[0],(double)pos[1]);
				update_owners(player_pos[1]+1,amt1);
				break;
		case 2:pr2.relocate((double)pos[0]+14, (double)pos[1]);
				update_owners(player_pos[2]+1,amt2);
				break;
		case 3:pr3.relocate((double)pos[0]+28, (double)pos[1]);
				update_owners(player_pos[3]+1,amt3);
				break;
		case 4:pr4.relocate((double)pos[0]+42, (double)pos[1]);
				update_owners(player_pos[4]+1,amt4);
				break;
		}
	}
	
	public void update_turn() {
		move_no+=1;
		try {
		//updating moves table
		Connection con = DriverManager.getConnection(url,uname,password);
		con.prepareStatement("INSERT INTO moves(move_no,pid,dice,lid) VALUES("+move_no+","+turn+","+(d1+d2)+","+(player_pos[turn]+1)+")").execute();
		
		if(d1!=d2) {
		if(turn+1>4)turn = 1;
		else turn += 1;
		}
		//to display the who's turn it is to play the next turn
		Statement d = con.createStatement();
		ResultSet d1 = d.executeQuery("SELECT pname FROM players WHERE pid="+turn);d1.next();
		to_roll.setText(d1.getString(1)+"'s turn");
		}
		catch(Exception e) {System.out.println(e+" error in inserting");}
	}
	
	public int[] fetch_pos(int p) {		//returns co-ordinates of the given position 
		int[] res = new int[2];
		
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement statement = con.createStatement();
			String query = "SELECT pos_x,pos_y FROM land WHERE lid="+p;		//selecting x and y co-ordinates from the database
			ResultSet result = statement.executeQuery(query);
			if(result.next()) {
			res[0] = result.getInt(1);
			res[1] = result.getInt(2);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return res;
	}
	
	public void display_color(int t,int p) {			//For displaying the color of the player who owns the land
		switch(p) {
		case 2:cid2.setStyle("-fx-background-color:"+colors[t]);break;
		case 4:cid4.setStyle("-fx-background-color:"+colors[t]);break;
		case 6:cid6.setStyle("-fx-background-color:"+colors[t]);break;
		case 7:cid7.setStyle("-fx-background-color:"+colors[t]);break;
		case 9:cid9.setStyle("-fx-background-color:"+colors[t]);break;
		case 10:cid10.setStyle("-fx-background-color:"+colors[t]);break;
		case 12:cid12.setStyle("-fx-background-color:"+colors[t]);break;
		case 13:cid13.setStyle("-fx-background-color:"+colors[t]);break;
		case 14:cid14.setStyle("-fx-background-color:"+colors[t]);break;
		case 15:cid15.setStyle("-fx-background-color:"+colors[t]);break;
		case 16:cid16.setStyle("-fx-background-color:"+colors[t]);break;
		case 17:cid17.setStyle("-fx-background-color:"+colors[t]);break;
		case 19:cid19.setStyle("-fx-background-color:"+colors[t]);break;
		case 20:cid20.setStyle("-fx-background-color:"+colors[t]);break;
		case 22:cid22.setStyle("-fx-background-color:"+colors[t]);break;
		case 24:cid24.setStyle("-fx-background-color:"+colors[t]);break;
		case 25:cid25.setStyle("-fx-background-color:"+colors[t]);break;
		case 26:cid26.setStyle("-fx-background-color:"+colors[t]);break;
		case 27:cid27.setStyle("-fx-background-color:"+colors[t]);break;
		case 28:cid28.setStyle("-fx-background-color:"+colors[t]);break;
		case 29:cid29.setStyle("-fx-background-color:"+colors[t]);break;
		case 30:cid30.setStyle("-fx-background-color:"+colors[t]);break;
		case 32:cid32.setStyle("-fx-background-color:"+colors[t]);break;
		case 33:cid33.setStyle("-fx-background-color:"+colors[t]);break;
		case 35:cid35.setStyle("-fx-background-color:"+colors[t]);break;
		case 36:cid36.setStyle("-fx-background-color:"+colors[t]);break;
		case 38:cid38.setStyle("-fx-background-color:"+colors[t]);break;
		case 40:cid40.setStyle("-fx-background-color:"+colors[t]);break;
		}
	}
	
	void display_updated_amount(int pid) {
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement s1 = con.createStatement();
			//select current balance amount to display
			ResultSet am = s1.executeQuery("SELECT amount FROM players WHERE pid="+pid);
			am.next();
			int amt = am.getInt(1);
			switch(pid) {							//displaying players's amount after updating
			case 1:amt1.setText(String.valueOf(amt));break;
			case 2:amt2.setText(String.valueOf(amt));break;
			case 3:amt3.setText(String.valueOf(amt));break;
			case 4:amt4.setText(String.valueOf(amt));break;
			}
		}catch(Exception e) {System.out.println(e);}
	}
	
	public void buy_land() {
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
				Statement s1 = con.createStatement();
				//getting land's buying amount from database
				ResultSet r = s1.executeQuery("SELECT buy FROM land where lid="+(player_pos[turn]+1));
				r.next();
				int l_amt = r.getInt(1);
				//getting players balance amount from database
				ResultSet r1 = s1.executeQuery("SELECT amount FROM players WHERE pid="+turn);
				r1.next();
				int p_amt = r1.getInt(1);
				if(l_amt<=p_amt)
				{
					con.prepareStatement("update players set amount=amount-"+l_amt+" WHERE pid="+turn).executeUpdate();//deduct amount from players balance
					s1.executeUpdate("INSERT INTO owners(pid,lid) values("+turn+","+(player_pos[turn]+1)+")");//update owners table
					display_updated_amount(turn);
					display_color(turn,player_pos[turn]+1);
				}
				r.close();
				r1.close();
				s1.close();
			buybtn.setDisable(true);
			update_turn();
			bought=0;
		}catch(Exception e) {
			System.out.println(e+"in buy land function");
		}
	}
	
	
	public void update_owners(int p,Label a) {
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement s = con.createStatement();
			ResultSet result = s.executeQuery("SELECT pid FROM owners WHERE lid="+p);
			boolean b = result.next();
			int own_pid=-1;
			//check if any owner exists of the particular land the player landed on if exists update 
			//own_pid variable
			if(b) {own_pid = result.getInt(1);}
			
			ResultSet result1 = s.executeQuery("SELECT rent,buy,lname FROM land WHERE lid="+p);
			result1.next();
			int rent = result1.getInt(1);
			int buy = result1.getInt(2);
			String land_name = result1.getString(3);
			result1 = s.executeQuery("SELECT pname FROM players where pid="+turn);result1.next();
			String player_name = result1.getString(1);
			
			if(!b) {	
				//if they land in either chance or community chest or free parking
				if(rent==0 || rent==Types.NULL) {
					if(land_name.equals("chance")){
						chance_controller(player_name);
						update_turn();
					}
					else if(land_name.equals("community chest")){
						community_controller(player_name);
						update_turn();
					}
					else {
					landed_on.setText(player_name+" landed in "+land_name);
					update_turn();
					}
				}
				else if(buy==0 || buy==Types.NULL)			//paying tax
				{
					con.prepareStatement("update players set amount=amount-"+rent+" where pid="+turn).executeUpdate();
					ResultSet am = s.executeQuery("SELECT amount FROM players WHERE pid="+turn);
					am.next();
					int amt = am.getInt(1);
					a.setText(String.valueOf(amt));
					landed_on.setText(player_name+" landed in "+land_name+"\nyou paid rs."+rent);
					update_turn();
				}
				else {							
					//no owners of the land so ask the player if he wants to buy the land
					landed_on.setText(player_name+" landed in "+land_name+"\ndo you want to buy it at rs."+buy);
					buybtn.setDisable(false);
					bought=1;
				}
			}
			else {
				if(own_pid!=turn) {
					//pay rent to the owner
					con.prepareStatement("update players set amount=amount-"+rent+" where pid="+turn).executeUpdate();
					con.prepareStatement("update players set amount=amount+"+rent+" where pid="+own_pid).executeUpdate();
					landed_on.setText(player_name+" landed in "+land_name+"\nyou paid rs."+rent+" as rent");
					ResultSet am = s.executeQuery("SELECT amount FROM players WHERE pid="+turn); am.next();
					int amt = am.getInt(1);
					a.setText(String.valueOf(amt));
					display_updated_amount(own_pid);
				}
				else {landed_on.setText(player_name+" landed on "+land_name);}
				update_turn();
			}
		}catch(Exception e) {
			System.out.println(e+" in update owner function");
		}
	}
	
	void chance_controller(String pname) {
	Random rand = new Random();
	int cno = rand.nextInt(5)+1; //select random task from chance database table
	
	try{
		Connection con = DriverManager.getConnection(url,uname,password);
		Statement s = con.createStatement();
		ResultSet t = s.executeQuery("SELECT task FROM chance WHERE id="+cno);
		t.next();
		String task = t.getString(1);
		landed_on.setText(pname+" landed in chance\n"+task);
		
		switch(cno) {
		case 1:player_pos[turn]=30;
				move_player();
				break;
		case 2:con.prepareStatement("update players set amount=amount+"+150+" where pid="+turn).executeUpdate();
				break;
		case 3:con.prepareStatement("update players set amount=amount-"+100+" where pid="+turn).executeUpdate();
				break;
		case 4:con.prepareStatement("update players set amount=amount+"+50+" where pid="+turn).executeUpdate();
				break;
		case 5:con.prepareStatement("update players set amount=amount-"+30+" where pid="+turn).executeUpdate();
				break;
		case 6:con.prepareStatement("update players set amount=amount+"+500+" where pid="+turn).executeUpdate();
				break;
		}
		display_updated_amount(turn);
	}catch(Exception e) {System.out.println(e);}
	}
	
	void community_controller(String pname) {
		Random rand = new Random();
		int cno = rand.nextInt(5)+1; //select random task from community_chest database table
		try{
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement s = con.createStatement();
			ResultSet t = s.executeQuery("SELECT task FROM community_chest WHERE id="+cno);
			t.next();
			String task = t.getString(1);
			landed_on.setText(pname+" landed in community_chest\n"+task);
			
			switch(cno) {
			case 1:con.prepareStatement("update players set amount=amount+"+500+" where pid="+turn).executeUpdate();
					break;
			case 2:con.prepareStatement("update players set amount=amount-"+800+" where pid="+turn).executeUpdate();
					break;
			case 3:con.prepareStatement("update players set amount=amount+"+45+" where pid="+turn).executeUpdate();
					break;
			case 4:con.prepareStatement("update players set amount=amount+"+1000+" where pid="+turn).executeUpdate();
					break;
			case 5:con.prepareStatement("update players set amount=amount-"+100+" where pid="+turn).executeUpdate();
					break;
			case 6:con.prepareStatement("update players set amount=amount+"+75+" where pid="+turn).executeUpdate();
					break;
			}
			display_updated_amount(turn);
		}catch(Exception e) {System.out.println(e);}
	}
	
	@Override
	 public void initialize(URL arg0, ResourceBundle arg1) {
		dp1.setText(controller.name1);
		dp2.setText(controller.name2);
		dp3.setText(controller.name3);
		dp4.setText(controller.name4);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println(e);
		}
		try {
			Connection con = DriverManager.getConnection(url,uname,password);
			Statement d = con.createStatement();
			ResultSet d1 = d.executeQuery("SELECT pname FROM players WHERE pid="+turn);d1.next();
			to_roll.setText(d1.getString(1)+"'s turn");
		}catch(Exception e) {System.out.println(e);}
		
	}
}

