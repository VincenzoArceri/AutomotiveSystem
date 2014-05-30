
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Simulator extends JFrame{

	public static Facade facade;

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	static int X = (screen.width / 2); 
	static int Y = (screen.height / 2); 
	private static String[] columnNames = { "Car id", "speed", "type", "action", "display"}; 

	private static String[] chStrings = { "channel 5", "channel 4", "channel 3", "channel 2", "channel 1" };

	static  DefaultTableModel model;

	private JFrame frame;
	private static JScrollPane scrollPane;
	private static JTable table;
	private static DefaultTableModel tableModel;

	static void showCars() {

		facade = new Facade();
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 2));
		splitPane.setLeftComponent(scrollPane);

		final DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);

		table = new JTable(tableModel);

		table.setBounds(0, 0, 500, 500);

		scrollPane.setViewportView(table);

		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.INSERT) {
					JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
					scrollBar.setValue(scrollBar.getMaximum());
				}
			}
		});

		JButton btnAddRow = new JButton("Refresh");
		btnAddRow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModel.setRowCount(0);
				for(String[] macchina :createTable()){
					tableModel.addRow(macchina); 	
				}
			}
		});

		splitPane.setRightComponent(btnAddRow);

		frame.add(splitPane);
		frame.add(btnAddRow, BorderLayout.SOUTH);
		frame.repaint();
		frame.setVisible(true);

	}



	private static String[][] createTable(){
		String[][] data = new String[facade.baseStation.allCars.size()+1][5]; 
		//data[0] = columnNames;	  

		int i=0;
		int h=0;

		for(ManualCar m : facade.baseStation.allCars){
			i++;
			
			for(h=0; h<4;h++)
				switch(h){
				case(0): data[i][h] = m.getCarId(); break;
				case(1): data[i][h] = "" + m.getSpeed(); break;
				case(2): data[i][h] = m.getDisplay(); break;
				//case(3): data[i][h] = "booooooo"; break;
				case(3): data[i][h] =  "" + (int)(Math.random()*(10 -1))+1; break;

				}
		}
		
		return data;
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
		channelWindow.setLocation(X * 5 / 6 + 150, Y / 2 );
		channelWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		channelWindow.setVisible(true);
	}

	public static void showBase() {

		JFrame baseWindow = new JFrame("Base");

		Container body = baseWindow.getContentPane();

		body.setLayout(new GridLayout(3, 1, 2, 2));

		JButton Bottone1 = new JButton("Action 1");
		JButton Bottone2 = new JButton("Action 2");
		JButton Bottone3 = new JButton("Action 3");


		Bottone1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		Bottone2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		Bottone3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		body.add(Bottone1);
		body.add(Bottone2);
		body.add(Bottone3);

		baseWindow.setSize(300, 100);
		baseWindow.setLocation(X * 5 / 6 + 250, Y * 4 / 5 +100);
		baseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseWindow.setVisible(true);
	}


	private static String buildText(int channel){
		String result;
		result = "Channel " + channel + "\t" + ":     ";

		result += "          " ;

		result+= "\t" + "Busy channel";
		return result;
	}

	public static void main(String args[]){

		Simulator.showChannels();
		Simulator.showBase();
		Simulator.showCars();

	}
}
