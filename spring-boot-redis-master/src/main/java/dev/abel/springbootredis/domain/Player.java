package dev.abel.springbootredis.domain;

import java.io.Serializable;

public class Player implements Serializable {


	private String id;
	private String bet;
	private double money;
	private double profit;
	
	public Player(String id, String bet, double money, double profit) {
		this.id = id;
		this.bet = bet;
		this.profit = profit;
		this.money = money;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBet() {
		return bet;
	}
	public void setBet(String bet) {
		this.bet = bet;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	
	

}
