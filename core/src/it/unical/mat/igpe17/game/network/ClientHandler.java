package it.unical.mat.igpe17.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler {
	
	protected Socket client;
	protected PrintStream out;
	protected BufferedReader in;
	
	public ClientHandler(Socket client) {
		try {
		
			this.client = client;
			out = new PrintStream(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		} catch (IOException e) {}
	}

}
