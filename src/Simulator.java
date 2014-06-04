
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class Simulator extends JFrame{

	public static Facade facade;
	public static JLabel[] label = new JLabel[5];

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	static int X = (screen.width / 2); 
	static int Y = (screen.height / 2); 
	private static String[] columnNames = { "ID Car", "Speed", "Display"}; 
	static  DefaultTableModel model;
	private static JTable table;

	static void showCars() {

		facade = new Facade();
		facade.initSimulator();
		JFrame frame = new JFrame("Traffic situation");
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
				for(String[] macchina :createTable()) {
					tableModel.addRow(macchina); 	
				}
				
				for (int i = 0; i < facade.baseStation.channelsCreated; i++) {
					facade.baseStation.channels[i].getFullness(i);
				}
			}
		});

		splitPane.setRightComponent(btnAddRow);

		frame.add(splitPane);
		frame.add(btnAddRow, BorderLayout.SOUTH);
		frame.repaint();
		frame.setVisible(true);
	}



	private static String[][] createTable() {
		String[][] data = new String[facade.baseStation.allCars.size()+1][3];  

		int i=0;
		int h=0;
		
		for (int j = 0; j < facade.baseStation.allCars.size(); j++) {
		
		//for(ManualCar m : facade.baseStation.allCars){
			i++;

			for(h=0; h<3;h++)
				switch(h){
				case(0): data[i][h] = facade.baseStation.allCars.get(j).getCarId(); break;
				case(1): data[i][h] = "" + facade.baseStation.allCars.get(j).getSpeed(); break;
				case(2): data[i][h] = facade.baseStation.allCars.get(j).getDisplay(); break;
				}
		}
		
		return data;
	}

	public static void showChannels() {
		JPanel[] channel = new JPanel[5];

		label = new JLabel[5];

		JFrame channelWindow = new JFrame("Channel");

		Container body = channelWindow.getContentPane();

		body.setLayout(new GridLayout(5, 1, 10, 10));

		for( int i = 0; i < 5; i++) {    
			label[i] = new JLabel(buildText(i + 1));
			channel[i] = new JPanel();
			channel[i].add(label[i]);
			body.add(channel[i]);
		}


		channelWindow.setSize(500, 200);
		channelWindow.setLocation(X * 5 / 6 + 150, Y / 2 );
		channelWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		channelWindow.setVisible(true);
	}

	


	private static String buildText(int channel){
		String result;
		result = "Channel " + channel + "\t" + ":     ";

		result += "          " ;

		result+= "\t" + "0 %";
		return result;
	}

	public static void main(String args[]){

		Simulator.showChannels();
		Simulator.showCars();

	}
}
