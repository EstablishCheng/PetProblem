package com.shareprog.server;

public class ClientTest {

	public static void main(String[] args) throws InterruptedException {
		PetClient petClient;
		for (int i = 0; i < 10; i++) {
			petClient = new PetClient();
			petClient.request();
		}
//		petClient = new PetClient();
//		petClient.request();
	}
}
