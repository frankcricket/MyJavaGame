package it.unical.mat.igpe17.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class ServerGame extends Thread{
	
	private ServerSocket ss;
	private ArrayBlockingQueue<Socket> q;
	private CyclicBarrier barrier;
	
	public ServerGame(ServerSocket ss, ArrayBlockingQueue<Socket> q, CyclicBarrier barrier) {
		this.ss = ss;
		this.q = q;
		this.barrier = barrier;
	}	
	
	@Override
	public void run() {
		
		try {
			
			Socket socket = ss.accept();
			q.put(socket);
			
			barrier.await();
			System.out.println("connection established!!");
			
			while(true){
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String rec = br.readLine();
				System.out.println("Messaggio: " + rec);
				for(int i = 0; i < 2; i++){
					PrintStream ps = new PrintStream(socket.getOutputStream());
					ps.println(rec);
				}
				
				//System.out.println("sono il server. ho ricevuto e mandato i messaggi");
			}
			
			
			
		} catch (IOException | InterruptedException | BrokenBarrierException e) {}

	}

}
