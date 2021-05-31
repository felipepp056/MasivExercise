/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Proyect Roullete masiv
 * Author: Edgar Felipe Peña Porras - 30/05/2021 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package dev.abel.springbootredis.domain;

import java.io.Serializable;
/**
 * This class represents a new player for a specific roullete game.
 */
public class Player implements Serializable {

	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------

    /**
     * Represents the id of the player.
     */
	private String id;
	
	/**
     * Represents the bet made by the player (0 -36) or Red or Black in the roullete game.
     */
	private String bet;
	
	/**
     * Represents the money gived by the player for the bet
     */
	private double money;
	
	/**
     * Represents the profit of the player after the roullete played. Could be negative if the player loose his money or positive if the player won.
     */
	private double profit;
	
	// -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	
	 /**
     * Constructs a new Player object. 
     * @param id ID of the player.
     * @param bet Bet of the player.
     * @param money Money of the player for the game.
     * @param profit Profit of the player because of the game
     */
	public Player(String id, String bet, double money, double profit) {
		this.id = id;
		this.bet = bet;
		this.profit = profit;
		this.money = money;
	}
	
	// -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------
    
	/**
     * gets the id of the player
     * @return the id of the player
     */
	public String getId() {
		return id;
	}
	
	/**
     * set the id of the player
     * @param id. Its the setted id of the player
     */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
     * gets the id of the player
     * @return the id of the player
     */
	public String getBet() {
		return bet;
	}
	
	/**
     * sets the bet of the player
     * @param bet. Its the bet of the player
     */
	public void setBet(String bet) {
		this.bet = bet;
	}
	
	/**
     * gets the profit of the player
     * @return the profit of the player
     */
	public double getProfit() {
		return profit;
	}
	
	/**
     * sets the profit of the player
     * @param profit. Its the profit of the player
     */
	public void setProfit(double profit) {
		this.profit = profit;
	}
	
	/**
     * gets the money of the player
     * @return the money of the player
     */
	public double getMoney() {
		return money;
	}

	/**
     * sets the money of the player
     * @param money. Its the profit of the player
     */
	public void setMoney(double money) {
		this.money = money;
	}

}
