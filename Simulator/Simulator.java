package Simulator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Simulator extends JFrame{

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	static int X = (screen.width / 2); 
	static int Y = (screen.height / 2); 
	private static String[] columnNames = { "Car id", "speed", "type", "display", "action"}; 
	private static String actualChannel;
	private static String[] chStrings = { "channel 5", "channel 4", "channel 3", "channel 2", "channel 1" };
	
	private static void showCars() {
		
		JPanel contUp = new JPanel();
		JPanel contDown = new JPanel();

		JComboBox chList = new JComboBox(chStrings);
		chList.setSelectedIndex(4);

		chList.addActionListener(
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				JComboBox cb = (JComboBox)e.getSource();
		        String chName = (String)cb.getSelectedItem();
		        actualChannel = chName;
			}
		});
		
		JTable table = new JTable(createTable(actualChannel), chStrings);
		
		JFrame carWindow = new JFrame("Cars");

		//Container body = carWindow.getContentPane();

		
		carWindow.add(contUp);
		table.setPreferredSize(new Dimension(700, 320));
	
		contUp.add(chList, BorderLayout.LINE_START);
		contUp.add(table, BorderLayout.LINE_END);
		

		carWindow.setSize(700, 300);
		carWindow.setLocation(X / 4 - 100, Y / 3 - 80);
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
		channelWindow.setLocation(X * 5 / 6 + 250, Y / 2 + 100);
		channelWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		channelWindow.setVisible(true);
	}
	
	public static void showBase() {
		
		JFrame baseWindow = new JFrame("Base");
		
		Container body = baseWindow.getContentPane();

		body.setLayout(new GridLayout(5, 1, 10, 10));
		
	
		
		baseWindow.setSize(500, 200);
		baseWindow.setLocation(X  / 2 - 180, Y * 2 / 3 + 150);
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
		
		/*carinchannel1[] car1 = ...
		 * carinchannel1[] car2 = ...
		 * 
		 * switch(channel) {
		 * case ("channel1"): 
		 * for(int i = 0; i < CarInChannel;i++)
			data[i] = {
				carinchannel1[i].carId, ...carSpeed, ...carType, ...carDisplay, ...carAction
			}
			
			...
		}
		
		*/
			
		
		return data;
	}
	
	public static void main(String[] args){
		showCars();
		showChannels();
		showBase();
	}
	
}
