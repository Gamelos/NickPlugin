package de.gamelos.nick;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.gamelos.PermissionsAPI.PermissionsAPI;
import de.gamelos.jaylosapi.Claninfo;
import de.gamelos.jaylosapi.JaylosAPI;
import de.gamelos.jaylosapi.PartyID;
import de.gamelos.jaylosapi.SQLParty;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{

	public static Field nameField;
	
	@Override
	public void onEnable() {
		System.out.println("[Nick] Das Plugin wurde geladen!");
		nameField = Main.getField(GameProfile.class, "name");
		Bukkit.getPluginManager().registerEvents(this, this);
		getCommand("nick").setExecutor(this);
		ConnectMySQL();
		setskins();
		super.onEnable();
	}

	public static MySQL mysql;
	private void ConnectMySQL(){
		mysql = new MySQL(JaylosAPI.gethost(), JaylosAPI.getuser(), JaylosAPI.getdatabase(), JaylosAPI.getpassword());
		mysql.update("CREATE TABLE IF NOT EXISTS Skins(UUID varchar(64),name varchar(64), value varchar(2000), signature varchar(2000), id varchar(2000));");
	}
	
	public static Field getField(Class<?>clazz,String name){
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException |SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void onDisable() {
		System.out.println("[Nick] Das Plugin wurde deaktiviert!");
		super.onDisable();
	}
	

	
	public static boolean tonick(Player p) {
		if(MySQLNick.playerExists(p.getUniqueId().toString())){
			if(MySQLNick.getNick(p.getUniqueId().toString()) == 1){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public static boolean nowtonick = false;
	
	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(JaylosAPI.rang || nowtonick) {
			if(tonick(p)) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						nick(p);
					}
				}, 2);
		}else {
			for(Player pp: partylist.keySet()) {
				if(partylist.get(pp).contains(geteffectivname(p))){
//					===================================
					String value;
					String signature;
					if(realskinsignature.containsKey(pp)) {
					value = realskinvalue.get(pp);
					signature = realskinsignature.get(pp);
					}else {
					value = "eyJ0aW1lc3RhbXAiOjE1MzQ0NTQxNDA1NTAsInByb2ZpbGVJZCI6ImI0NmYxM2MzZWYxYzRkZDhhMjc2OWRhNDRlNDY1YjA0IiwicHJvZmlsZU5hbWUiOiJHYW1lbG9zIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OTE4MWIwNWE5ODE4OTE0NjhiY2FiNTdmYmIyMzM5MjAzYThlYTlhOTUxYzkzMjNmZmNiNGVkYTc3NjgyMjg4In19fQ==";
					signature = "k+RSt668ydmocz0COUfdtz0bnFrE0F1gyq93VXrxT5car+G4/LA0fCJ13v0B46UvwaUddpOM1CcP6Uis8VQC1AG6f6Q7VmgeD2K5KVmBLpWamw3vkEvPJes/i/iY4GzPnxlnOiBAz0GsgHvGKg7Ihm784AR2YoDZxrC5cAp0+7fuWYW8G2uGcdF9ak/FKrt7eupFuGyTRmirn2LBm0O3rSjutmmtWBwFIlFDcA06muY3hn7JY6wrviw6eHwuZBsqdoWsrpaFyzHJRanLiyGK1dpdtNqJAeTuL1ipFdhS1sbe8OX1ZHadX7fu/cDQmOlSGjmTFUwAJzsbgfmXCoSbkeKv85FaImpMpMbh7So8e9EN7vAtk0BNGmGlg4pIdt8T2Rs9iYjaBPTno8/Henqt1MGECDGjs9HPEeysNWPEPK0iKotDUrf4p4PnymHAQPK6v9zCY4a/drBhuWfzR+oUOoj6Qq5pYskAzCBIC/SR9JrjxhF5Twg8yAf+t0a5Uh/urv4E3CyNd+UfOf2wXnvtQRB4BaBv2eXavlVxWe/+lx6m88eRb4a8h3aaaFkVpzk+CzemB0sX8Faw/QHt/utIXkxuX+R8FPB9rD78HkYJQL2xfodOys7GKyOqeU5zUOdUH0TE8aX1idk1ofq0RIltzAI3+/7IoYr9J7NKYKXr45A=";
					}
					
					String name = "textures";
					GameProfile profile = ((CraftPlayer)pp).getProfile();
					try {
						nameField.set(profile, getrealname(pp.getUniqueId().toString()));
					} catch (IllegalArgumentException |IllegalAccessException e1) {
						e1.printStackTrace();
					}
					profile.getProperties().clear();
					profile.getProperties().put(name, new Property(name,value,signature));
					p.hidePlayer(pp);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							p.showPlayer(pp);
						}
					}, 2);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							JaylosAPI.updaterang();
						}
						
					}, 5);
