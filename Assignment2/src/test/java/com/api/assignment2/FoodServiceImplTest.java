/**
 * 
 */
package com.api.assignment2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

/**
 * @author admin
 *
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FoodServiceImplTest {

	@Mock
	private FoodRepository foodRepository; // Fake the chef

	@InjectMocks
	private FoodServiceImpl foodService; // Test the waiter

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Init mocks
	}

	@Test
	void testServeDish_WhenDishExists_ShouldReturnFoodItem() {
		// Arrange
		FoodItem idli = new FoodItem("Idli", 40);
		when(foodRepository.findByDishName("Idli")).thenReturn(Optional.of(idli));

		// Act
		FoodItem result = foodService.serveDish("Idli");

		// Assert
		assertNotNull(result);
		assertEquals("Idli", result.getDishName());
		assertEquals(40, result.getPrice());

		verify(foodRepository, times(1)).findByDishName("Idli"); // Chef was asked
	}

	@Test
	void testServeDish_WhenDishNotExists_ShouldThrowException() {
		// Arrange
		when(foodRepository.findByDishName("Pizza")).thenReturn(Optional.empty());

		// Act & Assert
		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			foodService.serveDish("Pizza");
		});

		assertEquals("Sorry, we don't have that dish.", ex.getMessage());

		verify(foodRepository, times(1)).findByDishName("Pizza");
	}
}
