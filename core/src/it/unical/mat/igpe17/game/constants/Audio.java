package it.unical.mat.igpe17.game.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import it.unical.mat.igpe17.game.screens.Settings;

public class Audio {
	
	public static Sound coin_sound = Gdx.audio.newSound(Gdx.files.internal("audio/coin.wav"));
	public static Sound shot = Gdx.audio.newSound(Gdx.files.internal("audio/shot.wav"));
	public static Sound magic_key = Gdx.audio.newSound(Gdx.files.internal("audio/magic_key.wav"));
	public static Sound game_music = Gdx.audio.newSound(Gdx.files.internal("audio/game_music.wav"));
	public static Sound game_menu_music = Gdx.audio.newSound(Gdx.files.internal("audio/game_menu_music.wav"));
	
	public static boolean BACKGROUND_MUSIC = false; 
	public static boolean GAME_MUSIC = false; 
	
	static{
		game_music.loop();
		game_music.pause();
		
		game_menu_music.loop();
		game_menu_music.pause();
	}
	
	
	public static void playShot(){
		if(Settings.ENABLE_SOUND){
			shot.setVolume(shot.play(),0.35f);
		}
	}
	public static void playCoin(){
		if(Settings.ENABLE_SOUND){
			coin_sound.play();
		}
	}
	public static void playMagicKey(){
		if(Settings.ENABLE_SOUND){
			magic_key.play();
		}
	}
	
	public static void playGameMusic(){
		if(GAME_MUSIC && Settings.ENABLE_MUSIC){
			game_music.resume();
		}
	}
	public static void reloadGameMusic(){
		if(GAME_MUSIC && Settings.ENABLE_MUSIC){
			game_music.stop();
			game_music.loop();
		}
	}
	
	public static void playGameMenuMusic(){
		if(BACKGROUND_MUSIC && Settings.ENABLE_MUSIC){
			game_menu_music.resume();
		}
	}
	
	public static void resumeGameMusic(){
		
	}

}
