package com.krzem.wierd_2d_game;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.lang.Math;
import javax.swing.JFrame;



public class Engine{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice DEVICE=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=DEVICE.getDefaultConfiguration().getBounds();
	public static int MAX_FPS=60;
	public static final int SCALE=10;
	public float FPS=1;
	public Point MOUSE_POS=new Point(0,0);
	public Keyboard KEYBOARD;
	public World WORLD=null;
	public ImageLoader IMAGES;
	public JFrame frame;
	public Canvas canvas;
	private MouseEvent _mouse;



	public Engine(){
		this.frame_init();
		this.init();
		this.run();
	}



	public void init(){
		this.KEYBOARD=new Keyboard(this);
		this.IMAGES=new ImageLoader(this);
		this.WORLD=new World(this);
		this.WORLD.create("1.world");
	}
	public void frame_init(){
		this.frame=new JFrame("Wierd 2D Game");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		DEVICE.setFullScreenWindow(this.frame);
		this.canvas=new Canvas(this);
		this.canvas.setSize(WINDOW_SIZE.width,WINDOW_SIZE.height);
		this.canvas.setPreferredSize(new Dimension(WINDOW_SIZE.width,WINDOW_SIZE.height));
		Engine cls=this;
		this.canvas.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				cls._mouse=e;
			}
			public void mouseDragged(MouseEvent e){
				cls._mouse=e;
			}
		});
		this.canvas.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				cls.KEYBOARD.down(e);
			}
			public void keyReleased(KeyEvent e){
				cls.KEYBOARD.up(e);
			}
			public void keyTyped(KeyEvent e){
				cls.KEYBOARD.press(e);
			}
		});
		this.frame.setContentPane(this.canvas);
		this.canvas.requestFocus();
	}



	public void run(){
		Engine cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					Long s=System.currentTimeMillis();
					cls.update_events();
					cls.update();
					cls.canvas.repaint();
					Long d=System.currentTimeMillis()-s;
					if (d==0){
						d=1L;
					}
					float FPS=(float)Math.floor(1/(float)d*100000)/100;
					if (FPS>cls.MAX_FPS){
						try{
							Thread.sleep((long)(1/(float)cls.MAX_FPS*1000)-d);
						}
						catch (InterruptedException e){}
					}
					cls.FPS=(float)Math.floor(1/(float)(System.currentTimeMillis()-s)*100000)/100;
				}
			}
		}).start();
	}



	public void update_events(){
		if (this._mouse!=null){
			this.MOUSE_POS=new Point(this._mouse.getPoint());
			this._mouse=null;
		}
	}



	public void update(){
		this.WORLD.update();
		this.KEYBOARD.update();
	}



	public void draw(Graphics g){
		if (this.WORLD==null){
			return;
		}
		g.setColor(Color.white);
		g.fillRect(0,0,this.WINDOW_SIZE.width,this.WINDOW_SIZE.height);
		this.WORLD.draw(g);
		g.setFont(new Font("consolas",Font.PLAIN,40));
		g.setColor(Color.black);
		g.drawString("FPS: "+Float.toString(this.FPS),10,50);
		g.setColor(Color.blue);
		g.fillRect(this.MOUSE_POS.x-10,this.MOUSE_POS.y-10,20,20);
	}
}
