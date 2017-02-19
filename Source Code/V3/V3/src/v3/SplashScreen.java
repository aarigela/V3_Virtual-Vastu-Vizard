
package v3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow
{

	private static final long serialVersionUID = 1L;
	private JLabel splashLabel = null;
	private ImageIcon splashImage = null;
	private Dimension screenDim = null;

	public SplashScreen( )
	{
		setLayout(new BorderLayout( ));

		splashImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Splash/Splash.gif"));
		splashLabel = new JLabel(splashImage);
		screenDim = Toolkit.getDefaultToolkit( ).getScreenSize( );

	}

	public void play( )
	{
		getContentPane( ).add(splashLabel, BorderLayout.CENTER);
		setBounds((screenDim.width - splashImage.getIconWidth( )) / 2,
				(screenDim.height - splashImage.getIconHeight( )) / 2,
				splashImage.getIconWidth( ), splashImage.getIconHeight( ));
		setVisible(true);
		try
		{
			Thread.sleep(600);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace( );
		}
		setVisible(false);
	}

}
