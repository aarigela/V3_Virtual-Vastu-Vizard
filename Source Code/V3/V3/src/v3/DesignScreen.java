/**
 * DesignScreen.java - Displays the Design View
 */

package v3;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

// Package to support VRML Operations
import cv97.SceneGraph;
import cv97.j3d.SceneGraphJ3dObject;
import cv97.node.Node;
import cv97.node.RootNode;
import cv97.node.ShapeNode;
import cv97.node.TransformNode;

public class DesignScreen extends JPanel implements Runnable, MouseListener,
		MouseMotionListener, KeyListener
{

	private static final long serialVersionUID = 1L;

	private VRMLCoreJ3D vrmlCore = null;
	private MainScreen mainScreen = null;
	private MainPanel mainPanel = null;
	private Canvas3D canvas3D = null;

	private File selectedFile = null;
	private File saveFile = null;

	private boolean isThreadRunning = false;
	private Thread simulationThread = null;

	private Dimension panelDim = null;

	private PopupMenu popupMenu = null;
	private MenuItem pCut = null, pCopy = null, pPaste = null, pReset = null,
			pDelete = null;

	private DesignInfoWindow infoWindow = null;
	private InputDialog inputDialog = null;
	private SceneGraphJ3dObject sceneGraphObject = null;
	private boolean isComponentSelected = false;
	private File addFile = null;
	private Cursor selectedCursor = null, defaultCursor = null;
	private String name = "Component";

	private RightPanel rightPanel = null;
	private ComponentData componentData = null;
	private MouseEvent mouseEvent = null;
	private ShapeNode cutComponent = null;

	private StatusBar statusBar = null;

	public DesignScreen( )
	{
		vrmlCore = null;
		mainScreen = null;
		mainPanel = null;
		canvas3D = null;
	}

	public DesignScreen(MainPanel panel)
	{
		mainPanel = panel;

		mainScreen = mainPanel.getMainScreen( );
		panelDim = new Dimension(mainScreen.getWidth( ) - 200, mainScreen
				.getHeight( ) - 195);

		vrmlCore = new VRMLCoreJ3D( );
		canvas3D = vrmlCore.getCanvas3D( );

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

		canvas3D.addMouseListener(this);
		canvas3D.addMouseMotionListener(this);

		popupMenu = getPopupMenu( );

		canvas3D.add(popupMenu);
		canvas3D.addKeyListener(this);
		add(canvas3D, BorderLayout.CENTER);

		setSize(panelDim.width, panelDim.height);

		infoWindow = new DesignInfoWindow(mainScreen, canvas3D);
		sceneGraphObject = vrmlCore.getSceneGraphJ3dObject( );

		selectedCursor = new Cursor(Cursor.HAND_CURSOR);
		defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

		statusBar = mainScreen.getStatusBar( );
	}

	public MainScreen getMainScreen( )
	{
		return mainScreen;
	}

	public MainPanel getMainPanel( )
	{
		return mainPanel;
	}

	public Canvas3D getCanvas3D( )
	{
		return canvas3D;
	}

	private SceneGraph getSceneGraph( )
	{
		if (vrmlCore != null)
			return vrmlCore.getSceneGraph( );
		else
			return null;
	}

	private PopupMenu getPopupMenu( )
	{
		PopupMenu popup = new PopupMenu( );

		pCut = new MenuItem("Cut");
		pCut.addActionListener(new CutActionListener( ));
		popup.add(pCut);

		pCopy = new MenuItem("Copy");
		pCopy.addActionListener(new CopyActionListener( ));
		popup.add(pCopy);

		pPaste = new MenuItem("Paste");
		pPaste.addActionListener(new PasteActionListener( ));
		popup.add(pPaste);

		popup.addSeparator( );

		pDelete = new MenuItem("Delete");
		pDelete.addActionListener(new DeleteActionListener( ));
		popup.add(pDelete);

		popup.addSeparator( );

		pReset = new MenuItem("Reset");
		pReset.addActionListener(new ResetActionListener( ));
		popup.add(pReset);

		return (popup);
	}

	public void loadVRMLScene(File vrmlFile)
	{
		stopSimulation( );

		selectedFile = vrmlFile;
		SceneGraph sceneGraph = getSceneGraph( );
		sceneGraph.setOption(SceneGraph.USE_PREPROCESSOR);
		sceneGraph.load(selectedFile);

		startSimulation( );
	}

	public void setNewScene( )
	{
		stopSimulation( );

		SceneGraph sceneGraph = getSceneGraph( );
		sceneGraph.clear( );
		sceneGraph.initialize( );

		startSimulation( );
	}

	public void saveVRMLScene(File vrmlFile)
	{
		saveFile = vrmlFile;

		stopSimulation( );

		SceneGraph sceneGraph = getSceneGraph( );
		sceneGraph.saveVRML(saveFile);
		selectedFile = saveFile;
		mainScreen.setSelectedFile(saveFile);

		startSimulation( );
	}

	private void startSimulation( )
	{
		SceneGraph sceneGraph = getSceneGraph( );
		if (sceneGraph.isSimulationRunning( ) == false)
		{
			vrmlCore.startSimulation( );
			start( );
		}
	}

	public void stopSimulation( )
	{
		SceneGraph sceneGraph = getSceneGraph( );
		if (sceneGraph.isSimulationRunning( ) == true)
		{
			vrmlCore.stopSimulation( );
			stop( );
		}
	}

	public void start( )
	{
		if (simulationThread == null)
		{
			simulationThread = new Thread(this);
			simulationThread.start( );
			isThreadRunning = true;
		}
	}

	private void stop( )
	{
		isThreadRunning = false;
		simulationThread = null;
	}

	public void run( )
	{
		while (isThreadRunning)
		{
			updateViewpoint( );
			repaint( );
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	public void updateViewpoint( )
	{
		int width = getWidth( );
		int height = getHeight( );
		vrmlCore.updateViewpoint(width, height);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		int buttonPressed = e.getButton( );
		mouseEvent = e;
		if (buttonPressed == MouseEvent.BUTTON1)
		{
			if (isComponentSelected)
			{
				canvas3D.setCursor(defaultCursor);
				isComponentSelected = false;
				buttonPressed = 0;
				return;
			}
			buttonPressed = 0;
			return;
		}

		Shape3D clickedShape = sceneGraphObject.pickShape3D(canvas3D,
				e.getX( ), e.getY( ));

		if (buttonPressed == MouseEvent.BUTTON3)
		{
			if (clickedShape == null)
			{
				setShapeSelected(false);
			}
			else
			{
				setShapeSelected(true);
			}
			popupMenu.show(canvas3D, e.getX( ), e.getY( ));
		}
		buttonPressed = 0;
	}

	private void addNewComponent(MouseEvent e)
	{
		if (componentData == null)
			return;

		stopSimulation( );

		Point3d p3d = new Point3d( );
		canvas3D.getPixelLocationInImagePlate(e.getX( ), e.getY( ), p3d);
		Transform3D temp = new Transform3D( );
		canvas3D.getImagePlateToVworld(temp);
		temp.transform(p3d);

		SceneGraph sg = new SceneGraph( );
		sg.addVRML97(addFile);

		SceneGraph sceneGraph = getSceneGraph( );
		sceneGraph.uninitialize( );
		RootNode rn = sceneGraph.getRootNode( );

		TransformNode tn = new TransformNode( );
		tn.setTranslation((float) p3d.x, (float) p3d.y, (float) p3d.z);

		for (Node node = sg.getNodes( ); node != null;)
		{
			Node node1 = node.next( );
			tn.addChildNode(node);
			if (node1 == null)
				break;
			node = node1;
		}

		tn.setData(componentData);

		rn.addChildNode(tn);
		sceneGraph.initialize( );
		sceneGraph.updateBoundingBox( );
		sceneGraph.setHeadlightState(true);
		vrmlCore.zoomAllViewpoint( );

		mainScreen.setFileSaved(false);
		startSimulation( );
	}

	private void setShapeSelected(boolean b)
	{
		if (b)
		{
			pCut.setEnabled(true);
			pCopy.setEnabled(true);
			pReset.setEnabled(true);
			pDelete.setEnabled(true);
		}
		else
		{
			pPaste.setEnabled(true);

			pCut.setEnabled(false);
			pCopy.setEnabled(false);
			pDelete.setEnabled(false);
		}
		if (cutComponent == null)
			pPaste.setEnabled(false);
		else
			pPaste.setEnabled(true);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (isComponentSelected)
		{
			rightPanel = mainPanel.getRightPanel( );

			inputDialog = new InputDialog(mainScreen, isComponentSelected,
					name, rightPanel);
			inputDialog.showDialog(true);

			componentData = inputDialog.getComponentData( );

			addNewComponent(e);

			isComponentSelected = false;
			canvas3D.setCursor(defaultCursor);
			statusBar.setMessage("V3");
			rightPanel.clearSelectedButton( );
		}
		else
		{
			vrmlCore.mousePressed(e);
			ShapeNode clickedShape = sceneGraphObject.pickShapeNode(
					getSceneGraph( ), canvas3D, e.getX( ), e.getY( ));
			ComponentData cData = null;
			if (clickedShape != null)
			{
				Node parentNode = clickedShape.getParentNode( );
				if (parentNode.isRootNode( ))
				{
					cData = (ComponentData) parentNode.getData( );
				}
				while (!parentNode.isRootNode( ))
				{
					Node temp = parentNode.getParentNode( );
					if (parentNode.isTransformNode( )
							&& parentNode.getData( ) != null)
						break;
					parentNode = temp;
				}
				if (parentNode != null && parentNode.getData( ) != null)
				{
					cData = (ComponentData) parentNode.getData( );
				}
			}
			infoWindow.showInfo(cData);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		vrmlCore.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		vrmlCore.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		vrmlCore.mouseReleased(e);
		if (isComponentSelected)
			statusBar.setCoordinate2(e.getPoint( ));
		else
			statusBar.setCoordinate1(e.getPoint( ));
	}

	private class CutActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			cutComponent( );
		}
	}

	private class CopyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			copyComponent( );
		}
	}

	private class PasteActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (cutComponent != null)
			{
				stopSimulation( );
				SceneGraph sg = getSceneGraph( );
				sg.uninitialize( );
				RootNode rn = sg.getRootNode( );

				Point3d p3d = new Point3d( );
				canvas3D.getPixelLocationInImagePlate(mouseEvent.getX( ),
						mouseEvent.getY( ), p3d);
				Transform3D temp = new Transform3D( );
				canvas3D.getImagePlateToVworld(temp);
				temp.transform(p3d);

				TransformNode tn = new TransformNode( );

				tn.setTranslation((float) p3d.x, (float) p3d.y, (float) p3d.z);

				for (Node node = cutComponent.getChildNodes( ); node != null; node = node
						.next( ))
				{
					tn.addChildNode(node);
				}
				
				tn.addChildNode(cutComponent);
				rn.addChildNode(tn);
				sg.initialize( );
				sg.updateBoundingBox( );
				sg.setHeadlightState(true);
				cutComponent = null;
				startSimulation( );

			}
		}
	}

	private class DeleteActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			deleteComponent( );
		}
	}

	private class ResetActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			vrmlCore.zoomAllViewpoint( );
			repaint( );
		}
	}

	public DesignInfoWindow getInfoWindow( )
	{
		return infoWindow;
	}

	public void copyComponent( )
	{
		if (mouseEvent != null)
		{
			stopSimulation( );
			ShapeNode clickedShape = sceneGraphObject.pickShapeNode(
					getSceneGraph( ), canvas3D, mouseEvent.getX( ), mouseEvent
							.getY( ));
			if (clickedShape != null)
			{
				SceneGraph sg = getSceneGraph( );
				sg.uninitialize( );
				cutComponent = clickedShape;
				sg.initialize( );
			}
			startSimulation( );
		}
	}

	public void cutComponent( )
	{
		if (mouseEvent != null)
		{
			stopSimulation( );
			ShapeNode clickedShape = sceneGraphObject.pickShapeNode(
					getSceneGraph( ), canvas3D, mouseEvent.getX( ), mouseEvent
							.getY( ));
			if (clickedShape != null)
			{
				Node parentNode = clickedShape.getParentNode( );
				if (parentNode.isRootNode( ))
				{
					clickedShape.remove( );
					cutComponent = clickedShape;
					mainScreen.setFileSaved(false);
					return;
				}
				while (!parentNode.isRootNode( ))
				{
					Node temp = parentNode.getParentNode( );
					if (temp.isRootNode( ))
						break;
					parentNode = temp;
				}
				if (parentNode != null)
				{
					parentNode.remove( );
					cutComponent = clickedShape;
				}
				mainScreen.setFileSaved(false);
			}
			startSimulation( );
		}
	}

	public void deleteComponent( )
	{
		if (mouseEvent != null)
		{
			ShapeNode clickedShape = sceneGraphObject.pickShapeNode(
					getSceneGraph( ), canvas3D, mouseEvent.getX( ), mouseEvent
							.getY( ));
			Node parentNode = clickedShape.getParentNode( );
			if (parentNode.isRootNode( ))
			{
				clickedShape.remove( );
				mainScreen.setFileSaved(false);
				return;
			}
			while (!parentNode.isRootNode( ))
			{
				Node temp = parentNode.getParentNode( );
				if (parentNode.isTransformNode( )
						&& parentNode.getData( ) != null)
					break;
				parentNode = temp;
			}
			if (parentNode != null)
				parentNode.remove( );
			mainScreen.setFileSaved(false);
		}
	}

	public SceneGraphJ3dObject getSceneGraphJ3dObject( )
	{
		return vrmlCore.getSceneGraphJ3dObject( );
	}

	public void setComponentSelected(boolean b, File file)
	{
		isComponentSelected = b;
		if (isComponentSelected)
		{
			canvas3D.setCursor(selectedCursor);
			addFile = file;
			statusBar.setMessage(addFile.getName( ));
		}
		else
		{
			canvas3D.setCursor(defaultCursor);
			addFile = null;
			statusBar.setMessage("V3");
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode( );
		switch (keyCode)
		{
		case KeyEvent.VK_F6:
			mainScreen.displayWalkthrough( );
			break;
		case KeyEvent.VK_ESCAPE:
			if (isComponentSelected)
			{
				setComponentSelected(false, null);
			}
		default:
		}
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
