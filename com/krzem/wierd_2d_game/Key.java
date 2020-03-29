package com.krzem.wierd_2d_game;



import java.awt.event.KeyEvent;



public class Key{
	public String TYPE;
	public KeyEvent evt;



	public Key(String t,KeyEvent evt){
		this.TYPE=t;
		this.evt=evt;
	}



	public Key copy(){
		return new Key(this.TYPE,this.evt);
	}
}