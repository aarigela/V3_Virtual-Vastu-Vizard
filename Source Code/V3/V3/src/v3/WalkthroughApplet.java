/**
 * WalkthroughApplet.java - Displays Walkthrough
 */

package v3;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.RenderingError;
import javax.media.j3d.RenderingErrorListener;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom; // Java 3D VRML97
// Loader:
// j3d-vrm97.jar
// https://j3d-vrml97.dev.java.net/
import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

public class WalkthroughApplet extends JApplet implements KeyListener
{

	private static final long serialVersionUID = 1L;
	private boolean isJ3D = false;
	private boolean isJ3DLoader = false;

	private BoundingSphere globalBounds = null;

	private View view = null;
	private Canvas3D canvas3D = null;

	private VirtualUniverse vu = null;
	private Locale locale = null;

	private BranchGroup sceneBranch = null;
	private BranchGroup viewBranch = null;
	private BranchGroup enviBranch = null;

	private TransformGroup zoomTG = null;
	private TransformGroup sceneTG = null;

	private ClassLoader classLoader = null;
	private Transform3D tscale;

	private MainScreen mainScreen = null;
	private File selectedFile = null;

	public WalkthroughApplet( )
	{
	}

	public WalkthroughApplet(MainScreen screen)
	{
		mainScreen = screen;
		selectedFile = mainScreen.getSelectedFile( );
	}

	@Override
	public void init( )
	{
		// Check if Java 3D and your loader package are installed
		classLoader = this.getClass( ).getClassLoader( );
		try
		{
			classLoader.loadClass("javax.media.j3d.BoundingSphere");
			isJ3D = true;

			classLoader.loadClass("org.jdesktop.j3d.loaders.vrml97.VrmlLoader");
			isJ3DLoader = true;
		}
		catch (ClassNotFoundException e)
		{
		}

		if (isJ3D)
		{
			createUniverse( );
			if (isJ3DLoader)
				createScene( );
			else
				System.out
						.println("Loader 'org.jdesktop.j3d.loaders.vrml97.VrmlLoader' not found !!");
			setLive( );

			getContentPane( ).add(canvas3D);
			canvas3D.addKeyListener(this);
		}
	}

	@Override
	public void start( )
	{
	}

	@Override
	public void stop( )
	{
	}

	@Override
	public void destroy( )
	{
		// Unload the scene.
		view.removeAllCanvas3Ds( );
		view.attachViewPlatform(null);

		vu.removeAllLocales( );

	}

	private void createScene( )
	{
		// Load the scene form file.
		Scene scene = loadScene( );
		if (scene == null)
			System.out.println("No scene loaded");

		// Create BranchGroup object.
		BranchGroup rootGroup = null;
		if (scene != null && (rootGroup = scene.getSceneGroup( )) != null)
			sceneTG.addChild(rootGroup);
	}

	private Scene loadScene( )
	{

		Scene scene = null;

		try
		{
			if (selectedFile == null)
				return null;

			// Connect the selected scene file to the stream.
			FileInputStream sceneFile = new FileInputStream(selectedFile);

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(sceneFile));

			URL sceneUrl = selectedFile.toURI( ).toURL( );

			if (sceneUrl == null)
				return null;

			String sceneUrlString = sceneUrl.toString( );
			String baseUrlString = sceneUrlString.substring(0, sceneUrlString
					.lastIndexOf('/') + 1);

			URL baseUrl = new URL(baseUrlString);

			Loader vrmlLoader = new VrmlLoader( ); // No flag: Shapes only

			// BaseUrl
			vrmlLoader.setBaseUrl(baseUrl);

			try
			{
				// Load the scene using VrmlLoader.
				scene = vrmlLoader.load(bufferedReader);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace( );
			}
			catch (IncorrectFormatException e)
			{
				e.printStackTrace( );
			}
			catch (ParsingErrorException e)
			{
				e.printStackTrace( );
			}

			// Close the streams.
			sceneFile.close( );
			bufferedReader.close( );
		}
		catch (IOException e)
		{
		}

