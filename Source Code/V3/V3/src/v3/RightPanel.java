/**
 * RightPanel.java - It contains all the Components
 */

package v3;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

public class RightPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private MainPanel parentPanel = null;
	private JTabbedPane componentTab = null;
	private Dimension mainPanelDim = null;
	private JPanel walls, doors, windows;
	private DesignInfoWindow designInfoWindow = null;
	private JScrollPane doorScrollPane = null, windowScrollPane = null,
			wallScrollPane = null;
	private DoorPanel doorPanel = null;
	private WindowPanel windowPanel = null;
	private WallPanel wallPanel = null;
	private DesignScreen designScreen = null;

	public RightPanel( )
	{
		parentPanel = null;
		componentTab = null;
	}

	public RightPanel(MainPanel parent)
	{
		parentPanel = parent;
		mainPanelDim = parentPanel.getPanelDim( );

		setPreferredSize(new Dimension(mainPanelDim.width / 3,
				mainPanelDim.height));
		setMinimumSize(new Dimension(mainPanelDim.width / 4,
				mainPanelDim.height));
		designScreen = parentPanel.getDesignScreen( );

		componentTab = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.WRAP_TAB_LAYOUT);

		walls = new JPanel( );
		walls.setPreferredSize(new Dimension(getPreferredSize( ).width - 10,
				getPreferredSize( ).height / 6 * 5 - 10));

		doors = new JPanel( );
		doors.setPreferredSize(new Dimension(getPreferredSize( ).width - 10,
				getPreferredSize( ).height / 6 * 5 - 10));

		windows = new JPanel( );
		windows.setPreferredSize(new Dimension(getPreferredSize( ).width - 10,
				getPreferredSize( ).height / 6 * 5 - 10));

		doorPanel = new DoorPanel(this, designScreen);
		doorScrollPane = new JScrollPane(doorPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		doors.add(doorScrollPane);

		wallPanel = new WallPanel(this, designScreen);
		wallScrollPane = new JScrollPane(wallPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		walls.add(wallScrollPane);

		windowPanel = new WindowPanel(this, designScreen);
		windowScrollPane = new JScrollPane(windowPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		windows.add(windowScrollPane);

		componentTab.addTab("Walls", null, walls, "Walls");
		componentTab.addTab("Doors", null, doors, "Doors");
		componentTab.addTab("Windows", null, windows, "Windows");

		add(componentTab);
		designInfoWindow = parentPanel.getDesignInfoWindow( );
		add(designInfoWindow, BorderLayout.SOUTH);
	}

	public void sizeChanged( )
	{
		setSize(new Dimension(mainPanelDim.width / 3, mainPanelDim.height));
		setPreferredSize(new Dimension(mainPanelDim.width / 3,
				mainPanelDim.height));
		walls.setSize(getPreferredSize( ));
		doors.setSize(getPreferredSize( ));
		windows.setSize(getPreferredSize( ));
	}

	public JPanel getWalls( )
	{
		return walls;
	}

	public JPanel getDoors( )
	{
		return doors;
	}

	public JPanel getWindows( )
	{
		return windows;
	}

	public void clearSelectedButton( )
	{
		doorPanel.clearSelected( );
		windowPanel.clearSelected( );
		wallPanel.clearSelected( );
	}

	public DoorPanel getDoorPanel( )
	{
		return doorPanel;
	}

	public WindowPanel getWindowPanel( )
	{
		return windowPanel;
	}

	public WallPanel getWallPanel( )
	{
		return wallPanel;
	}
}