//					===================================
				}else {
//					===================================
					String value = nickskinvalue.get(pp);
					String signature = nickskinsignature.get(pp);
					String name = "textures";
					GameProfile profile = ((CraftPlayer)pp).getProfile();
					try {
						nameField.set(profile, nickedplayer.get(pp));
					} catch (IllegalArgumentException |IllegalAccessException e1) {
						e1.printStackTrace();
					}
					profile.getProperties().clear();
					profile.getProperties().put(name, new Property(name,value,signature));
					p.hidePlayer(pp);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							p.showPlayer(pp);
						}
					}, 2);
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						@Override
						public void run() {
							JaylosAPI.updaterang();
						}
						
					}, 5);
//					===================================
				}
			}
		}
		}
	}
	
	public static HashMap<Player,List<String>> partylist = new HashMap<>();
	public static HashMap<Player,String> nickedplayer = new HashMap<>();
	public static HashMap<String,String> realname = new HashMap<>();
	public static HashMap<String,String> realnameonuuid = new HashMap<>();
	
	public static String getrealname(String uuid) {
		if(realnameonuuid.containsKey(uuid)) {
			return realnameonuuid.get(uuid);
		}else {
			return null;
		}
	}
	
	public static List<String> getpartylist(Player p){
		List<String> list = new ArrayList<>();
		if(partylist.containsKey(p)) {
			for(String ss : partylist.get(p)) {
				if(realname.containsKey(ss)) {
					list.add(realname.get(ss));
				}else {
					list.add(ss);
				}
			}
		}
		return list;
	}
	public static List<String> getplayerswhonottonick(Player p){
		List<String> playerstononick = new ArrayList<>();
		if(PartyID.playerExists(p.getUniqueId().toString())){
			playerstononick = JaylosAPI.getparty(p);
		}
		return playerstononick;
	}
	
//	==========================
	public static HashMap<Player,String> realskinvalue = new HashMap<>();
	public static HashMap<Player,String> realskinsignature = new HashMap<>();
	public static HashMap<Player,String> nickskinvalue = new HashMap<>();
	public static HashMap<Player,String> nickskinsignature = new HashMap<>();
