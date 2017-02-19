/**
 * WindowPanel.java - It displays the various Windows
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

public class WindowPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	private ImageIcon image1 = null, image2 = null, image3 = null,
			image4 = null, image5 = null;

	private JToggleButton button1 = null, button2 = null, button3 = null,
			button4 = null, button5 = null;

	private DesignScreen designScreen = null;
	private RightPanel rightPanel = null;
	private DoorPanel doorPanel = null;
	private WallPanel wallPanel = null;

	public WindowPanel(RightPanel parentPanel, DesignScreen screen)
	{
		designScreen = screen;
		rightPanel = parentPanel;
		setLayout(new GridLayout(4, 2));

		JPanel panel = rightPanel.getWindows( );

		Dimension dim = new Dimension(panel.getWidth( ) - 5,
				panel.getHeight( ) - 5);
		setSize(dim);

		image1 = new ImageIcon(this.getClass( ).getResource(
				"Images/Windows/window1.jpg"));
		image2 = new ImageIcon(this.getClass( ).getResource(
				"Images/Windows/window2.jpg"));
		image3 = new ImageIcon(this.getClass( ).getResource(
				"Images/Windows/window3.jpg"));
		image4 = new ImageIcon(this.getClass( ).getResource(
				"Images/Windows/window4.jpg"));
		image5 = new ImageIcon(this.getClass( ).getResource(
				"Images/Windows/window5.jpg"));

		button1 = new JToggleButton(image1);
		button1.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button2.setSelected(false);
				button3.setSelected(false);
				button4.setSelected(false);
				button5.setSelected(false);
				if (button1.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"window1.wrl"));
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
				button4.setSelected(false);
				button5.setSelected(false);
				if (button2.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"window2.wrl"));
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
				button4.setSelected(false);
				button5.setSelected(false);
				if (button3.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"window3.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);
			}

		});

		button4 = new JToggleButton(image4);
		button4.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				button1.setSelected(false);
				button2.setSelected(false);
				button3.setSelected(false);
				button5.setSelected(false);
				if (button4.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"window4.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);
			}

		});

		button5 = new JToggleButton(image5);
		button5.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button1.setSelected(false);
				button2.setSelected(false);
				button3.setSelected(false);
				button4.setSelected(false);
				if (button5.isSelected( ))
				{
					designScreen.setComponentSelected(true, new File(
							"window5.wrl"));
					clearOthers( );
				}
				else
					designScreen.setComponentSelected(false, null);

			}

		});

		add(button1);
		add(button2);
		add(button3);
		add(button4);
		add(button5);
	}

	public void clearSelected( )
	{
		button1.setSelected(false);
		button2.setSelected(false);
		button3.setSelected(false);
		button4.setSelected(false);
		button5.setSelected(false);
	}

	public boolean isSelected( )
	{
		if (button1.isSelected( ))
			return true;
		if (button2.isSelected( ))
			return true;
		if (button3.isSelected( ))
			return true;
		if (button4.isSelected( ))
			return true;
		if (button5.isSelected( ))
			return true;

		return false;
	}

	public void clearOthers( )
	{
		doorPanel = rightPanel.getDoorPanel( );
		wallPanel = rightPanel.getWallPanel( );
		if (doorPanel.isSelected( ))
			doorPanel.clearSelected( );
		if (wallPanel.isSelected( ))
			wallPanel.clearSelected( );
	}
}
