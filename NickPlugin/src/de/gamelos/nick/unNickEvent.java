package de.gamelos.nick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class unNickEvent extends Event{

	Player player;
	public static HandlerList handlers = new HandlerList();
	
	public unNickEvent(Player p){
		this.player = p;
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return unNickEvent.handlers;
	}
	
	public static HandlerList getHandlerList(){
		return unNickEvent.handlers;
	}

	public Player getplayer(){
		return this.player;
	}
	
	
}
