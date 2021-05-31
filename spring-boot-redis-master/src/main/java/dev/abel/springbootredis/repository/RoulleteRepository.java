package dev.abel.springbootredis.repository;

import dev.abel.springbootredis.domain.Player;
import dev.abel.springbootredis.domain.Roullete;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Map;

@Repository
public class RoulleteRepository implements RedisRepository {
    private static final String KEY = "Roullete";

    private RedisTemplate<String, Roullete> redisTemplate;
    private HashOperations hashOperations;

    public RoulleteRepository(RedisTemplate<String, Roullete> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, Roullete> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public Roullete findById(String id) {
        return (Roullete) hashOperations.get(KEY, id);
    }

    @Override
    public void save(Roullete roullete) {
        hashOperations.put(KEY, roullete.getId(), roullete);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
    
    @Override
	public String getId(Roullete roullete) {
		// TODO Auto-generated method stub
		return roullete.getId();
	}
	
	//only roullete repositories
	
    public void inizialicePlayers(String idRoullete) {
    	Roullete roullete = findById(idRoullete);
    	roullete.inicializePlayers();
    	hashOperations.put(KEY, idRoullete, roullete);
    }
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
	
	public void changeStatus(String idRoullete, int status) {
		Roullete roullete = findById(idRoullete);
		roullete.setStatus(status);
		hashOperations.put(KEY, idRoullete, roullete);
		
	}
	
	public void restartRoullete(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		roullete.setResult(0);
		roullete.errasePlayers();
		roullete.setStatus(0);
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
	public int giveStatus(String idRoullete) {
		return findById(idRoullete).getStatus();
	}
	
	public int giveResult(String idRoullete) {
		return findById(idRoullete).getResult();
	}
	public void playRoullete(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		int result = (int) Math.floor(Math.random()*(36+1));
		roullete.setResult(result);	
		hashOperations.put(KEY, idRoullete, roullete);
	}
	
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
			 //actualizePlayers(idRoullete,actual,i);
			 hashOperations.put(KEY, idRoullete, roullete);
		}
	}
	
	public void actualizePlayers(String idRoullete, Player player, int i) {
		Roullete roullete = findById(idRoullete);
		roullete.getPlayers().remove(i);
		roullete.getPlayers().add(i, player);
	}
	
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
	
	public ArrayList<Player> givePlayers(String idRoullete) {
		return findById(idRoullete).getPlayers();
	}
	public void deletePlayers(String idRoullete) {
		Roullete roullete = findById(idRoullete);
		roullete.getPlayers().clear();
	}

}
