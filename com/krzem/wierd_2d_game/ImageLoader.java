package com.krzem.wierd_2d_game;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;



public class ImageLoader{
	public Engine engine;
	private Map<String,BufferedImage> imgl;



	public ImageLoader(Engine engine){
		this.engine=engine;
		this.imgl=new HashMap<String,BufferedImage>();
	}



	public void load(String d){
		this.load(d,"");
	}
	public void load(String d,String p){
		for (File f:new File(d).listFiles()){
			try{
				this.imgl.put(p+f.getName(),(BufferedImage)ImageIO.read(f));
			}
			catch (IOException e){
				if (f.getName().endsWith(".png")){
					e.printStackTrace();
				}
				else{
					this.load(d+"\\"+f.getName(),f.getName()+"/");
				}
			}
		}
	}



	public BufferedImage get(String n){
		return this.imgl.get(n);
	}
}