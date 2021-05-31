/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Proyect Roullete masiv
 * Author: Edgar Felipe Peña Porras - 30/05/2021 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package dev.abel.springbootredis.repository;

import dev.abel.springbootredis.domain.Roullete;
import java.util.Map;

/**
 * This interface has all the needed methods for the redis database.
 */
public interface RedisRepository {
	
	// -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------
	
	/**
	 * Represents the method that gives all the Roulletes.
	 */
    Map<String, Roullete> findAll();
    
    /**
	 * Represents the method that find an roullete game with its ID.
	 */
    Roullete findById(String id);
    
    /**
	 * Represents the method that saves a new roullete in redis database.
	 */
    void save(Roullete roullete);
    
    /**
	 * Represents the method that deletes the roullete game by its ID.
	 */
    void delete(String id);
    
    /**
	 * Represents the method gets the id of the roullete.
	 */
    String getId(Roullete roullete);
}
