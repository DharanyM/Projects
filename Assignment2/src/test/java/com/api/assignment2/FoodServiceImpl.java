/**
 * 
 */
package com.api.assignment2;

/**
 * @author admin
 *
 */
public class FoodServiceImpl implements FoodService {

	private FoodRepository foodRepository;

	public FoodServiceImpl(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	@Override
	public FoodItem serveDish(String dishName) {
		return foodRepository.findByDishName(dishName)
				.orElseThrow(() -> new RuntimeException("Sorry, we don't have that dish."));
	}
}
