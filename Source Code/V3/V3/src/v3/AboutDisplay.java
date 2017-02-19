/**
 * AboutDisplay.java - It displays the information about V3
 */

package v3;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class AboutDisplay extends JPanel implements Runnable
{
	private static final long serialVersionUID = -5879383685410500176L;
	private String msg;

	private Color mainColor;
	private Color shadowColor;

	private Border mainBorder;

	private Font headingFont, mainFont;
	private FontMetrics fontInfo;
	private Thread displayThread;
	private boolean running;

	private int xpos, ypos;
	private int maxSize, stringWidth;

	private boolean imageDisplayed = false;
	private JLabel v3Label = null;

	private Dialog mainDialog;
	private Graphics graphicsContext;

	public AboutDisplay(Dialog aboutDialog)
	{
		msg = "Virtual Vastu Vizard";
		mainColor = new Color(255, 0, 0);
		shadowColor = new Color(0, 102, 51);

		setLayout(null);

		ImageIcon v3ImageIcon = new ImageIcon(this.getClass( ).getResource(
				"Images/Logo/V3 LOGO.jpg"));
		v3Label = new JLabel(v3ImageIcon);
		v3Label.setSize(v3ImageIcon.getIconWidth( ), v3ImageIcon
				.getIconHeight( ));

		headingFont = new Font("Monotype Corsiva", Font.BOLD, 36);
		mainFont = new Font("Lucida Console", Font.BOLD, 22);

		setBackground(new Color(255, 239, 186));
		setForeground(new Color(191, 62, 255));

		mainBorder = BorderFactory.createEtchedBorder(mainColor, shadowColor);
		setBorder(BorderFactory.createTitledBorder(mainBorder, " V3 ",
				TitledBorder.CENTER, TitledBorder.TOP, headingFont, Color.RED));

		setPreferredSize(new Dimension(300, 300));
		setMaximumSize(new Dimension(300, 300));

		mainDialog = aboutDialog;
		mainDialog.addWindowListener(new WindowAdapter( )
		{
			public void windowClosing(WindowEvent e)
			{
				stopThread( );
			}
		});

		ypos = 290;
		displayThread = new Thread(this);
		running = true;
		displayThread.start( );

		setVisible(true);
	}

	public void run( )
	{
		while (running)
		{
			if (graphicsContext == null)
				continue;

			paintIcon( );
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}

			v3Label.setVisible(false);

			paintHeading( );
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}

			paintCreators( );
		}
	}

	private void paintIcon( )
	{
		boolean display = true;
		imageDisplayed = true;
		while (display)
		{
			msg = null;
			int width = v3Label.getWidth( );
			if (ypos <= 30)
			{
				ypos = getHeight( );
				display = false;
				imageDisplayed = false;
			}
			xpos = (maxSize - width) / 2;
			repaint( );
			ypos -= 2;
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace( );
			}

		}
	}

	private void paintHeading( )
	{
		boolean display = true;
		while (display)
		{
			int tempx, tempy;
			mainFont = new Font("Edwardian Script ITC", Font.BOLD, 48);
			msg = "Virtual Vastu Vizard";
			fontInfo = graphicsContext.getFontMetrics(mainFont);
			Rectangle2D fontRect = fontInfo.getStringBounds(msg,
					graphicsContext);
			int stringWidth = (int) fontRect.getWidth( );
			int stringHeight = (int) fontRect.getHeight( );
			xpos = (maxSize - stringWidth) / 2;
			if (ypos <= getHeight( ) / 2)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace( );
				}
				msg = null;
				repaint( );
				ypos = getHeight( );
				display = false;
				return;
			}
			ypos -= 2;
			repaint(xpos, ypos - stringHeight, stringWidth + 20, stringHeight
					+ fontInfo.getMaxDescent( ) + 5);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace( );
			}
			tempx = xpos;
			tempy = ypos;
			msg = "V3";
			int ypos1 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos1 = (maxSize - stringWidth) / 2;
			ypos = ypos1;
			xpos = xpos1;
			repaint(xpos1, ypos1 - stringHeight, stringWidth + 20, getHeight( )
					- ypos1 + stringHeight);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			msg = "Virtual Vastu Vizard";
			xpos = tempx + 58;
			ypos = tempy + 58;
			msg = "Version 1.0.0.0";
			int ypos2 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos2 = (maxSize - stringWidth) / 2;
			ypos = ypos2;
			xpos = xpos2;
			repaint(xpos2, ypos2 - stringHeight, stringWidth + 20, getHeight( )
					- ypos2 + stringHeight);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			xpos = tempx;
			ypos = tempy;
		}
	}

	private void paintCreators( )
	{
		boolean display = true;
		while (display)
		{
			int tempx, tempy;
			mainFont = new Font("Edwardian Script ITC", Font.BOLD, 33);
			msg = "Created By";
			fontInfo = graphicsContext.getFontMetrics(mainFont);
			Rectangle2D fontRect = fontInfo.getStringBounds(msg,
					graphicsContext);
			int stringWidth = (int) fontRect.getWidth( );
			int stringHeight = (int) fontRect.getHeight( );
			xpos = (maxSize - stringWidth) / 2;
			if (ypos <= getHeight( ) / 3)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace( );
				}
				repaint( );
				ypos = getHeight( );
				display = false;
			}
			ypos -= 2;
			repaint(xpos, ypos - stringHeight, stringWidth + 20, stringHeight
					+ fontInfo.getMaxDescent( ));
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace( );
			}
			tempx = xpos - 3;
			tempy = ypos - 3;
			msg = "Anshul Jain";
			int ypos1 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos1 = (maxSize - stringWidth) / 2;
			ypos = ypos1;
			xpos = xpos1;
			repaint(xpos1, ypos1 - stringHeight + 15, stringWidth + 20,
					getHeight( ) - ypos1 + stringHeight - 5);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			msg = "Created By";
			xpos = tempx + 46;
			ypos = tempy + 46;
			msg = "Aaditya Arigela";
			int ypos2 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos2 = (maxSize - stringWidth) / 2;
			ypos = ypos2;
			xpos = xpos2;
			repaint(xpos2, ypos2 - stringHeight + 10, stringWidth + 10,
					getHeight( ) - ypos2 + stringHeight - 5);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			xpos = tempx + 91;
			ypos = tempy + 91;
			msg = "Yogesh Jadhav";
			int ypos3 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos3 = (maxSize - stringWidth) / 2;
			ypos = ypos3;
			xpos = xpos3;
			repaint(xpos3, ypos3 - stringHeight + 10, stringWidth + 10,
					getHeight( ) - ypos3 + stringHeight - 5);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			xpos = tempx + 135;
			ypos = tempy + 135;
			msg = "Chetan Patil";
			int ypos4 = ypos + fontInfo.getHeight( );
			fontRect = fontInfo.getStringBounds(msg, graphicsContext);
			stringWidth = (int) fontRect.getWidth( );
			stringHeight = (int) fontRect.getHeight( );
			int xpos4 = (maxSize - stringWidth) / 2;
			ypos = ypos4;
			xpos = xpos4;
			repaint(xpos4, ypos4 - stringHeight + 10, stringWidth + 10,
					getHeight( ) - ypos4 + stringHeight - 5);
			try
			{
				Thread.sleep(40);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace( );
			}
			xpos = tempx;
			ypos = tempy;
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (graphicsContext == null)
			setGraphicsContext(g);
		else
		{
			if (imageDisplayed)
			{
				add(v3Label);
				v3Label.setBounds(xpos, ypos, v3Label.getWidth( ), v3Label
						.getHeight( ));
				v3Label.setVisible(true);
			}
			else
			{
				if (msg == null)
					return;
				g.setFont(mainFont);
				fontInfo = g.getFontMetrics( );
				Rectangle2D fontRect = fontInfo.getStringBounds(msg, g);
				stringWidth = (int) fontRect.getWidth( );
				g.drawString(msg, (maxSize - stringWidth) / 2, ypos);
			}
		}
	}

	private void stopThread( )
	{
		running = false;
	}

	private void setGraphicsContext(Graphics g)
	{
		graphicsContext = g;
		maxSize = 461;
	}
}
