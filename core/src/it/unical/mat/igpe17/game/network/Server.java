package it.unical.mat.igpe17.game.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Server extends Thread {

	private ServerSocket server;
	protected ArrayBlockingQueue<ClientHandler> clients;
	protected static int stillAlive = 2;

	public Server(int port) {
		try {

			server = new ServerSocket(port);
			clients = new ArrayBlockingQueue<>(2);

			start();
		} catch (IOException e) {
		}
	}

	static String type1 = null, type2 = null;

	@Override
	public void run() {
		try {

			// validazione della connesione tra server e client
			for (int i = 0; i < 2; i++) {
				Socket client = server.accept();
				System.out.println(client.getInetAddress().getHostName() + " connected!");
				ClientHandler newClient = new ClientHandler(client);
				clients.put(newClient);
				// new SendMessage(clients).notifyCl();
			}

			// attesa di 2 secondi prima di notificare la connessione
			sleep(2000);

			// invio del messaggio di fine attesa
			for (ClientHandler ch : clients) {
				ch.out.println(1);
			}

			// invio tipo personaggio
			for (ClientHandler ch : clients) {
				String type = ch.in.readLine();
				if(type.equals("1")){
					ch.out.println(2);
				}
				else{
					ch.out.println(1);
				}

			}
			
			//gestione richieste client
			ClientHandler c1 = clients.take();
			ClientHandler c2 = clients.take();
			
			Listener l1 = new Listener(c1, c2);
			Listener l2 = new Listener(c2, c1);
			l1.start();
			l2.start();
			
			
			
			
		} catch (IOException | InterruptedException e) {
		}

	}

}

class Listener extends Thread {

	private ClientHandler receiver;
	private ClientHandler sender;

	public Listener(ClientHandler receiver, ClientHandler sender) {
		this.receiver = receiver;
		this.sender = sender;
	}

	@Override
	public void run() {
		
		try {
			while(true){
				
				if(Server.stillAlive == 0){
					sender.out.println("dead");
				}
				
				if(receiver.in.ready()){
					String received = receiver.in.readLine();
					if(received.equals("imdead")){
						Server.stillAlive--;
						continue;
					}
					sender.out.println(received);
					sender.out.flush();
					
				}
			}
		} catch (IOException e /*| InterruptedException e*/) {}		
		
	}
}
