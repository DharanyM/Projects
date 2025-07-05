/**
 * 
 */
package com.api.assignment2;

/**
 * @author admin
 *
 */
public class FoodItem {
	private String dishName;
	private int price;

	public FoodItem(String dishName, int price) {
		this.dishName = dishName;
		this.price = price;
	}

	public String getDishName() {
		return dishName;
	}

	public int getPrice() {
		return price;
	}

}