		return scene;
	}

	private void setLive( )
	{
		// Allow the compiler to perform optimization.
		sceneBranch.compile( );
		viewBranch.compile( );
		enviBranch.compile( );

		// Add the scene to the Universe.
		locale.addBranchGraph(sceneBranch);
		locale.addBranchGraph(viewBranch);
		locale.addBranchGraph(enviBranch);
	}

	private void createUniverse( )
	{
		// Bounds
		globalBounds = new BoundingSphere( );
		globalBounds.setRadius(Double.MAX_VALUE);

		// Viewing
		view = new View( );
		view.setPhysicalBody(new PhysicalBody( ));
		view.setPhysicalEnvironment(new PhysicalEnvironment( ));

		GraphicsConfigTemplate3D gCT = new GraphicsConfigTemplate3D( );
		GraphicsConfiguration gcfg = GraphicsEnvironment
				.getLocalGraphicsEnvironment( ).getDefaultScreenDevice( )
				.getBestConfiguration(gCT);

		try
		{
			canvas3D = new Canvas3D(gcfg);
		}
		catch (NullPointerException e)
		{
			System.out.println("AppletVrmlLoader: Canvas3D failed !!");
			e.printStackTrace( );
			System.exit(0);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("AppletVrmlLoader: Canvas3D failed !!");
			e.printStackTrace( );
			System.exit(0);
		}

		view.addCanvas3D(canvas3D);

		// SuperStructure
		vu = new VirtualUniverse( );
		VirtualUniverse.addRenderingErrorListener(new RenderingErrorListener( )
		{

			@Override
			public void errorOccurred(RenderingError arg0)
			{
				JOptionPane.showMessageDialog(null,
						"Graphic Card Error: Restart V3", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		locale = new Locale(vu);

		// BranchGraphs
		sceneBranch = new BranchGroup( );
		viewBranch = new BranchGroup( );
		enviBranch = new BranchGroup( );

		// ViewBranch
		tscale = new Transform3D( );
		tscale.setScale(1.0);

		TransformGroup viewTG = new TransformGroup(tscale);
		viewTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		viewTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		// Use the Mouse to rotate.
		MouseRotate mouseBeh1 = new MouseRotate(viewTG);
		viewTG.addChild(mouseBeh1);
		mouseBeh1.setSchedulingBounds(bounds);

		// Use the Mouse Wheel to zoom.
		MouseWheelZoom mouseBeh2 = new MouseWheelZoom(viewTG);
		viewTG.addChild(mouseBeh2);
		mouseBeh2.setSchedulingBounds(bounds);

		// Use the Mouse to move.
		MouseTranslate mouseBeh3 = new MouseTranslate(viewTG);
		viewTG.addChild(mouseBeh3);
		mouseBeh3.setSchedulingBounds(bounds);

		// Use the keyboard to move.
		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(viewTG);
		keyNavBeh.setSchedulingBounds(bounds);
		viewTG.addChild(keyNavBeh);

		Transform3D viewTransform = new Transform3D( );
		viewTransform.setTranslation(new Vector3f(0.5f, 0.25f, 1.0f));
		viewTG.setTransform(viewTransform);

		ViewPlatform vp = new ViewPlatform( );
		view.attachViewPlatform(vp);

		DirectionalLight headLight = new DirectionalLight( );
		headLight.setInfluencingBounds(globalBounds);

		viewTG.addChild(vp);
		viewTG.addChild(headLight);

		viewBranch.addChild(viewTG);

		// EnviBranch
		Background bg = new Background( );
		bg.setApplicationBounds(globalBounds);
		bg.setColor(0.0f, 0.47f, 0.63f);

		enviBranch.addChild(bg);

		// SceneBranch

		zoomTG = new TransformGroup( );
		zoomTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		sceneBranch.addChild(zoomTG);

		sceneTG = new TransformGroup( );
		// Scene positioning
		Transform3D sceneTransform = new Transform3D( );
		sceneTransform.setTranslation(new Vector3f(0.0f, 0.0f, -3.0f));
		sceneTG.setTransform(sceneTransform);

		zoomTG.addChild(sceneTG);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode( );
		switch (keyCode)
		{
		case KeyEvent.VK_F5:
		case KeyEvent.VK_ESCAPE:
			destroy( );
			mainScreen.setMainFrameVisible(false);
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
