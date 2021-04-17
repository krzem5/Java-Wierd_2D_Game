package com.krzem.wierd_2d_game;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
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



	public BufferedImage get(String n){
		if (this.imgl.get(n)==null){
			try{
				InputStream is=ImageLoader.class.getResourceAsStream("/rsrc/"+n);
				this.imgl.put(n,(BufferedImage)ImageIO.read(is));
				is.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.imgl.get(n);
	}
}
