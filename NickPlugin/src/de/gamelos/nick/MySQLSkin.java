package de.gamelos.nick;

import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("static-access")
public class MySQLSkin {
public static boolean playerExists(String uuid){
		try {
			ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE UUID = '"+ uuid + "'");
			
			if(rs.next()){
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

public static boolean nameExists(String uuid){
	try {
		ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE name = '"+ uuid + "'");
		
		if(rs.next()){
			return rs.getString("name") != null;
		}
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}

public static boolean valueexists(String id){
	try {
		ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE value = '"+ id + "'");
		
		if(rs.next()){
			return rs.getString("value") != null;
		}
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
	
public static void createPlayer(String uuid, String name, String value, String signature, String id){
	if(!(playerExists(uuid))){
		Main.mysql.update("INSERT INTO Skins(UUID, name, value, signature, id) VALUES ('" +uuid+ "', '"+name+"', '"+value+"', '"+signature+"', '"+id+"');");
	}
}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static String getname(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("name")) == null));
				
				i = rs.getString("name");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	public static String getvalue(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("value")) == null));
				
				i = rs.getString("value");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	public static String getSignature(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("signature")) == null));
				
				i = rs.getString("signature");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	public static String getid(String uuid){
		String i = "";
		if(playerExists(uuid)){
			try {
				ResultSet rs = Main.mysql.querry("SELECT * FROM Skins WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("id")) == null));
				
				i = rs.getString("id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
public static void setname(String uuid, String name){
		
		if(playerExists(uuid)){
			Main.mysql.update("UPDATE Skins SET name= '" + name+ "' WHERE UUID= '" + uuid+ "';");
		}else{
		}
	}

public static void setvalue(String uuid, String value){
	
	if(playerExists(uuid)){
		Main.mysql.update("UPDATE Skins SET value= '" + value+ "' WHERE UUID= '" + uuid+ "';");
	}else{
	}
}
public static void setsignature(String uuid, String signature){
	
	if(playerExists(uuid)){
		Main.mysql.update("UPDATE Skins SET signature= '" + signature+ "' WHERE UUID= '" + uuid+ "';");
	}else{
	}
}

public static void setid(String uuid, String id){
	
	if(playerExists(uuid)){
		Main.mysql.update("UPDATE Skins SET id= '" + id+ "' WHERE UUID= '" + uuid+ "';");
	}else{
	}
}

public static void removeSpieler(String UUID){
	Main.mysql.update("DELETE FROM Skins WHERE UUID = '"+UUID+"'");
}
}