//	==========================
	
	@EventHandler
	public void onquit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(partylist.containsKey(p)) {
			partylist.remove(p);
			nickedplayer.remove(p);
			realnameonuuid.remove(p);
		}
	}
	
	
	public void nick(Player p) {
//		????????????
		String nickname = randomnickname();
		p.sendMessage(" ");
		p.sendMessage(ChatColor.GRAY+"Nickname: "+ChatColor.YELLOW+nickname);
		p.sendMessage(" ");
		partylist.put(p, getplayerswhonottonick(p));
		nickedplayer.put(p, nickname);
		realname.put(nickname, p.getName());
		realnameonuuid.put(p.getUniqueId().toString(), p.getName());
//		??????????????
		p.sendMessage(Prefix+ChatColor.GRAY+"Der Skin wird geladen...");
		if(MySQLSkin.playerExists(p.getUniqueId().toString())) {
			p.sendMessage(Prefix+ChatColor.GREEN+"Der Skin wurde erfolgreich geladen!");
			realskinsignature.put(p, MySQLSkin.getSignature(p.getUniqueId().toString()));
			realskinvalue.put(p, MySQLSkin.getvalue(p.getUniqueId().toString()));
		}else {
			p.sendMessage(Prefix+ChatColor.RED+"Der Skin konnte nicht geladen werden!");
		}
//		??????????????
		p.setDisplayName(nickname);
		int id = getradomskinid();
		String value = nickskinvalues.get(id);
		String signature = nickskinsignatures.get(id);
		String name = "textures";
		nickskinvalue.put(p, value);
		nickskinsignature.put(p, signature);
		GameProfile profile = ((CraftPlayer)p).getProfile();
		try {
			nameField.set(profile, nickname);
		} catch (IllegalArgumentException |IllegalAccessException e) {
			e.printStackTrace();
		}
		profile.getProperties().clear();
		profile.getProperties().put(name, new Property(name,value,signature));
		
		List<String> nonicklist = getpartylist(p);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for(Player pp:Bukkit.getOnlinePlayers()) {
					if(pp!=p) {
						if(!nonicklist.contains(geteffectivname(pp))) {
						pp.hidePlayer(p);
						}
					}
				}
			}
			
		}, 1);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for(Player pp:Bukkit.getOnlinePlayers()) {
					if(pp!=p) {
						if(!nonicklist.contains(geteffectivname(pp))) {
						pp.showPlayer(p);
						}
					}
				}
			}
			
		}, 5);
		
		Bukkit.getPluginManager().callEvent(new PlayerNickEvent(p));
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				JaylosAPI.updaterang();
			}
			
		}, 8);
	}
	
	
	public static String randomnickname(){
		String s = "";
		List<String> list = new ArrayList<>();
		list.add("vapesz");
		list.add("_Andrik740_");
		list.add("Takqshi");
		list.add("ImInLoveWithCoco");
		list.add("zabidon");
		list.add("SuquinUS_");
		list.add("OhSad_");
		list.add("mqrshall");
		list.add("LiebeSie");
		list.add("iTzWizardCraft");
		list.add("WhoTookMyUnicorn");
		list.add("toyd");
		list.add("HetIsSTORM");
		list.add("Sainou");
		list.add("Thankies");
		list.add("Zuhlo");
		list.add("IchKlauSupremee");
//		
		list.add("MAEDANDAYO");
		list.add("TheBlackSurvivor");
		list.add("OinkCraft");
		list.add("Fully800");
		list.add("iUGBR");
		list.add("Fayzie1101");
		list.add("adidisiti");
		list.add("Mazduhz");
		list.add("Peurrpull");
		list.add("TristenzMincraft");
		list.add("KatziiiMit3iii");
		list.add("Frostbxtten");
		list.add("jellyjam34");
		list.add("Tyrant1717");
		list.add("KayJS");
		list.add("MikeyK65");
		for(Player pp:nickedplayer.keySet()){
			String ss = nickedplayer.get(pp);
			list.remove(ss);
		}
		Random r = new Random();
		int i = r.nextInt(list.size());
		s = list.get(i);
		return s;
	}
	
	public static String geteffectivname(Player p) {
		if(de.gamelos.nick.Main.nickedplayer.containsKey(p)) {
			return de.gamelos.nick.Main.getrealname(p.getUniqueId().toString());
		}else {
			return p.getName();
		}
	}
	
	
	public static  void unnick(Player pp) {
		pp.sendMessage(Prefix+ChatColor.GREEN+"Du wurdest erfolgreich entnickt!");
		for(Player p:Bukkit.getOnlinePlayers()) {
			String value;
			String signature;
			if(realskinsignature.containsKey(pp)) {
			value = realskinvalue.get(pp);
			signature = realskinsignature.get(pp);
			}else {
			value = "eyJ0aW1lc3RhbXAiOjE1MzQ0NTQxNDA1NTAsInByb2ZpbGVJZCI6ImI0NmYxM2MzZWYxYzRkZDhhMjc2OWRhNDRlNDY1YjA0IiwicHJvZmlsZU5hbWUiOiJHYW1lbG9zIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83OTE4MWIwNWE5ODE4OTE0NjhiY2FiNTdmYmIyMzM5MjAzYThlYTlhOTUxYzkzMjNmZmNiNGVkYTc3NjgyMjg4In19fQ==";
			signature = "k+RSt668ydmocz0COUfdtz0bnFrE0F1gyq93VXrxT5car+G4/LA0fCJ13v0B46UvwaUddpOM1CcP6Uis8VQC1AG6f6Q7VmgeD2K5KVmBLpWamw3vkEvPJes/i/iY4GzPnxlnOiBAz0GsgHvGKg7Ihm784AR2YoDZxrC5cAp0+7fuWYW8G2uGcdF9ak/FKrt7eupFuGyTRmirn2LBm0O3rSjutmmtWBwFIlFDcA06muY3hn7JY6wrviw6eHwuZBsqdoWsrpaFyzHJRanLiyGK1dpdtNqJAeTuL1ipFdhS1sbe8OX1ZHadX7fu/cDQmOlSGjmTFUwAJzsbgfmXCoSbkeKv85FaImpMpMbh7So8e9EN7vAtk0BNGmGlg4pIdt8T2Rs9iYjaBPTno8/Henqt1MGECDGjs9HPEeysNWPEPK0iKotDUrf4p4PnymHAQPK6v9zCY4a/drBhuWfzR+oUOoj6Qq5pYskAzCBIC/SR9JrjxhF5Twg8yAf+t0a5Uh/urv4E3CyNd+UfOf2wXnvtQRB4BaBv2eXavlVxWe/+lx6m88eRb4a8h3aaaFkVpzk+CzemB0sX8Faw/QHt/utIXkxuX+R8FPB9rD78HkYJQL2xfodOys7GKyOqeU5zUOdUH0TE8aX1idk1ofq0RIltzAI3+/7IoYr9J7NKYKXr45A=";
			}
		String name = "textures";
		GameProfile profile = ((CraftPlayer)pp).getProfile();
		try {
			nameField.set(profile, getrealname(pp.getUniqueId().toString()));
		} catch (IllegalArgumentException |IllegalAccessException e1) {
			e1.printStackTrace();
		}
		profile.getProperties().clear();
		profile.getProperties().put(name, new Property(name,value,signature));
			p.hidePlayer(pp);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Nick"), new Runnable() {
				@Override
				public void run() {
					if(p.getAllowFlight()) {
					p.showPlayer(pp);
					}
				}
			}, 2);
		}
