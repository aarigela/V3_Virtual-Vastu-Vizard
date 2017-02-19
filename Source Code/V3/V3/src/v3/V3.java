/**
 * V3.java - The main class of V3
 */

package v3;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class V3
{
	private static MainScreen screen1;
	private static SplashScreen splashScreen = null;

	public static void main(String[] args)
	{
		try
		{
			// Set System Look & Feel
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName( ));
		}
		catch (UnsupportedLookAndFeelException e)
		{

		}
		catch (ClassNotFoundException e)
		{

		}
		catch (InstantiationException e)
		{

		}
		catch (IllegalAccessException e)
		{

		}

		splashScreen = new SplashScreen( );
		splashScreen.play( );

		screen1 = new MainScreen( );
		screen1.setExtendedState(Frame.MAXIMIZED_BOTH);
		screen1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		screen1.addWindowListener(new WindowAdapter( )
		{
			public void windowClosing(WindowEvent e)
			{
				if (JOptionPane.showConfirmDialog(screen1,
						"Do you want to quit ?", "Confirmation Dialog Box",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		screen1.setVisible(true);
	}
}
