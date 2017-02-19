/**
 * AboutDisplay.java - It displays the information about V3
 * 
 * Copyright (C) Satoshi Konno 1997-1998
 * 
 */

package v3;

import java.awt.*;
import java.awt.event.*;

import javax.media.j3d.Canvas3D;

import com.sun.j3d.utils.universe.*;

import cv97.*;
import cv97.node.*;
import cv97.j3d.*;

public class VRMLCoreJ3D implements Constants, MouseListener,
		MouseMotionListener
{

	private SceneGraph mSceneGraph = null;
	private SceneGraphJ3dObject mSceneGraphObject = null;
	private Canvas3D mCanvas3D = null;

	public SceneGraph getSceneGraph( )
	{
		return mSceneGraph;
	}

	public SceneGraphJ3dObject getSceneGraphJ3dObject( )
	{
		return mSceneGraphObject;
	}

	public Canvas3D getCanvas3D( )
	{
		return mCanvas3D;
	}

	public VRMLCoreJ3D( )
	{
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration( );
		mCanvas3D = new Canvas3D(config);
		
		mSceneGraph = new SceneGraph( );
		mSceneGraphObject = new SceneGraphJ3dObject(mCanvas3D, mSceneGraph);
		getSceneGraph( ).setObject(mSceneGraphObject);

	}

	public void zoomAllViewpoint( )
	{
		SceneGraph sg = getSceneGraph( );
		ViewpointNode view = sg.getViewpointNode( );
		if (view == null)
			view = sg.getDefaultViewpointNode( );

		sg.updateBoundingBox( );

		float bboxSize[] = sg.getBoundingBoxSize( );

		view.setPosition(0.0f, 0.0f ,bboxSize[2]*5 );
		view.setOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public void startSimulation( )
	{
		SceneGraph sg = getSceneGraph( );
		if (sg.isSimulationRunning( ) == false)
		{
			sg.initialize( );
			sg.startSimulation( );
			sg.setHeadlightState(true);
			zoomAllViewpoint( );
		}
	}

	public void stopSimulation( )
	{
		SceneGraph sg = getSceneGraph( );
		if (sg.isSimulationRunning( ) == true)
		{
			sg.stopSimulation( );
		}
	}

	private int mMouseX = 0;
	private int mMouseY = 0;
	private int mMouseButton = 0;

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		if (e.getModifiers( ) == MouseEvent.BUTTON1_MASK)
			mMouseButton = 1;
		if (e.getModifiers( ) == MouseEvent.BUTTON3_MASK)
			mMouseButton = 3;
	}

	public void mouseReleased(MouseEvent e)
	{
		if (e.getModifiers( ) == MouseEvent.BUTTON1_MASK)
			mMouseButton = 0;
		if (e.getModifiers( ) == MouseEvent.BUTTON3_MASK)
			mMouseButton = 0;
	}

	public void mouseDragged(MouseEvent e)
	{
		mMouseX = e.getX( );
		mMouseY = e.getY( );
	}

	public void mouseMoved(MouseEvent e)
	{
		mMouseX = e.getX( );
		mMouseY = e.getY( );
	}

	public int getMouseX( )
	{
		return mMouseX;
	}

	public int getMouseY( )
	{
		return mMouseY;
	}

	public int getMouseButton( )
	{
		return mMouseButton;
	}

	public void updateViewpoint(int width, int heght)
	{

		// get mouse informations
		float width2 = (float) width / 2.0f;
		float height2 = (float) heght / 2.0f;

		int mx = getMouseX( );
		int my = getMouseY( );
		int mbutton = getMouseButton( );

		float vector[] = new float[3];
		float yrot = 0.0f;

		if (mbutton == 1)
		{
			vector[X] = (((float) my - height2) / height2) * 0.1f;
			yrot = -(((float) mx - width2) / width2 * 0.01f);
			vector[Y] = (((float) my - height2) / height2) * 0.1f;
			yrot = -(((float) mx - width2) / width2 * 0.01f);
		}
		if (mbutton == 3)
		{
			vector[Z] = (((float) my - height2) / height2) * 0.1f;
			yrot = -(((float) mx - width2) / width2 * 0.01f);
		}

		// update viewpoint position
		SceneGraph sceneGraph = getSceneGraph( );
		if (sceneGraph == null)
			return;
		
		ViewpointNode view = sceneGraph.getViewpointNode( );
		if (view == null)
			view = sceneGraph.getDefaultViewpointNode( );
		
		float viewOrienataion[] = new float[4];
		viewOrienataion[0] = 0.0f;
		viewOrienataion[1] = 1.0f;
		viewOrienataion[2] = 0.0f;
		viewOrienataion[3] = yrot;
		view.addOrientation(viewOrienataion);

		float viewFrame[][] = new float[3][3];
		view.getFrame(viewFrame);
		for (int n = X; n <= Z; n++)
		{
			viewFrame[n][X] *= vector[n];
			viewFrame[n][Y] *= vector[n];
			viewFrame[n][Z] *= vector[n];
			view.addPosition(viewFrame[n]);
		}
	}

}
