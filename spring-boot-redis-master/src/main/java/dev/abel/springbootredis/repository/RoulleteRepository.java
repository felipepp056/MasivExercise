/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Proyect Roullete masiv
 * Author: Edgar Felipe Peña Porras - 30/05/2021 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package dev.abel.springbootredis.repository;

import dev.abel.springbootredis.domain.Player;
import dev.abel.springbootredis.domain.Roullete;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class has all the important methods to run the game API
 */
@Repository
public class RoulleteRepository implements RedisRepository {
	
	// -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------
    
	/**
	 * represents the key to communicate with the database
	 */
    private static final String KEY = "Roullete";
    
    // -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------
    
    /**
	 * represents redisTemplate to manipulate the database
	 */
    private RedisTemplate<String, Roullete> redisTemplate;
    
    /**
	 * represents the hasOperarations to manipulate the database
	 */
    private HashOperations hashOperations;
    
    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
    
    /**
     * Constructs a new RoulleteRepository object. 
     * @param redisTemplate RedisTemplate to communicate with he database.
     */
    public RoulleteRepository(RedisTemplate<String, Roullete> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * init for the data base. 
     */
    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    
    // -----------------------------------------------------------------
    // Override interface methods
    // -----------------------------------------------------------------

    /**
     * gives a map of the roullete stored in the database.
     * @return Map of all the roulletes stored in the database
     */
    @Override
    public Map<String, Roullete> findAll() {
        return hashOperations.entries(KEY);
    }

    /**
     * gives a roullete by a given id.
     * @param id Id given to find the roullete.
     * @return Roullete that has given ID
     */
    @Override
    public Roullete findById(String id) {
        return (Roullete) hashOperations.get(KEY, id);
    }

    /**
     * saves a new roullete in the database
     * @param roullete Roullete to be stored in the database.
     */
    @Override
    public void save(Roullete roullete) {
        hashOperations.put(KEY, roullete.getId(), roullete);
    }

    /**
     * deletes a roullete from the database.
     * @param id Id of the roullete to be deleted.
     */
    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
    
    /**
     * gives the id of a given roullete.
     * @param roullete Roullete given to give its ID.
     * @return id of the given roullete.
     */
    @Override
	public String getId(Roullete roullete) {

		return roullete.getId();
	}
	
   // -----------------------------------------------------------------
    // Override interface methods
    // -----------------------------------------------------------------
    
    /**
     * initialize the players array of the roullete game.
     * @param idRoullete id of the roullete that is going to initialize its players.
     */
    public void initializePlayers(String idRoullete) {
    	Roullete roullete = findById(idRoullete);
    	roullete.inicializePlayers();
    	hashOperations.put(KEY, idRoullete, roullete);
    }
    
    /**
     * add players to the players array of the selected roullete.
     * @param idRoullete id of the roullete that is going to add players.
     * @param idRoullete player of the roullete that is going to added to the game.
     */
	public void addPlayer(String idRoullete, Player player) {
		Roullete roullete = findById(idRoullete);
		boolean hasSameId = false;
		for(int i = 0; i < roullete.getPlayers().size(); i++) {
			Player players = roullete.getPlayers().get(i);
			if(players.getId().equals(player.getId()) )
				hasSameId = true;	
		}
		if(hasSameId == false) {
			roullete.addPlayers(player);
		}
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
	/**
     * changes the status of the given roullete
     * @param idRoullete id of the roullete that is going to change its status.
     * @param status New status of the roullete.
     */
	public void changeStatus(String idRoullete, int status) {
		Roullete roullete = findById(idRoullete);
		roullete.setStatus(status);
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
	/**
     * restart all the values of the selected roullete
     * @param idRoullete id of the roullete that is going to be restarted.
     */
	public void restartRoullete(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		roullete.setResult(0);
		roullete.errasePlayers();
		roullete.setStatus(0);
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
	/**
     * give the status of the selected roullete.
     * @param idRoullete id of the roullete.
     * @return status of the roullete.
     */
	public int giveStatus(String idRoullete) {
		
		return findById(idRoullete).getStatus();
	}
	
	/**
     * give the result of the game of the selected roullete.
     * @param idRoullete id of the roullete that its result.
     * return result of the roullete game.
     */
	public int giveResult(String idRoullete) {
		
		return findById(idRoullete).getResult();
	}
	
	/**
     * play the selected roullete.
     * @param idRoullete id of the roullete that is going play.
     */
	public void playRoullete(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		int result = (int) Math.floor(Math.random()*(36+1));
		roullete.setResult(result);	
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
	/**
     * set the profit of the players depending on the result of the game.
     * @param idRoullete id of the roullete that played.
     * @param result Result of the game that the roullete played.
     */
	public void setPlayersProfit(String idRoullete,int result) {
		Roullete roullete = findById(idRoullete);
		ArrayList<Player> players = roullete.getPlayers();
		for(int i = 0; i < players.size( ); i++) {
			 Player actual = players.get(i);
			 if(actual.getBet().equalsIgnoreCase("Rojo") || actual.getBet().equalsIgnoreCase("Negro") ) {
				 setColorProfit(actual,result);
			 }else {
				 setNumberProfit(actual,result);
			 }
			 hashOperations.put(KEY, idRoullete, roullete);
		}
	}
	
	/**
     * set the profit of the player if the made a number bet (0 -36).
     * @param player Player that made the number bet.
     * @param result result of the roullete game played.
     */
	public void setNumberProfit(Player actual,int result) {
		if(Integer.parseInt(actual.getBet()) == result) {
			double profit = 5*actual.getMoney() - actual.getMoney();
			actual.setProfit(profit);
			double actualMoney = actual.getMoney() + actual.getProfit();
			actual.setMoney(actualMoney);
		}else {
			double loss = -1*actual.getMoney();
			actual.setProfit(loss);
			double actualMoney = actual.getMoney() + actual.getProfit();
			actual.setMoney(actualMoney);
		}
	}
	
	/**
     * set the profit of the player if the made a color bet (Negro - Rojo).
     * @param player Player that made the color bet.
     * @param result result of the roullete game played.
     */
	public void setColorProfit(Player actual,int result) {
		if(actual.getBet().equalsIgnoreCase("Rojo") || actual.getBet().equalsIgnoreCase("Negro") ) {
			 if(result%2==0 && actual.getBet().equalsIgnoreCase("Rojo")) {
				 double profit = actual.getMoney()*1.8 - actual.getMoney();
				 actual.setProfit(profit);
				 double actualMoney = actual.getMoney() + actual.getProfit();
				 actual.setMoney(actualMoney);
			 }else if(result%2==0 && actual.getBet().equalsIgnoreCase("Negro")){
				 double loss = -1*actual.getMoney();
				 actual.setProfit(loss);
				 double actualMoney = actual.getMoney() + actual.getProfit();
				 actual.setMoney(actualMoney); 
			 }
			 else if(result%2!=0 && actual.getBet().equalsIgnoreCase("Rojo")){
				 double loss = -1*actual.getMoney();
				 actual.setProfit(loss);
				 double actualMoney = actual.getMoney() + actual.getProfit();
				 actual.setMoney(actualMoney);
			 }else if(result%2!=0 && actual.getBet().equalsIgnoreCase("Negro")){
				 double profit = actual.getMoney()*1.8 - actual.getMoney();
				 actual.setProfit(profit);
				 double actualMoney = actual.getMoney() + actual.getProfit();
				 actual.setMoney(actualMoney);
			 }
		 }
	}
	
	/**
     * give the arrayList of the player of a given roulette
     * @param idRoullete Id of the roulette.
     * @return Arraylist of player of the given roulette game played.
     */
	public ArrayList<Player> givePlayers(String idRoullete) {
		return findById(idRoullete).getPlayers();
	}
	public void deletePlayers(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		roullete.getPlayers().clear();
	}

}
