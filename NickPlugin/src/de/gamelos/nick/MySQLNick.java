package de.gamelos.nick;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MySQLNick {

public static boolean playerExists(String spielername){
		
		
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = Main.mysql.querry("SELECT * FROM Nick WHERE Name = '"+ spielername + "'");
			
			if(rs.next()){
				return rs.getString("Name") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void createPlayer(String spielername){
		if(!(playerExists(spielername))){
			Main.mysql.update("INSERT INTO Nick(Name, Nick) VALUES ('" +spielername+ "', '0');");
		}
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static Integer getNick(String spielername){
		int i = 0;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Nick WHERE Name = '"+ spielername + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Nick")) == null));
				
				i = rs.getInt("Nick");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getNick(spielername);
		}
		return i;
	}
	
	
	
	
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
	public static void setNick(String spielername, String Nick){
		
		if(playerExists(spielername)){
			Main.mysql.update("UPDATE Nick SET Nick= '" + Nick+ "' WHERE Name= '" + spielername+ "';");
		}else{
			createPlayer(spielername);
			setNick(spielername, Nick);
		}
		
	}
	
}