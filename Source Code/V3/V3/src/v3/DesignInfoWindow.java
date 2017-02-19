/**
 * DesignInfoWindow.java - Displays Component Information.
 */

package v3;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DesignInfoWindow extends JPanel
{

	private static final long serialVersionUID = 1L;

	private MainScreen mainScreen = null;
	private Canvas3D canvas3D = null;

	private JLabel length = new JLabel("  Length : ", JLabel.LEFT);
	private JLabel height = new JLabel("  Height : ", JLabel.LEFT);
	private JLabel breadth = new JLabel("  Breadth : ", JLabel.LEFT);
	private JLabel color = new JLabel("  Color : ", JLabel.LEFT);
	private JLabel type = new JLabel("  Type : ", JLabel.LEFT);

	private JLabel lengthVal = null, heightVal = null, breadthVal = null,
			colorVal = null, typeVal = null;

	private JPanel panel = null;
	private JScrollPane scrollPane = null;
	private JButton editButton = null;
	private InputDialog inputDialog = null;
	private ComponentData componentData = null;

	public DesignInfoWindow( )
	{
		mainScreen = null;
		canvas3D = null;
	}

	public DesignInfoWindow(MainScreen screen, Canvas3D canvas)
	{
		mainScreen = screen;
		canvas3D = canvas;

		setSize(145, 130);

		panel = new JPanel( );
		panel.setLayout(new GridLayout(5, 2));

		panel.add(type);
		typeVal = new JLabel("Component", JLabel.LEFT);
		panel.add(typeVal);

		panel.add(length);
		lengthVal = new JLabel("0.0 M", JLabel.LEFT);
		panel.add(lengthVal);

		panel.add(height);
		heightVal = new JLabel("0.0 M", JLabel.LEFT);
		panel.add(heightVal);

		panel.add(breadth);
		breadthVal = new JLabel("0.0 M", JLabel.LEFT);
		panel.add(breadthVal);

		panel.add(color);
		colorVal = new JLabel( );
		colorVal.setBackground(Color.white);
		panel.add(colorVal);

		scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		inputDialog = new InputDialog(mainScreen, true, "Component");

		editButton = new JButton("Edit");
		add(editButton);
		editButton.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				inputDialog.setVisible(true);
			}

		});

		if (componentData == null)
			editButton.setEnabled(false);
		else
			editButton.setEnabled(true);

		editButton.setToolTipText("Edit");
	}

	public void showInfo(String tVal, String wVal, String hVal, String bVal)
	{
		typeVal.setText(tVal);
		lengthVal.setText(wVal);
		heightVal.setText(hVal);
		breadthVal.setText(bVal);
	}

	public void hideInfo( )
	{
		setVisible(false);
		canvas3D.repaint( );
	}

	public Insets insets( )
	{
		return new Insets(10, 10, 10, 10);
	}

	public void showInfo(ComponentData cData)
	{
		componentData = cData;
		if (cData == null)
		{
			typeVal.setText("Component");
			lengthVal.setText("0.0 M");
			heightVal.setText("0.0 M");
			breadthVal.setText("0.0 M");
			colorVal.setBackground(Color.white);

			editButton.setEnabled(false);
		}
		else
		{
			float wVal = componentData.getComponentLength( );
			float hVal = componentData.getComponentHeight( );
			float bVal = componentData.getComponentBreadth( );
			Color sColor = componentData.getComponentColor( );

			lengthVal.setText("" + wVal + " M");
			heightVal.setText("" + hVal + " M");
			breadthVal.setText("" + bVal + " M");
			colorVal.setBackground(sColor);

			editButton.setEnabled(true);
		}
	}
}
