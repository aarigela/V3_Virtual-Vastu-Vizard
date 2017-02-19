/**
 * StatusBar.java - It displays the status bar.
 */

package v3;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

class StatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private String message, coordinate1, coordinate2;
	private JLabel statusMessage, position1, position2;
	private JFrame parentFrame;
	private Dimension parentFrameSize;

	private StatusBar( )
	{
		message = "V3";
		coordinate1 = "Mouse Position";
		coordinate2 = "Drag";
		setPreferredSize(new Dimension(10, 23));
		setLayout(new BorderLayout( ));
		setBackground(SystemColor.control);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setToolTipText("StatusBar");
	}

	public StatusBar(JFrame frame)
	{
		this( );
		parentFrame = frame;
		parentFrameSize = parentFrame.getSize( );

		coordinate1 = String.valueOf(parentFrame.getWidth( )) + " * "
				+ String.valueOf(parentFrame.getHeight( ));
		coordinate2 = new String(coordinate1);

		statusMessage = new JLabel(message);
		statusMessage.setMinimumSize(new Dimension(
				parentFrame.getWidth( ) * 6 / 8, 20));
		statusMessage.setMaximumSize(new Dimension(
				parentFrame.getWidth( ) * 6 / 8, 20));
		statusMessage.setPreferredSize(new Dimension(
				parentFrame.getWidth( ) * 6 / 8, 20));
		statusMessage.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		statusMessage.setOpaque(false);
		add(statusMessage, BorderLayout.WEST);

		position1 = new JLabel(coordinate1);
		position1
				.setMinimumSize(new Dimension(parentFrame.getWidth( ) / 8, 20));
		position1
				.setMaximumSize(new Dimension(parentFrame.getWidth( ) / 8, 20));
		position1.setPreferredSize(new Dimension(parentFrame.getWidth( ) / 8,
				20));
		position1.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		position1.setOpaque(false);
		add(position1, BorderLayout.CENTER);

		position2 = new JLabel(coordinate2);
		position2
				.setMinimumSize(new Dimension(parentFrame.getWidth( ) / 8, 20));
		position2
				.setMaximumSize(new Dimension(parentFrame.getWidth( ) / 8, 20));
		position2.setPreferredSize(new Dimension(parentFrame.getWidth( ) / 8,
				20));
		position2.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		position2.setOpaque(false);
		add(position2, BorderLayout.EAST);

		frame.addComponentListener(new ComponentAdapter( )
		{
			public void componentResized(ComponentEvent e)
			{
				parentFrameSize = parentFrame.getSize( );
				resize( );
			}
		});

	}

	public StatusBar(String msg, JFrame frame)
	{
		this(frame);
		message = msg;
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		statusMessage.setText(message);
		position1.setText(coordinate1);
		position2.setText(coordinate2);
	}

	private void resize( )
	{
		statusMessage.setPreferredSize(new Dimension(
				parentFrame.getWidth( ) * 6 / 8, 20));
		position1
				.setPreferredSize(new Dimension(parentFrameSize.width / 8, 20));
		position2
				.setPreferredSize(new Dimension(parentFrameSize.width / 8, 20));
		coordinate1 = String.valueOf(parentFrameSize.width) + " * "
				+ String.valueOf(parentFrameSize.height);
		coordinate2 = new String(coordinate1);
		repaint( );
	}

	public void setMessage(String msg)
	{
		message = msg;
		repaint( );
	}

	public void setCoordinate1(Point point)
	{
		coordinate1 = point.getX( ) + " * " + point.getY( );
		repaint( );
	}

	public void setCoordinate1(String msg)
	{
		coordinate1 = msg;
		repaint( );
	}

	public void setCoordinate1(int x, int y)
	{
		coordinate1 = String.valueOf(x) + " * " + String.valueOf(y);
		repaint( );
	}

	public void setCoordinate2(Point point)
	{
		coordinate2 = point.getX( ) + " * " + point.getY( );
		repaint( );
	}

	public void setCoordinate2(String msg)
	{
		coordinate2 = msg;
		repaint( );
	}

	public void setCoordinate2(int x, int y)
	{
		coordinate2 = String.valueOf(x) + " * " + String.valueOf(y);
		repaint( );
	}

	public void showStatus( )
	{
		setVisible(true);
	}

	public void hideStatus( )
	{
		setVisible(false);
	}
}
