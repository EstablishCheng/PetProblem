package com.shareprog.pojo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * <p>Title: Pet</p>  
 * <p>Description: 宠物实体类</p> 
 * @author chengli
 * @date 2021年4月21日
 */
public class Pet {

	private String species;
	
	private Integer quantity;

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(AtomicInteger quantity) {
		this.quantity = quantity.get();
	}

	@Override
	public String toString() {
		return "{\"species\":\"" + species + "\", \"quantity\":\"" + quantity + "\"}";
	}

}
