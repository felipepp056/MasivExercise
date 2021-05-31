/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Proyect Roullete masiv
 * Author: Edgar Felipe Peña Porras - 30/05/2021 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package dev.abel.springbootredis.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent the roullete object
 */
public class Roullete implements Serializable {
	
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
	
	 /**
     * Represents the id of the roulette.
     */
    private String id;
    
    /**
     * Represents the status of the roulette.
     */
	private int status;
	
	 /**
     * Represents the result of the roulette.
     */
    private int result;
    
    /**
     * Represents the Array of active players in the roulette game.
     */
    private ArrayList<Player> players;
    
    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------
    
    /**
     * gives the status of the roulette. 
     * @return status of the roulette.
     */
	public int getStatus() {
		return status;
	}
	
	/**
     * sets the status of the roulette. 
     * @param status of the roulette.
     */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
     * gives the result of the roulette. 
     * @return result of the roulette game.
     */
	public int getResult() {
		return result;
	}
	
	/**
     * sets the result of the roulette. 
     * @param result of the roulette.
     */
	public void setResult(int result) {
		this.result = result;
	}
	
	/**
     * gives the Array of active players of the roulette. 
     * @return Array of the active players.
     */
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	/**
     * initialize the Array of active players of the roulette. 
     */
	public void inicializePlayers() {
		this.players = new ArrayList();
	}
	
	/**
     * adds a new active player into the array of players of the roulette. 
     * @param player Player to be added to the array  of the roulette.
     */
	public void addPlayers(Player player)
	{
		players.add(player);
	}
	
	/**
     * errase all the active players of the roulette. 
     */
	public void errasePlayers()
	{
		players.clear();
	}
	
	/**
     * gives the id of the roulette. 
     * @return id of the roulette.
     */
	public String getId() {
		return id;
	}
	
	/**
     * sets the id of the roulette. 
     * @param id of the roulette.
     */
	public void setId(String id) {
		this.id = id;
	}
    
}
