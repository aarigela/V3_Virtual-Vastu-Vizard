/**
 * InputDialog.java - It accepts Input
 */

package v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputDialog extends JDialog
{

	private static final long serialVersionUID = 1L;
	private String componentName = "";

	private JLabel length = null, breadth = null, height = null, color = null;
	private JTextField lengthField = null, breadthField = null,
			heightField = null;
	private JButton colorButton = null, ok = null, clear = null;
	private JComboBox comboBox = null, comboBox1 = null, comboBox2 = null;

	private JPanel textPanel = null, buttonPanel = null;
	private RightPanel rightPanel = null;
	private ComponentData componentData = null;
	private Color selectedColor = null;

	public InputDialog(MainScreen parentDialog, boolean isModal, String name,
			RightPanel rPanel)
	{
		this(parentDialog, isModal, name);
		rightPanel = rPanel;
	}

	public InputDialog(MainScreen parentDialog, boolean isModal, String name)
	{
		super(parentDialog, isModal);
		componentName = name;
		setTitle(componentName);

		componentData = new ComponentData( );
		selectedColor = Color.white;

		setSize(250, 165);
		setResizable(false);
		setLocation(350, 350);

		comboBox = new JComboBox( );
		comboBox.addItem("Cm");
		comboBox.addItem("In");
		comboBox.addItem("Ft");
		comboBox.addItem("M");

		comboBox1 = new JComboBox( );
		comboBox1.addItem("Cm");
		comboBox1.addItem("In");
		comboBox1.addItem("Ft");
		comboBox1.addItem("M");

		comboBox2 = new JComboBox( );
		comboBox2.addItem("Cm");
		comboBox2.addItem("In");
		comboBox2.addItem("Ft");
		comboBox2.addItem("M");

		textPanel = new JPanel( );

		textPanel.setLayout(new GridLayout(4, 3));
		length = new JLabel("Length: ", Label.RIGHT);
		textPanel.add(length);
		lengthField = new JTextField(10);
		textPanel.add(lengthField);

		lengthField.addFocusListener(new FocusAdapter( )
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				try
				{
					float lengthVal = Float.parseFloat(lengthField.getText( ));
					int unit = comboBox.getSelectedIndex( );
					lengthVal = toMeter(lengthVal, unit);
					componentData.setComponentLength(lengthVal);
				}

				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(getDialog( ),
							"Enter correct value:", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		textPanel.add(comboBox);

		breadth = new JLabel("Breadth: ", Label.RIGHT);
		textPanel.add(breadth);
		breadthField = new JTextField(10);
		textPanel.add(breadthField);
		breadthField.addFocusListener(new FocusAdapter( )
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				try
				{
					float breadthVal = Float
							.parseFloat(breadthField.getText( ));
					int unit = comboBox1.getSelectedIndex( );
					breadthVal = toMeter(breadthVal, unit);
					componentData.setComponentBreadth(breadthVal);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(getDialog( ),
							"Enter correct value:", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		textPanel.add(comboBox1);

		height = new JLabel("Height: ", Label.RIGHT);
		textPanel.add(height);
		heightField = new JTextField(10);
		textPanel.add(heightField);
		heightField.addFocusListener(new FocusAdapter( )
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				try
				{
					float heightVal = Float.parseFloat(heightField.getText( ));
					int unit = comboBox.getSelectedIndex( );
					heightVal = toMeter(heightVal, unit);
					componentData.setComponentHeight(heightVal);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(getDialog( ),
							"Enter correct value:", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		textPanel.add(comboBox2);

		color = new JLabel("Color: ", Label.RIGHT);
		textPanel.add(color);
		colorButton = new JButton( );
		textPanel.add(colorButton);
		colorButton.setBackground(Color.black);
		colorButton.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				selectedColor = JColorChooser.showDialog(getDialog( ),
						componentName, selectedColor);
				if (selectedColor == null)
					selectedColor = Color.white;
				colorButton.setBackground(selectedColor);
				componentData.setComponentColor(selectedColor);
			}
		});

		add(textPanel, BorderLayout.CENTER);

		buttonPanel = new JPanel( );
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttonPanel.add(ok = new JButton("OK"));
		ok.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
			}
		});

		buttonPanel.add(clear = new JButton("CANCEL"));
		clear.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
				lengthField.setText("");
				breadthField.setText("");
				heightField.setText("");
				if (rightPanel != null)
				{
					rightPanel.clearSelectedButton( );
				}
				componentData = null;
			}
		});

		add(buttonPanel, BorderLayout.SOUTH);

	}

	protected JDialog getDialog( )
	{
		return this;
	}

	private float toMeter(float d, int i)
	{
		float val = (float) 0.0;
		switch (i)
		{
		case 0:
			val = d / 100;
			break;
		case 1:
			val = d / 39.3700787f;
			break;
		case 2:
			val = d / 3.2808399f;
			break;
		case 3:
			val = d / 1;
			break;
		default:
			val = 0;
			break;

		}
		return val;
	}

	public void showDialog(boolean b)
	{
		if (b)
		{
			setVisible(b);
		}
		else
		{
			setVisible(b);
		}
	}

	public ComponentData getComponentData( )
	{
		return componentData;
	}
}
