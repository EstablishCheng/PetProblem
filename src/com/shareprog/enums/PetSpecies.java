package com.shareprog.enums;

import java.util.Random;

public enum PetSpecies {
	// 狗
	DOG,
	// 猫
	CAT,
	// 鸡
	CHICKEN,
	// 鹦鹉
	PARROT;
	
	public static PetSpecies randomPetEnum() {
		return PetSpecies.values()[new Random().nextInt(PetSpecies.values().length)];
	}
}
