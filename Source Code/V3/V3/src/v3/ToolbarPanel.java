/**
 * ToolbarPanel.java - It displays the toolbar
 */

package v3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolbarPanel
{

	private MainScreen mainScreen = null;
	private JButton tNew, tOpen, tSave, tCut, tCopy, tPaste;
	// private JButton tUndo, tRedo;
	private JButton tDesign, tWalkthrough;
	private JToolBar toolBar = null;

	public ToolbarPanel( )
	{
		mainScreen = null;
		tNew = null;
		tOpen = null;
		tSave = null;
		tCut = null;
		tCopy = null;
		tPaste = null;
		// tUndo = null;
		// tRedo = null;
		tDesign = null;
		tWalkthrough = null;

		toolBar = null;
	}

	public ToolbarPanel(MainScreen screen)
	{
		mainScreen = screen;

		ImageIcon tNewImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/NEW.jpg"));
		ImageIcon tOpenImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/OPEN.jpg"));
		ImageIcon tSaveImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/SAVE.jpg"));
		ImageIcon tCutImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/CUT.jpg"));
		ImageIcon tCopyImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/COPY.jpg"));
		ImageIcon tPasteImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/PASTE.jpg"));
		ImageIcon tDesignImage = new ImageIcon(this.getClass( ).getResource(
				"Images/Toolbar/DESIGN.jpg"));
		ImageIcon tWalkthroughImage = new ImageIcon(this.getClass( )
				.getResource("Images/Toolbar/WALKTHROUGH.jpg"));
		// ImageIcon tUndoImage = new ImageIcon(this.getClass( ).getResource(
		// "UNDO.jpg"));
		// ImageIcon tRedoImage = new ImageIcon(this.getClass( ).getResource(
		// "REDO.jpg"));

		tNew = new JButton(tNewImage);
		tNew.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen.setNewProject( );

			}

		});
		tNew.setToolTipText("New");
		// tNew.setSize(new Dimension(24,43));

		tOpen = new JButton(tOpenImage);
		tOpen.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen.saveOpenDialog(false);

			}

		});
		tOpen.setToolTipText("Open");

		tSave = new JButton(tSaveImage);
		tSave.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen.saveOpenDialog(true);

			}

		});
		tSave.setToolTipText("Save");

		tCut = new JButton(tCutImage);
		tCut.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

			}

		});
		tCut.setToolTipText("Cut");

		tCopy = new JButton(tCopyImage);
		tCopy.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

			}

		});
		tCopy.setToolTipText("Copy");

		tPaste = new JButton(tPasteImage);
		tPaste.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

			}

		});
		tPaste.setToolTipText("Paste");

		/*
		 * tUndo = new JButton(tUndoImage); tUndo.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * }
		 * 
		 * }); tUndo.setToolTipText("Undo");
		 */

		/*
		 * tRedo = new JButton(tRedoImage); tRedo.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * }
		 * 
		 * }); tRedo.setToolTipText("Redo");
		 */

		tDesign = new JButton(tDesignImage);
		tDesign.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen.displayDesign( );
			}

		});
		tDesign.setToolTipText("Design");

		tWalkthrough = new JButton(tWalkthroughImage);
		tWalkthrough.addActionListener(new ActionListener( )
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen.displayWalkthrough( );
			}

		});
		tWalkthrough.setToolTipText("Walkthrough");

		toolBar = new JToolBar( );

		toolBar.add(tNew);
		toolBar.add(tOpen);
		toolBar.add(tSave);

		toolBar.addSeparator( );

		toolBar.add(tCut);
		toolBar.add(tCopy);
		toolBar.add(tPaste);

		toolBar.addSeparator( );

		toolBar.add(tDesign);
		toolBar.add(tWalkthrough);
	}

	public JToolBar getToolBar( )
	{
		return toolBar;
	}

}
