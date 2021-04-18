package com.krzem.wierd_2d_game;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;



public class World{
	public Engine engine;
	public String SAVES_DIR="data/";
	public int BLOCK_WIDTH;
	public int BLOCK_HEIGHT;
	public Vec2 OFFSET;
	public ArrayList<BaseBlock> blocks;
	public Player player;



	public World(Engine engine){
		this.engine=engine;
		this.OFFSET=new Vec2(0,0);
		BufferedImage i=this.engine.IMAGES.get("blocks/default.png");
		this.BLOCK_WIDTH=i.getWidth()*this.engine.SCALE;
		this.BLOCK_HEIGHT=i.getHeight()*this.engine.SCALE;
	}



	public void update(){
		this.player.update();
	}



	public void draw(Graphics g){
		this.player.draw(g);
		for (BaseBlock b:this.blocks){
			b.draw(g);
		}
	}



	public void set_block(int x,int y,String b){
		this.blocks.add(new BaseBlock(this.engine,b,x,y));
	}



	public void spawn_tree(int x,int y){
		int h=(int)(Math.random()*3)+2;
		int tl=(int)(Math.random()*2)+1;
		int lsl=(int)(Math.random()*(h-2))+1;
		if (h>=5){
			lsl=(int)(Math.random()*(h-5))+2;
		}
		for (int i=0;i<h;i++){
			this.set_block(x,y+i,"logY");
		}
		for (int i=0;i<tl;i++){
			this.set_block(x,y+h+i,"leaves");
		}
		for (int i=0;i<lsl;i++){
			for (int j=0;j<i+1;j++){
				this.set_block(x-1-j,y+h-i-2+tl,"leaves");
			}
			for (int j=0;j<i+1;j++){
				this.set_block(x+1+j,y+h-i-2+tl,"leaves");
			}
		}
	}



	public void create(String fn){
		File f=new File(this.SAVES_DIR+fn);
		if (f.exists()==false||f.isDirectory()==true){
			return;
		}
		this.blocks=new ArrayList<BaseBlock>();
		this.player=null;
		this.OFFSET=new Vec2(0,0);
		try{
			FileReader fr=new FileReader(this.SAVES_DIR+fn);
			BufferedReader br=new BufferedReader(fr);
			String line;
			while (true){
				line=br.readLine();
				if (line==null){
					break;
				}
				if (line.charAt(0)=='P'&&this.player==null){
					float px=Float.parseFloat(line.substring(1).split("/")[0]),py=Float.parseFloat(line.substring(1).split("/")[1]);
					String pn=line.substring(1).split("/")[2];
					this.player=new Player(this.engine,pn,px,py);
				}
				if (line.charAt(0)=='B'){
					int bx=Integer.parseInt(line.substring(1).split("/")[0]),by=Integer.parseInt(line.substring(1).split("/")[1]);
					String bn=line.substring(1).split("/")[2];
					this.blocks.add(new BaseBlock(this.engine,bn,bx,by));
				}
			}
			fr.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
