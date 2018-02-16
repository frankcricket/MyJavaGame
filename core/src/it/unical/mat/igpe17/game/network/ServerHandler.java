package it.unical.mat.igpe17.game.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerHandler extends Thread{
	
	private int port;
	private ArrayBlockingQueue<Socket> q;
	private CyclicBarrier barrier;
	
	public ServerHandler(int port) {
		this.port = port;
		q = new ArrayBlockingQueue<>(2);
		barrier = new CyclicBarrier(3);
	}
	
	
	@Override
	public void run() {

		try {
			ServerSocket ss = new ServerSocket(port);
			for(int i = 0; i < 2; i++){
				ServerGame sg = new ServerGame(ss,q,barrier);
				sg.start();
			}
			System.out.println("sono: " + getId());
			barrier.await();

			sleep(2000);
			for(Socket s : q){
				PrintStream out = new PrintStream(s.getOutputStream());
				out.println(1);
			}
			System.out.println("ho finito di inviare i messaggi");
			
		}		
		catch (IOException | InterruptedException | BrokenBarrierException e) {}
				
	}

}
