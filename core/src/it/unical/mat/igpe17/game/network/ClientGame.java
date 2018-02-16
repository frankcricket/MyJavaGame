package it.unical.mat.igpe17.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import it.unical.mat.igpe17.game.network.screen.Connection;

public class ClientGame extends Thread {

	private Socket s;
	private MultiplayerGameMain mGame;

	public ClientGame(MultiplayerGameMain mGame, String ip, int port) {

		this.mGame = mGame;

		try {
			s = new Socket(ip, port);

		} catch (IOException e1) {
		}

	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {

			while (true) {
				Scanner sc = new Scanner(s.getInputStream());
				int num = sc.nextInt();
				//System.out.println("ho ricevuto il messaggio");
				if (num == 1) {
					Connection.received--;
					break;
				}
			} // while

			// invio al server il valore del player attuale
			PrintStream p = new PrintStream(s.getOutputStream());
			p.println(mGame.getPlayer1Type());
			System.out.println("Sono il player: " + mGame.getPlayer1Type());

			boolean notMyValue = true;
			while (notMyValue) {
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String rec = br.readLine();
				if(Integer.parseInt(rec) != mGame.getPlayer1Type()){
					notMyValue = false;
					System.out.println("ho ricevuto l'altro player: " + rec);
				}

			}

		} catch (IOException e) {
		}

	}

}
