package it.unical.mat.igpe17.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.network.screen.Connection;

public class ClientGame extends Thread {

	public Socket s;
	private PrintStream out;
	private BufferedReader in;
	private MultiplayerGameMain mGame;

	public ClientGame(MultiplayerGameMain mGame, String ip, int port) {

		try {
			this.mGame = mGame;
			s = new Socket(ip, port);

			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		} catch (IOException e1) {
		}

	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {

			Scanner sc = new Scanner(s.getInputStream());
			int num = sc.nextInt();
			if (num == 1) {
				Connection.received--;
			}

			// invio al server il valore del player attuale
			out.println(mGame.getPlayer1Type());
			out.flush();
			String rec = in.readLine();
			if (Integer.parseInt(rec) != mGame.getPlayer1Type()) {
				mGame.virtual_player.setType(Integer.parseInt(rec));
			}

			String received = null;
			int toSend;
			int toSendPrec = -1;
			while (true) {

				// ricezioni aggiornamenti dell'altro player
				if (in.ready()) {
					if ((received = in.readLine()) != null && !(received.equals(""))) {
						
						if(received.equals("dead")){
							mGame.showScreen = true;
						}else
						if (received.startsWith("(")) {
							int pos = received.indexOf(",");
							float x = Float.parseFloat(received.substring(1, pos - 1));
							float y = Float.parseFloat(received.substring(pos + 1, received.length() - 1));
							if(!(mGame.virtual_player.getState() == PlayerState.JUMPING))
								mGame.virtual_player.setState(PlayerState.IDLING);
							mGame.virtual_player.setPosition(new Vector2(x, y));
							
						}else if(received.equals("running_jumping")){
							mGame.isKeyPressed = 51;
							mGame.receiveIsJumping = true;
						}
						else if(received.startsWith("camera")){
							received = received.substring(6);
							float c = Float.parseFloat(received);
							mGame.game.vp_camera = c;
						}
						else {
							int key = Integer.parseInt(received);
							mGame.isKeyPressed = key;
						}
						
					}
				}
				//input da inviare
				toSend = mGame.sendPressedKey;
				
				if(toSend == 51 && toSendPrec == 51){		
					continue;
				}
				
				if(mGame.dead){
					mGame.dead = false;
					out.println("imdead");
				}else				
				//salto
				if(toSend == 51 && mGame.sendIsJumping){
					mGame.sendIsJumping = false;
					out.println("running_jumping");
				}else if (toSend > 0) {   //altri input
					if(toSend == 100)
						out.println("camera"+mGame.game.getCamera());
					else{
						out.println(toSend);
					}
	
				} else {
					if (toSendPrec > 0 && toSendPrec != 51) { // caso no keys: mando la posizione
						out.println(mGame.player.getPosition());
					}

				}
				//viene svuotato il buffer
				out.flush();
				
				toSendPrec = toSend;
				
				//reset degli input
				if(toSend == Input.Keys.Q){
					mGame.sendPressedKey = -1;
				} else if(toSend == Input.Keys.SPACE){
					mGame.sendPressedKey = -1;
				}
			
			}

		} catch (IOException e/* | InterruptedException e */) {
		}

	}

}
