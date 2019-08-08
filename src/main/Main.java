package main;


import java.util.ArrayList;
import client.Client;

public class Main {

	public static void main(String[] args) {
		
		  String nome       = args[0]; 
		  String porta      = args[1]; 
		  String IPClient   = args[2];
		  String caminho    = args[3]; 
		  String[] vizinhos = args[4].split(",");
		  String ttl        = args[5];
		  String tempo      = args[6];
		  String portaTCP	= args[7];
		 
		  Client client = new Client(nome, IPClient, porta, vizinhos, caminho, ttl, tempo, portaTCP); 
		  
		  client.start();
	}

}