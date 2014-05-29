package Simulator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;


public class Simulator extends JFrame{
	public static Facade facade;

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	static int X = (screen.width / 2); 
	static int Y = (screen.height / 2); 
	private static String[] columnNames = { "Car id", "speed", "type", "display", "action"}; 
	private static String actualChannel = "channel 1";
	private static String[] chStrings = { "channel 5", "channel 4", "channel 3", "channel 2", "channel 1" };
	
	static void showCars() {
		facade = new Facade();
		
		
		JPanel contUp = new JPanel();

	
	

	
		JFrame carWindow = new JFrame("Cars");

		

		
		carWindow.add(contUp);
	
	 
	
		
		

		carWindow.setSize(740, 400);
		carWindow.setLocation(X / 4 - 120, Y / 2);
		carWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		carWindow.setVisible(true);
	   
	}
	
	

	public static void showChannels() {
		JPanel[] channel = new JPanel[5];
	
		JLabel[] label = new JLabel[5];
		
		JFrame channelWindow = new JFrame("Channel");
		
		Container body = channelWindow.getContentPane();

		body.setLayout(new GridLayout(5, 1, 10, 10));
		
		for( int i = 0; i < 5; i++){    //fino a channel.size()
			label[i] = new JLabel(buildText(i+1));
			channel[i] = new JPanel();
			channel[i].add(label[i]);
			body.add(channel[i]);
			
			
		}
	
		
		channelWindow.setSize(500, 200);
		channelWindow.setLocation(X * 5 / 6 + 250, Y / 2 - 100);
		channelWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		channelWindow.setVisible(true);
	}
	
	public static void showBase() {
		
		JFrame baseWindow = new JFrame("Base");
		
		Container body = baseWindow.getContentPane();

		body.setLayout(new GridLayout(5, 1, 10, 10));
		
	
		
		baseWindow.setSize(500, 200);
		baseWindow.setLocation(X * 5 / 6 + 250, Y * 4 / 5 + 150);
		baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseWindow.setVisible(true);
	}
	
		
	private static String buildText(int channel){
		String result;
		result = "Channel " + channel + "\t" + ":          ";
		//for(int i = 0; i < 10 - numcars.size()
		//		result += "  ";
		//for( int i = 0; i < numCars.size(); i++)
		// 		if(numCars[i] == Man)
		//			result += "M ";
		//		else ...
		result += "          " ;
		// if (Channel[channel] == busy)
		// 		result += "busy channel";
		//else ...
		result+= "\t" + "Busy channel";
		return result;
	}
	
	private static Object[][] createTable(String channel){
		Object[][] data = new Object[20][5]; 
		data[0] = columnNames;	  
		
		int count = 0;
		int i=0;
		int h=0;
				
		for(ManualCar m : facade.baseStation.allCars){
			 	i++;
			 	for(h=0; h<4;h++)
				switch(h){
				case(0): data[i][h] = m.getCarId(); break;
				case(1): data[i][h] = "" + m.getSpeed(); break;
				case(2): data[i][h] = m.getDisplay(); break;
				case(3): data[i][h] = "booooooo"; break;
				}
				
			}	
			
		
		return data;
	}
	
	public static void main(String args[]){
	
		Simulator.showCars();
		Simulator.showChannels();
		Simulator.showBase();
	}
	
	
}
