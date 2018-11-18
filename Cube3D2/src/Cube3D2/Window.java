package Cube3D2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running;
	
	private Cube3D2 cube;
	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = (WIDTH / 16) * 9;
	public static String title = "3D Cube";
	public static JFrame frame;
	
	public Window() {
		frame = new JFrame();
		
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setVisible(true);
		init();
		start();
	}
	
	public void init() {
		cube = new Cube3D2();
	}
	
	public void update() {
		cube.update();
	}
	
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Draw here!
		/////////////////////
		cube.draw(g2d);
		/////////////////////
		
		g2d.dispose();
		
		bs.show();
 	}
	
	
	public void run() {
		double NsPerTick = 1000000000D / 60D;
		long lastTime = System.nanoTime();
		long lastMili = System.currentTimeMillis();
		double delta = 0;
		long updates = 0;
		long frames = 0;
		
		while(running) {
			delta += (System.nanoTime() - lastTime) / NsPerTick;
		    lastTime = System.nanoTime();
		    
			while (delta >= 1) {
				delta--;
				update();
				updates++;
			}
			
			frames++;
			draw();
			
			if (System.currentTimeMillis() - lastMili > 1000) {
				lastMili += 1000;
				frames = 0;
				updates = 0;
			}
			
			frame.requestFocus();
		}
	}
	
	public void start() {
		if (!running) {
			running = true;
			thread = new Thread(this);
			thread.run();
		} else {
			return;
		}
	}
	
	public void stop() {
		if (running) {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
	}
	
	public static void main(String args[]) {
		@SuppressWarnings("unused")
		Window window = new Window();
	}
	
}
