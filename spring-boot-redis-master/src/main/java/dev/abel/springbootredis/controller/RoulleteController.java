/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Proyect Roullete masiv
 * Author: Edgar Felipe Peña Porras - 30/05/2021 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package dev.abel.springbootredis.controller;


import dev.abel.springbootredis.domain.Player;
import dev.abel.springbootredis.domain.Roullete;
import dev.abel.springbootredis.repository.RoulleteRepository;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represent the roullete controller for the API
 */
@RestController
public class RoulleteController {
	
	// -----------------------------------------------------------------
    // Atributes
    // -----------------------------------------------------------------

	/**
     * Represents the roullete repository.
     */
    private RoulleteRepository roulleteRepository;
    
    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------
	
	
	 /**
     * Constructs a new roulette controller object. 
     * @param roulleteRepository RoulleteRepository to manage the database.
     */
    public RoulleteController(RoulleteRepository roulleteRepository) {
        this.roulleteRepository = roulleteRepository;
    }
    
    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * endpoint to create a new roulette game. 
     * @param roullete roulette to be created.
     * @return id of the roulette created.
     */
    @PostMapping("/create")
    public String createRoullete(@RequestBody Roullete roullete) {
    	roulleteRepository.save(roullete);
    	roulleteRepository.initializePlayers(roullete.getId());
    	
    	return roulleteRepository.getId(roullete);
    }
    
    /**
     * endpoint to active the status of a given roulette. 
     * @param id ID of the roulette to be activated.
     * @return message alerting the status of the request.
     */
    @GetMapping("/open/{id}")
    public String openRoullete(@PathVariable String id) {
    	String message = "";
    	if(roulleteRepository.findById(id) != null) {
    		roulleteRepository.changeStatus(id, 1);
    		message = "estado abierto Existosa";
    	}else {
    		message = "estado abierto Denegada";
    	}

        return message;
    }
    
    /**
     * endpoint to create a new player with its important values. 
     * @param playerID ID of the new player.
     * @param betType type of bet made by the new player (numeric 0-36) or (color: Negro - Rojo).
     * @param betValue value of money givend by the new player for the game.
     * @param rouleteID ID of the roulette that is going to add a new player.
     */
    @PutMapping("/bet/{rouleteID}/{betType}/{betValue}")
    public void betRoullete(@RequestHeader String playerID,@PathVariable String betType,@PathVariable double betValue,@PathVariable String rouleteID ) {
    	Roullete roullete = roulleteRepository.findById(rouleteID);
    	if(roullete != null && ((betType.equalsIgnoreCase("Rojo") || (betType.equalsIgnoreCase("Negro")) ) || (Integer.parseInt(betType)<= 36 ) || (Integer.parseInt(betType )>=0)) && (betValue <= 10000) && (betValue > 0) ) {
    		if(roullete.getStatus() == 1) {
    			Player player = new Player(playerID,betType,betValue,0);
    			roulleteRepository.addPlayer(rouleteID, player);
    		}
    	}
    }
    
    /**
     * endpoint to close a roulette game where the result and the profit of the plyaers is given, the the roulette status will be closed. 
     * @param id ID of the roulette to be closed.
     * @return ArrayList with the result of the roulette game.
     */
    @GetMapping("/close/{id}")
    public ArrayList<Object> playGame(@PathVariable String id) {
    	ArrayList<Object> resultArray = new ArrayList<Object>();
    	Roullete roullete = roulleteRepository.findById(id);
    	if(roullete != null) {
    		if(roullete.getStatus() == 1) {
    			roulleteRepository.playRoullete(id);
    			int result = roulleteRepository.giveResult(id);
    			String message = "El valor obtenido en la ruleta fue: "+ result;
    			resultArray.add(message);
    			roulleteRepository.setPlayersProfit(id, result);
    			ArrayList<Player> players = roulleteRepository.givePlayers(id);
    			resultArray.add(players);
    			roulleteRepository.restartRoullete(id);
    		}
    	}
    	
    	return resultArray;		
    }  

    /**
     * endpoint to give all the roulettes with their important information. 
     * @return Map of the atributes of the roulettes created.
     */
    @GetMapping("/all")
    public Map<String, Roullete> findAll() {
    	
        return roulleteRepository.findAll();
    }
    
    /**
     * endpoint to delete a roulette. 
     * @param id ID of the roulette to be deleted.
     */
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable String id) {
    	roulleteRepository.deletePlayers(id);
    	roulleteRepository.delete(id);
    }
}
