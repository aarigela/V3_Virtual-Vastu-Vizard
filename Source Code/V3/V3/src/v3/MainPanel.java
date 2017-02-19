/**
 * MainPanel.java - Contains the Design View Components
 */

package v3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.media.j3d.Canvas3D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

public class MainPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen = null;
	private JSplitPane mainSplit = null;
	private RightPanel rightPanel = null;
	private Dimension panelDim = null;
	private Canvas3D leftCanvas3D = null;
	private DesignInfoWindow designInfoWindow = null;
	private JScrollPane jsp;
	private DesignScreen designScreen = null;

	public MainPanel( )
	{
		mainScreen = null;
	}

	public MainPanel(MainScreen screen)
	{
		mainScreen = screen;
		panelDim = new Dimension(mainScreen.getWidth( ) - 200, mainScreen
				.getHeight( ) - 195);

		setLayout(new BorderLayout( ));

		designScreen = new DesignScreen(this);

		leftCanvas3D = designScreen.getCanvas3D( );
		leftCanvas3D.setPreferredSize(new Dimension(panelDim.height,
				panelDim.height));
		leftCanvas3D.setMinimumSize(new Dimension(panelDim.height,
				panelDim.height));
		leftCanvas3D.setSize(new Dimension(panelDim.height, panelDim.height));
		designInfoWindow = designScreen.getInfoWindow( );

		rightPanel = new RightPanel(this);

		int horScrollBar = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		int verScrollBar = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

		jsp = new JScrollPane(rightPanel, verScrollBar, horScrollBar);

		mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCanvas3D,
				jsp);

		mainSplit.setDividerLocation(2 * mainScreen.getWidth( ) / 3);
		mainSplit.setPreferredSize(panelDim);
		mainSplit.setMaximumSize(panelDim);
		mainSplit.setSize(panelDim);
		// mainSplit.setContinuousLayout(true);

		add(mainSplit, BorderLayout.CENTER);

		JLabel label = new JLabel( );
		label.setPreferredSize(new Dimension(mainSplit.getWidth( ), 5));
		label.setOpaque(true);
		add(label, BorderLayout.NORTH);

		JLabel label2 = new JLabel( );
		label2.setPreferredSize(new Dimension(5, mainSplit.getHeight( )));
		label2.setOpaque(true);
		add(label2, BorderLayout.WEST);

		JLabel label3 = new JLabel( );
		label3.setPreferredSize(new Dimension(5, mainSplit.getHeight( )));
		label3.setOpaque(true);
		add(label3, BorderLayout.EAST);

		JLabel label4 = new JLabel( );
		label4.setPreferredSize(new Dimension(mainSplit.getWidth( ), 5));
		label4.setOpaque(true);
		add(label4, BorderLayout.SOUTH);

		setSize(mainScreen.getSize( ));
	}

	public void sizeChanged( )
	{
		mainSplit.setDividerLocation(2 * mainScreen.getWidth( ) / 3);
		rightPanel.sizeChanged( );
		leftCanvas3D.setSize(panelDim.height, panelDim.height);
		leftCanvas3D.repaint( );
	}

	public MainScreen getMainScreen( )
	{
		return mainScreen;
	}

	public Dimension getPanelDim( )
	{
		return panelDim;
	}

	public void setNewProject( )
	{
		designScreen.setNewScene( );
		leftCanvas3D = designScreen.getCanvas3D( );
		leftCanvas3D.setPreferredSize(new Dimension(panelDim.height,
				panelDim.height));
		leftCanvas3D.setMinimumSize(new Dimension(panelDim.height,
				panelDim.height));
		leftCanvas3D.setSize(new Dimension(panelDim.height, panelDim.height));
		mainSplit.setLeftComponent(leftCanvas3D);
	}

	public Canvas3D getLeftCanvas3D( )
	{
		return leftCanvas3D;
	}

	public DesignInfoWindow getDesignInfoWindow( )
	{
		return designInfoWindow;
	}

	public void saveVRMLFile(File saveFile)
	{
		designScreen.saveVRMLScene(saveFile);
	}

	public void openExistingProject(File selectedFile)
	{
		designScreen.loadVRMLScene(selectedFile);
	}

	public DesignScreen getDesignScreen( )
	{
		return designScreen;
	}

	public RightPanel getRightPanel( )
	{
		return rightPanel;
	}

	public void deleteComponent( )
	{
		if (designScreen != null)
			designScreen.deleteComponent( );
	}

}
