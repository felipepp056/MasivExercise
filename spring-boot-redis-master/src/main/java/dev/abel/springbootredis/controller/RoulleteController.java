package dev.abel.springbootredis.controller;


import dev.abel.springbootredis.domain.Player;
import dev.abel.springbootredis.domain.Roullete;
import dev.abel.springbootredis.repository.RoulleteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class RoulleteController {
    private RoulleteRepository roulleteRepository;
    
    public RoulleteController(RoulleteRepository roulleteRepository) {
        this.roulleteRepository = roulleteRepository;
    }

    
    
   
    //1  create roullete and returns the ID
    @PostMapping("/create")
    public String createRoullete(@RequestBody Roullete roullete) {
    	//1 create roullete and return id
    	roulleteRepository.save(roullete);
    	roulleteRepository.inizialicePlayers(roullete.getId());
    	return roulleteRepository.getId(roullete);
    }
    
    
    //2  open roullete and return the confirmation of the operation
    @GetMapping("/open/{id}")
    public String openRoullete(@PathVariable String id) {
    	String message = "";
    	if(roulleteRepository.findById(id) != null) {
    		roulleteRepository.changeStatus(id, 1);
    		message = "Existosa";
    	}else {
    		message = "Denegada";
    	}
    	//1 find roullete if not return denegado
    	//2 change status of roullete
        return message;//roulleteRepository.findById(id);
    }
    
    //3 bet create a bet with the id of the user
    
    @PutMapping("/bet/{rouleteID}/{betType}/{betValue}")
    public void betRoullete(@RequestHeader String playerID,@PathVariable String betType,@PathVariable double betValue,@PathVariable String rouleteID ) {
    	Roullete roullete = roulleteRepository.findById(rouleteID);
    	if(roullete != null && ((betType.equalsIgnoreCase("Rojo") || (betType.equalsIgnoreCase("Negro")) ) || (Integer.parseInt(betType)<= 36 ) || (Integer.parseInt(betType )>=0)) && (betValue <= 10000) && (betValue > 0) ) {
    		if(roullete.getStatus() == 1) {
    			Player player = new Player(playerID,betType,betValue,0);
    			roulleteRepository.addPlayer(rouleteID, player);
    		}
    	}
    	//1 find roullete 
    	//2 check status
    	//3 add new player with its components
    }
    
    //4  close roullete returns the result of the game
    @GetMapping("/close/{id}")
    public ArrayList<Object> playGame(@PathVariable String id) {
    	ArrayList<Object> resultArray = new ArrayList<Object>();
    	Roullete roullete = roulleteRepository.findById(id);
    	if(roullete != null) {
    		if(roullete.getStatus() == 1) {
    			roulleteRepository.playRoullete(id);
    			int result = roulleteRepository.giveResult(id);
    			String message = "el valor obtenido por la ruleta fue: "+ result;
    			resultArray.add(message);
    			roulleteRepository.setPlayersProfit(id, result);
    			ArrayList<Player> players = roulleteRepository.givePlayers(id);
    			resultArray.add(players);
    			roulleteRepository.restartRoullete(id);
    		}
    	}
    	return resultArray;		
    }
    
    // 5 find all the roulletes
    @GetMapping("/all")
    public Map<String, Roullete> findAll() {
        return roulleteRepository.findAll();
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable String id) {
    	roulleteRepository.deletePlayers(id);
    	roulleteRepository.delete(id);
    }
    /*
     * 
    @GetMapping("/students/{id}")
    public Roullete findById(@PathVariable String id) {
        return roulleteRepository.findById(id);
    }
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable String id) {
    	roulleteRepository.delete(id);
    }
    
    */
    
}
