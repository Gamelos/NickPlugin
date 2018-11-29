package de.gamelos.nick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNickEvent extends Event{

	Player player;
	public static HandlerList handlers = new HandlerList();
	
	public PlayerNickEvent(Player p){
		this.player = p;
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return PlayerNickEvent.handlers;
	}
	
	public static HandlerList getHandlerList(){
		return PlayerNickEvent.handlers;
	}

	public Player getplayer(){
		return this.player;
	}
	
	
}
