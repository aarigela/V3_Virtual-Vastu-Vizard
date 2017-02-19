/**
 * WallPanel.java - It displays the various Walls
 */

package v3;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class WallPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon image1 = null, image2 = null, image3 = null;

	private JToggleButton button1 = null, button2 = null, button3 = null;
	private DesignScreen designScreen = null;
	private RightPanel rightPanel = null;
	private DoorPanel doorPanel = null;
	private WindowPanel windowPanel = null;

	public WallPanel(RightPanel parentPanel, DesignScreen screen)
	{
		designScreen = screen;
		rightPanel = parentPanel;

		setLayout(new GridLayout(4, 2));

		JPanel panel = rightPanel.getWalls( );
		Dimension dim = new Dimension(panel.getWidth( ) - 5,
				panel.getHeight( ) - 5);
		setSize(dim);

		image1 = new ImageIcon(this.getClass( ).getResource(
				"Images/Walls/wall1.jpg"));
		image2 = new ImageIcon(this.getClass( ).getResource(
				"Images/Walls/wall2.jpg"));
		image3 = new ImageIcon(this.getClass( ).getResource(
				"Images/Walls/wall3.jpg"));

		button1 = new JToggleButton(image1);
		button1.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button2.setSelected(false);
				button3.setSelected(false);
				if (button1.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"wall1.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);
			}
		});

		button2 = new JToggleButton(image2);
		button2.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button1.setSelected(false);
				button3.setSelected(false);
				if (button2.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"wall2.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);

			}

		});

		button3 = new JToggleButton(image3);
		button3.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button1.setSelected(false);
				button2.setSelected(false);
				if (button3.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"wall3.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);

			}

		});

		add(button1);
		add(button2);
		add(button3);

	}

	public void clearSelected( )
	{
		button1.setSelected(false);
		button2.setSelected(false);
		button3.setSelected(false);
	}

	public boolean isSelected( )
	{
		if (button1.isSelected( ))
			return true;
		if (button2.isSelected( ))
			return true;
		if (button3.isSelected( ))
			return true;

		return false;
	}

	public void clearOthers( )
	{
		doorPanel = rightPanel.getDoorPanel( );
		windowPanel = rightPanel.getWindowPanel( );
		if (doorPanel.isSelected( ))
			doorPanel.clearSelected( );
		if (windowPanel.isSelected( ))
			windowPanel.clearSelected( );
	}
}
