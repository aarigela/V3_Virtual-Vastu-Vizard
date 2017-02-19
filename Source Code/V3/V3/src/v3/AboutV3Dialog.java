/**
 * AboutV3Dialog.java - Dialog Box for the About panel.
 */

package v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

public class AboutV3Dialog extends Dialog implements KeyListener
{
	private AboutDisplay mainDisplay;
	private static final long serialVersionUID = 1L;

	public AboutV3Dialog(Frame parentFrame, boolean modal)
	{
		super(parentFrame, modal);

		setTitle("About - Virtual Vastu Vizard");
		setLayout(new BorderLayout(15, 15));
		setBackground(new Color(255, 239, 186));
		setMinimumSize(new Dimension(500, 300));

		mainDisplay = new AboutDisplay(this);
		add(mainDisplay, BorderLayout.CENTER);

		JLabel label1 = new JLabel( );
		label1.setMaximumSize(new Dimension(getWidth( ), 15));
		label1.setBackground(new Color(255, 239, 186));
		label1.setOpaque(true);
		add(label1, BorderLayout.NORTH);

		JLabel label2 = new JLabel( );
		label2.setSize(15, getHeight( ));
		label2.setBackground(new Color(255, 239, 186));
		label2.setOpaque(true);
		add(label2, BorderLayout.EAST);

		JLabel label3 = new JLabel( );
		label3.setSize(getWidth( ), 15);
		label3.setBackground(new Color(255, 239, 186));
		label3.setOpaque(true);
		add(label3, BorderLayout.SOUTH);

		JLabel label4 = new JLabel( );
		label4.setSize(15, getHeight( ));
		label4.setBackground(new Color(255, 239, 186));
		label4.setOpaque(true);
		add(label4, BorderLayout.WEST);

		addKeyListener(this);
		addWindowListener(new WindowAdapter( )
		{
			public void windowClosing(WindowEvent e)
			{
				dispose( );
			}
		});

		setResizable(false);
		pack( );
		setLocationRelativeTo(parentFrame);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode( );
		if (keyCode == KeyEvent.VK_ESCAPE)
			dispose( );
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

}
