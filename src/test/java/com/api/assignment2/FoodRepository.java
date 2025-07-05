/**
 * 
 */
package com.api.assignment2;

/**
 * @author admin
 *
 */
import java.util.Optional;

public interface FoodRepository {
	Optional<FoodItem> findByDishName(String name);

	FoodItem save(FoodItem item);
}