//		00000000000000000000000000
//		????????????
		partylist.remove(pp);
		nickedplayer.remove(pp);
		realnameonuuid.remove(pp);
//		??????????????
	}

	public static String Prefix = ChatColor.GRAY+"["+ChatColor.DARK_PURPLE+"Nick"+ChatColor.GRAY+"] ";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(cmd.getName().equalsIgnoreCase("nick")) {
		Player pp = (Player) sender;
		if(pp.hasPermission("system.nick")) {
			if(!nickedplayer.containsKey(pp)) {
				pp.sendMessage(Prefix+ChatColor.RED+"Du bist nicht genickt!");
			}else {
		
			for(Player p:Bukkit.getOnlinePlayers()) {
//				00000000000000000000000000
				if(!realskinsignature.containsKey(pp)) {
					pp.sendMessage(Prefix+ChatColor.RED+"Du kannst dich nicht entnicken, da dein Skin nicht erfolgreich geladen wurde!");
				}else {
				String value = realskinvalue.get(pp);
				String signature = realskinsignature.get(pp);
				String name = "textures";
				GameProfile profile = ((CraftPlayer)pp).getProfile();
				try {
					nameField.set(profile, getrealname(pp.getUniqueId().toString()));
				} catch (IllegalArgumentException |IllegalAccessException e1) {
					e1.printStackTrace();
				}
				profile.getProperties().clear();
				profile.getProperties().put(name, new Property(name,value,signature));
				if(p.getAllowFlight()) {
					if(pp.getAllowFlight()) {
					p.hidePlayer(pp);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Nick"), new Runnable() {
						@Override
						public void run() {
							if(pp.getAllowFlight()) {
								if(p.getAllowFlight()) {
									p.showPlayer(pp);	
								}
							}else {
							p.showPlayer(pp);
							}
						}
					}, 2);
					}
				}else {
				p.hidePlayer(pp);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Nick"), new Runnable() {
					@Override
					public void run() {
						if(pp.getAllowFlight()) {
							if(p.getAllowFlight()) {
								p.showPlayer(pp);	
							}
						}else {
						p.showPlayer(pp);
						}
					}
				}, 2);
				}
//				00000000000000000000000000
//				????????????
				partylist.remove(pp);
				nickedplayer.remove(pp);
				realnameonuuid.remove(pp);
//				??????????????
			Bukkit.getPluginManager().callEvent(new unNickEvent(pp));
			}
			}
			if(realskinsignature.containsKey(pp)) {
				pp.sendMessage(Prefix+ChatColor.GREEN+"Du wurdest erfolgreich entnickt!");
			}
			}
		}
	}
		return super.onCommand(sender, cmd, label, args);
	}
	
	
	
	public static HashMap<Integer,String> nickskinvalues = new HashMap<>();
	public static HashMap<Integer,String> nickskinsignatures = new HashMap<>();
	public static void setskins() {
		nickskinvalues.put(0, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzYzOTA1MTcsInByb2ZpbGVJZCI6ImNkZDg1Nzg1MmFmOTRiZDQ4ZGRiZWY0ZjEwNTRjMjhkIiwicHJvZmlsZU5hbWUiOiJBUjBaIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lZWMyOWIyNWI2NTEyNWU4ZTM2NjEyMmE4NmU2NjNmZjJlOWM3OTI4MWQ0NmQyMTI1YTNhZmExMjliNjE3OGU1IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=");
		nickskinsignatures.put(0, "pI54xloOnfkl9/U7goPLtgrciRODlS7Epq/eK9LrkmgfZW8jPtCcROQpQcSpS2KAKaULSP9UdtomDeOse9x/DAS/PZs9MjSvgjoNV6cMEWmdBo/CnMsG8QPesxYDyRmiryxTPyKa2HRavowxaI7eetEnKc49zEV9ktqfej3AQ8eGxeN3oVtKCmmKQEBwQBVDRXGz4V5q8thk7ESDe25rMIr3tWIvrZPgY3sx9NFBGNTgV+k767AJ5iQjqx164yFGDUFm0ePmoUMBf2Il22GtPezW1bJk+ZVlnv2bljPKGvp8GJg0EYsfEkqSW8mZv6JwsyDkFL8Y+VPyazORBiqCllwk0ezQQB52a3bDe+sSveruc1OEbr5whihSEI4e94zPpZ7ITWo8UUyw62bWn+065Tq5Ga6Js2/zbpd4cmgUGawEeiPA69MwfPka1yUA4HHG667/HaIO6485GnXSYQAmL+tXFoecScdwDOu8ogMyu18A6zT1UjcqwVz/GD6QCe1Fk2VOAmYRA8mnR42TpYTCbHhpR7bQBji9LgUbKLk6ZiygxOsH+0boA+0veD0gTQ8DAwEoeemmX9XalDpwNenhKIZIGLyg/wywt2VvBe7oCNQ84BPYabHIBVshsiAQOV0Ma5uwbgOfo+FchXsim+dukAUIrBfC+WeB3ZG2UNz4Dz4=");
		nickskinvalues.put(1, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY1Mjc0MTYsInByb2ZpbGVJZCI6ImJiZjhiN2EyZDhkYzQxMGI5MDJlNGZjNzliYzlmNDIyIiwicHJvZmlsZU5hbWUiOiIxMjN4MTIzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YjdlMmJiY2ViYjNlNGRjNTQxNTgwZmIyYTJlNDIwZTYxNWFjN2I1MzNkYjYxMmM0MmY1OTcxZDg2NDk2Zjg2IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=");
		nickskinsignatures.put(1, "OgoTGi/0Gi0YXHJ6f5ekaEjQ5C8vTehaocXbf0A9Qw2jNJHYDCfKELQDdaeDQmNu2PZvNUUcWluSXaxBWLdL/dj7eQccZ5IOedjl9e2yHYJVz5Tnh9uY0cldj4qnzoBZRlGTEcWY0WOWmA6pG8G4Dx3k0D3MIoisBzNslW0RC6OGToTqzfjtHrZ+zwNLAOPaYfki9QUiByk3RLDt3KB/UVHGsQh/RwolKTHNEnDrSqYbXrYxffWS+cm/kIvBTnd5lTn35cZXU/KVYAB0jlE03GKO8a9C5TkUzayaCbjC1/O2AuF+wdE/7vQydH6IYktp9TAV1oZPrpzBFHsuWMBzTZIb49gj47Sp2CKph9ap1jgNBI92+qZj59v/YHbeJzakyfyfDQld3TCxu7R/ZgAckkyiIIC2iC03xtBOXS8afein+ocKjRSQiKoiqmKQpXo4fBfoAq0Vr4DPN2mC81Q++95oB833AfWdB7qjYEdgOZw7Lxsic8uVMeaXG8yfv7H0B4LJaFM7fyIbOJhL2K79IRBWuSUO2z5Ugv7adWS3nlW8a/WYlK4TGDwaLI8BzDyjtaeRqYHoVOceytmAHVQo1bavdjdSE0/ETjlQylzjR7JAVYc1C9IC+oxyrK88Agu4A0+S3Iqx+e7rn1slPRlfPFEkhbU5GuXMFagVpFFVe/U=");
		nickskinvalues.put(2, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY1ODM0NjAsInByb2ZpbGVJZCI6IjUwMDk2MGIzYmQ0ZjRlNWI4M2UwMjUxNzU1MzE4ZTZkIiwicHJvZmlsZU5hbWUiOiIyMDBpcV9fXyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTczNzZjZDRkOTBkYzI2MGMwODE2MTUwNzU5MzYzNjI5MGNlNDA4MmFiMDJmNWM4MTk5ZGQ0OWM0MTU1ZjE5ZCIsIm1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9fX19");
		nickskinsignatures.put(2, "eVR1Qp6yQW1SPACOs4LPe36Rl0YBRA1OJ/xTC+PwMTBO54ZhYGchMRNy5DeAj2CilRQVr4kuju8u/KmVZS5fxOJVUQtqdjJp+VqL6w2iP5f4ljl/rCSO//0mvZvyhRYnv0uXI6Xfv9J++S34ObHjytpvf5a2E3uQPmmyaB0aRB/Elsw1pHp1M1YszET1m6Itbj6RvCQvyXYgetH1hOi0fUIC3vXUJAAsbvBZDEyDmSitM9GaZinkHBZ4oT4zsU7zbFnhGYhYD8amAUOyGM62vUxaOUVh6C479uJT/fnTYbGGScAYiKkRsBi8acI8NHyhQJwK9fmkHHsIDF4DPkJbRMABwI3vtFfCSld4RDNI5pPCHGTtSeWjxdemC+dDtPkCVpzQi+CgJISD/NjsRHrupq/pJA1PdY5c07Hc+oj2TS+tSBosm2wYLsWHaC5KbXRVvv2559nOrwDpG1e4XRxi73GbEoOISpNU8ii5zFNuneaMMMDA7DM3bC5ZIL9zx90MJ6i2KaIMUh0k9E+laEEKBc6UiSUxCLObjJea4nv68RPLr4+h3cDCe2gmt+2LqDSxGnAAiuQHGPqRXF5aJ4GU8FB1pwM4DNxAFeIFU1PCj5D87Ym4+BZCsg8OSLOlVKIEvd0IDL+1yahQmHobvG8OAI5EL8H5P4Cr5yb2bOzTY4c=");
		nickskinvalues.put(3, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY2Mjc2MjYsInByb2ZpbGVJZCI6IjlhYzg4N2IzMDM5ZDRlNGM4NDc2Y2I3YjY5YWE2ZWFhIiwicHJvZmlsZU5hbWUiOiJhbG9oZXkiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NkZTdjMGYwYzQ3ZjY4ZDU0YTQyZTc3NjdmNzA1YTk5NWViMmEwYTI1M2U0ZTkyOWJlYTJmZDA0Y2JlZDNlN2UifX19");
		nickskinsignatures.put(3, "PmQ9tBNcYAfvxsWGTAKlDM+7ErSyVlZmWk4nwKSiGJyPdkFIKNmnObi62lquugSLY0lNh9WJWLTKRKqGTzZYKY3GRXiLK4vSxwR2YVCLjabx8SGsya5RTlDWfGu/3eHEbMUY7xLbrm/Cgs2EcrU5WOFHQAs+HLhBbM8Xv92uQO8AUSgyj0nNVkRrucWibA3M/Tp1j82NiWpiBxHT+E5km41q5o8cg+2KzjYFVaf13JbcJIaPfajqzouIgAHRtS2Pz2yXtcc3zw7VWHtSt5ciu2W2hCG8w8AEqXoR4hgjO0BOFClgYrBnUBjTBYzY7TMWCw7tvgWUZveMvsL1tph/MU9xp2AXMVfTd/d35erwcb1/+PB22DtFuS8hNkNxoM/WKB3s6Ec0LZheZylJJ/8EAnkpThQNE9Qkr6fIYioOjR98TGA25MjN2iNViS1BZFBcZInHZD66w/uNWNoOMbCK3l+ux08Y3onj/rGjTZSwULJ6NBn81HKJJYVa1w/X2LSWD5+yqDZZ3J/wjCK2M+eA2KExnTRxcFWJ/Pa8dvEA3nywW13lErQILtn4OB7nKx7KLpOkm/mohM5Ojx/a9LiLvJvWg2aEiMsFrX70kx1eh9MhB18P3pBsmiAiUF0jpcb6tjjpcRSQuiozq5xY+2xlRj5MlFYxba4RWkCb86h84iU=");
		nickskinvalues.put(4, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY2NzQzMTksInByb2ZpbGVJZCI6IjA1YjczYzlkMTk0NzQ0NDU5MzI4YWQ5MjhlNmYxMjMwIiwicHJvZmlsZU5hbWUiOiIxc2Vuc2VpIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jMTQ1YmE2MzY4MGM2MDYxOGJiNTYxNzc4NjExY2RiZGNkZGQ0ZWI5Y2VhNDQxODQ0MGI0ZmUxZWZjMTJiYjgzIn19fQ==");
		nickskinsignatures.put(4, "O12ruOdK15FxFXu5GU9UjTS27FxJ4ianVDMTUp4Mscqj/V+djxAFG7QbHyUVdFHRpuKPpeP/uzGdUfZApnKEglCdtQ6jYMmy5zy72CdbhzrRp/vR2tZgobtfPa0NIBVC/7dMROseG+ZHTACno1qui4ebNHFYyJ1b283hk3m/WD3H8JDQn6f/ZEMsHcy6LdKYJj+T01A82hrtVbK+nC6VpFpm5qbyfIBe+Ve1B5HFk4WPogwHEzXixQH7Ikq2sXVsnTLE4mT3YQl0KRBMWal1hpvRrO8X+NufW3876ISvcrMEjw9s5sEtqnOEIVuomLlVM7I6zs1uz4feA5h87yEA9Wd90L2rDmkzqroU1U3BgbwTQfq8V9Os4V6O/ZtnaEA05om52quUx+1ubLlVODy9vbqN10VDSt0X2i4GuaN9+C/9iEPT5SyU0Ui2LcDwg+VlYDRMNzWPoxMRrc/1+P6QES8LFedPMjgTEqK43MPHBaPTAcyav9WLT5LYz4b88Ere26x1SSGtAjiNfur0HOzCoomdfPK8Vh9TNMPwQvlafDFrtqJfKoid6T/Tjp1tsBsbEYbzkO8/j0ER9TrgX1E68zD+gyCm31qRPyu0RZIYkIi9SHZvCU7RxV2y7bwCng5qjPQ0qyP6qajFEofMzVMPDuO/MpdPqsUaWy0W7gtsY+E=");
		nickskinvalues.put(5, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY3MjIxMTUsInByb2ZpbGVJZCI6IjJlYTU0Mjg4ZmI0YjQ3YWVhNDAyZGFlYWNmYzNjMjJkIiwicHJvZmlsZU5hbWUiOiJBbGd4cmllbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU1NTBhMmUwODE3MzgyNTQ5ZTU4YWEwNWFjYmFkMzEzZDI2NWYwZDVkMWQ5YjkwYWJiYjZiNjUyYmUyMjI0YyIsIm1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9fX19");
		nickskinsignatures.put(5, "RXn9SLfDAH0+/W/VjDfsvwei7uumS/4d2GmAR3dV6WhGx93QcjNOAuHOro7cOiYu5omPtbqsNtHOjBxdGf6yzz25I3+gkbMKjxu8xyvtcs9fAsEN0t17GCeKxZljI9mu9n/z4F+f2PzvG0rS09pp5qwLjpkFNpNDzJSQkvn+EvxDXsTaoMOFPaBfRNuqQEKxZj/W+FHjQv12/wwnz3mnCTUXWKh4T7FkpjDK1PlCe5GyPrhjJyyKWK3wrcOfAOgP4xu0mpx4tW5/p3KCF50qQjgUL/FrPT5bpfo9S4Zz4zSaNInSfK5UibwtPCG/1VEcJ2QB21+l0iEC/G1jUUO3Z3mCqhbhGXPFrhkdPC6obRBtzeMMKTx1xkuC01tszQBCwTtDMIPojvbpCsYeFeRJml1E1BwU0jE0JBpcOM7X75qY0b3WehZGUNIwRY93jXjxAyUJPPy+q9dE0G5ob2D+TYMm8aPGsjo36vr62Vlz82reMGlT8hHLQ3GpKfW6YuME9DIWsZo6k3ePbNq0HceVNpZzHWND/ZfCjr/Wd7+AgRW29h1iLd0wMgPJwd8wMF+GjXrTh05y3aoqInB3Dq9XOlYsIB3YxqmoLLi9YvUmBEH2e9auvu5WaeEKKbA92/PEwbCOOos1t6Skhb8AQAX9y7ORA19IGlXbk0urt+lRKvc=");
		nickskinvalues.put(6, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY3NzEzNjksInByb2ZpbGVJZCI6ImE0M2IwODA5NjQ5ZjRlZjI4ZTdiMWFiMDI2YjY0ZTUxIiwicHJvZmlsZU5hbWUiOiJCeGRMeGNrXyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFiMjI4YWExZTI5NjVmM2U5NjEzMGFiY2NkOTk3ODU0ZjQwNmFjODk0NTY5MjI0NjcwMjY1NGZkZTJhZjkxNiIsIm1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9fX19");
		nickskinsignatures.put(6, "tzIK7ya0LiIYLiKUOgNA1BuU/PxZWQ5shWM7dg6LxPtOclXt3DuIV0oGL0z7GVAifX2iPlvJdxdYMO2bsZfHN3k4yCb0KxkP4lO8/BpNUFPWMYPkXNFTaE+mXv9nY2x0ce2HjlHDvLlnCDv75iYUdQ9o/bJJFwCxjbJygHa1L20UB6/WV0l8UMLPR+dCqcyG/MYasNUgAjqfuvCF48PQiJB7+gYXmC907B5NeNgPmNos+S35nqQjkxkbDABo0VpjRVTGuK9YakYERMwnNV7JLlp2Au+jQmQgYci4upcpESUvVJgYfvLDoQAAI16X+G1vBnhGclEuSAeOTsRY71jk+oJsK5vu8411ArYfdfbqiak57yyzuKtRffMWW/NKeD5REyrsri4Y7ZDivO+07QMBh+RSJFy6ixY9gqmEjF0YbaD8V0Yy8QjX13WlTlk/2cOiXRDnbS4aLZUqowS6KwwPF2wbvpY6dxKcb6FwFfxIvjfb6CjarAxl4xvZHg/dO/y54W2Alh6c3XaVVkCY2akyS4nl0d4WE7yasuqW7kUAjXloI4BGXQ8OH0iLAbE6B1ZbDxsAPEYjnuMrq0LQ0SFSfuLvMMJqORIm1g453rJv/RzTNN40k8pSMfjAZXIsgGMrDAJIBpWPt85a8JBIJm4OQbzBW7nCe8YmQcwbufSz030=");
		nickskinvalues.put(7, "eyJ0aW1lc3RhbXAiOjE1MzQ4NzY4MjUyMDksInByb2ZpbGVJZCI6ImMzN2Y1ZWEyNzY1MzRhNjFiMDU4NWE0NTYwNTVmMWM0IiwicHJvZmlsZU5hbWUiOiI1ZXJpb3VzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zYTJlOTRmOTc2MGVkYzljM2RlMzhjYTNjYmY0NDIxOTcwM2E0ZjY3ZmVlNzRhMDZhNzY2YzEyZmNjMTRmYjVjIn19fQ==");
		nickskinsignatures.put(7, "kJ+pYvnH13HNWpHITiHZanh8Mw1M6fAZ+9iwtgI0yvUWS+WnGV2s/gNTr4WcwD0ob1TAotqxVwsW1ag+Ss3cJky+qX9UuNjkq9SdUWSWaOdcwTzEH8YB+YA/8hhLZ5U5VkbVRaKDqHhB2gCBgzst6cLk9c21fIxo3va8+T+F9w3+NePlu+Fh2qAcpgxzsq0d0dZkQ3tQ7Co331+X3m7bbJj12PBxkN2LAwGWze2CnbUbox0vsjEeBdutbTHQFzoWKU0kYQ2bkkmv3lBxWz64XD+XuXO6cx8cvv4q+GWlaG2SNw1mLbUIsDr743ATLXmxqUwoQ083xVUmnBz/bnQ6BYTm12MaqUjkIMYe5AhtjQpH3Qr+56/96ifnQY9PnQwwxOeHLjsshW9JUy132+Mfn7vxHxruBjtuG0nhLMFK0ikK328OsJKlZ1mb8+l1RrVxkIW821pKK0ZInJOuGRsmEcI6IqQX/PDoNSOdcHDJ9QlDtrEeJbDGWGY7soUohQuSgZkj+DQfExrAGlWbSZS1sL65GtivZpTBV+U4Fboq/PsjENY8xPFsRRWbmyXU8Q2tfRDXoDjEZF79nCkKglHARFBWJk+2UOVjmH9Qw1gp5I/PcHgttmqxVLKk9Iaf2Nq0p7HKlHPxHMcqdP2AE4nnElZhDQgbDx82WUqHcHBu510=");
	}
	
	
	public static Integer getradomskinid() {
		int i = 0;
		Random r = new Random();
		i = r.nextInt(7);
		return i;
	}
	
}
