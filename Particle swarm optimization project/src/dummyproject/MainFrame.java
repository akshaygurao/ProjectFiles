/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummyproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author aksha
 */
public class MainFrame extends JFrame
{
	private PaintPanel panel1;
	private JPanel panel2;
	private Hashtable labelTable1;
	private JPanel container1;
	private JButton button1, button2;
	private JCheckBox checkbox1;
	//private JSlider slider1;
	private static JTextArea textarea1;
	private JScrollPane scroll1;
	
	public MainFrame()
	{
		super("Particle Swarm Optimization");
		
		container1 = new JPanel(new GridBagLayout());
		add(container1, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		add(panel2, BorderLayout.SOUTH);
		
		button1 = new JButton("Back to the begin");
		panel2.add(button1, BorderLayout.WEST);
		
		
		button2 = new JButton("Forward");
		panel2.add(button2, BorderLayout.WEST);
		
		checkbox1 = new JCheckBox("Iterate automatically");
		panel2.add(checkbox1, BorderLayout.CENTER);
		
		
		//slider1 = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, MIN_DELAY);
//		slider1.setLabelTable(labelTable1);
//		slider1.setPaintLabels(true);
//		add(slider1, BorderLayout.WEST);
		
		textarea1 = new JTextArea(20, 22);
		textarea1.setEditable(false);
		textarea1.setLineWrap(true);
                textarea1.setVisible(false);
		scroll1 = new JScrollPane(textarea1);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll1, BorderLayout.EAST);
		
		panel1 = new PaintPanel();
		container1.add(panel1);
		
		container1.addComponentListener(new ComponentListenerOne());
		button1.addActionListener(new ActionListenerOne(false));
                button1.setVisible(false);
                button2.setVisible(false);
		button2.addActionListener(new ActionListenerOne(true));
                checkbox1.setVisible(false);
		checkbox1.addItemListener(new ItemListenerOne());
		//slider1.addChangeListener(new ChangeListenerOne());
	}
	
	public static void appendText(String text)
	{
		textarea1.append(text + "\n");
		textarea1.setCaretPosition(textarea1.getDocument().getLength());
	}
	
	class ComponentListenerOne implements ComponentListener
	{

		@Override
		public void componentResized(ComponentEvent e) {

			int w = container1.getWidth();
			int h = container1.getHeight();
			int size = Math.min(w, h);
			panel1.setPreferredSize(new Dimension(size, size));
			container1.revalidate();
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class ActionListenerOne implements ActionListener
	{
		private boolean forward;
		
		public ActionListenerOne(boolean forward)
		{
			super();
			this.forward = forward;
		}
		
                @Override
		public void actionPerformed(ActionEvent e) 
		{
			//panel1.executeProcess(this.forward);
		}
		
	}
	
	class ItemListenerOne implements ItemListener
	{
                @Override
		public void itemStateChanged(ItemEvent e) 
		{
			//panel1.setAutoIterate(checkbox1.isSelected());
		}
	}
	
	class ChangeListenerOne implements ChangeListener
	{
                @Override
		public void stateChanged(ChangeEvent e) 
		{
			JSlider source = (JSlider)e.getSource();
			if(!source.getValueIsAdjusting())
			{
				int delay = (int)source.getValue();
				//panel1.setIterateTimerDelay(delay);
			}
		}
	}
}