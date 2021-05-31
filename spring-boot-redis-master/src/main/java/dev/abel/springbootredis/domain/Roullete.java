package dev.abel.springbootredis.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Roullete implements Serializable {
    private String id;
	private int status;
    private int result;
    private ArrayList<Player> players;
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	
	//roullete methods
	public void inicializePlayers() {
		this.players = new ArrayList();
	}
	public void addPlayers(Player player)
	{
		players.add(player);
	}
	
	public void errasePlayers()
	{
		players.clear();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
