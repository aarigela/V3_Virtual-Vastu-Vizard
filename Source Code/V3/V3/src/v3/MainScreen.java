/**
 * MainScreen.java - It is the Main Window of the Application
 */

package v3;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import com.sun.j3d.utils.applet.MainFrame;

class MainScreen extends JFrame
{
	private static final long serialVersionUID = 4304723134942430123L;

	// Menu for the Main Screen
	private JMenuBar mbar;
	private JMenu file, edit, view, help;
	private JMenuItem mnew, open, save, exit;
	// private JMenuItem undo, redo;
	private JMenuItem cut, copy, paste, delete, selectAll;
	private JMenuItem design, walkthrough;
	private JCheckBoxMenuItem statusBarOption;
	private JMenuItem topics, about;
	private MainPanel mainPanel;
	private ToolbarPanel toolbar;

	// Other variables of V3
	private boolean statusBarDisplay;
	private Container contentPane;
	private StatusBar statusBar;

	// Variables for internal housekeeping
	protected String fileName;
	private File selectedFile;
	private boolean isFileSaved = true;
	private Frame mainFrame = null;
	
	// Constructor for the V3 Main Screen

	MainScreen( )
	{
		super("V3 - Untitled");
		Dimension screenDim = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setSize(screenDim);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		statusBarDisplay = true;
		contentPane = getContentPane( );
		statusBar = new StatusBar("V3", this);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		mainPanel = new MainPanel(this);
		
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane jsp = new JScrollPane(mainPanel, v, h);

		contentPane.add(jsp, BorderLayout.CENTER);
		mainPanel.setVisible(true);

		javax.swing.JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		mbar = new JMenuBar( );

		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		mnew = new JMenuItem("New"); // "New" Menu
		mnew.setMnemonic(KeyEvent.VK_N);
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK);
		mnew.setAccelerator(keyStroke);
		mnew.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("File -> New");
				else
					removeStatus( );
				setNewProject( );
			}
		});
		file.add(mnew);

		open = new JMenuItem("Open"); // "Open" Menu
		open.setMnemonic(KeyEvent.VK_O);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK);
		open.setAccelerator(keyStroke);
		open.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("File -> Open");
				else
					removeStatus( );
				saveOpenDialog(false);
			}
		});
		file.add(open);

		save = new JMenuItem("Save"); // "Save" Menu
		save.setMnemonic(KeyEvent.VK_S);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
		save.setAccelerator(keyStroke);
		save.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("File -> Save");
				else
					removeStatus( );
				saveOpenDialog(true);
			}
		});

		file.add(save);

		file.addSeparator( );

		exit = new JMenuItem("Exit"); // "Exit" Menu
		exit.setMnemonic(KeyEvent.VK_X);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK);
		exit.setAccelerator(keyStroke);
		exit.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				System.exit(0);
			}
		});
		file.add(exit);

		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);

		cut = new JMenuItem("Cut"); // "Cut" Menu
		cut.setMnemonic(KeyEvent.VK_X);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
		cut.setAccelerator(keyStroke);
		cut.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Edit -> Cut");
				else
					removeStatus( );
			}
		});
		edit.add(cut);

		copy = new JMenuItem("Copy"); // "Copy" Menu
		copy.setMnemonic(KeyEvent.VK_C);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
		copy.setAccelerator(keyStroke);
		copy.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Edit -> Copy");
				else
					removeStatus( );
			}
		});
		edit.add(copy);

		paste = new JMenuItem("Paste"); // "Paste" Menu
		paste.setMnemonic(KeyEvent.VK_P);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK);
		paste.setAccelerator(keyStroke);
		paste.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Edit -> Paste");
				else
					removeStatus( );
			}
		});
		edit.add(paste);

		edit.addSeparator( );

		delete = new JMenuItem("Delete"); // "Delete" Menu
		delete.setMnemonic(KeyEvent.VK_D);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		delete.setAccelerator(keyStroke);
		delete.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Edit -> Delete");
				else
					removeStatus( );
				mainPanel.deleteComponent( );
			}
		});
		edit.add(delete);

		selectAll = new JMenuItem("Select All"); // "Select All" Menu
		selectAll.setMnemonic(KeyEvent.VK_A);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK);
		selectAll.setAccelerator(keyStroke);
		selectAll.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Edit -> Select All");
				else
					removeStatus( );
			}
		});
		edit.add(selectAll);

		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);

		design = new JMenuItem("Design View"); // "Design" Menu
		design.setMnemonic(KeyEvent.VK_D);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
		design.setAccelerator(keyStroke);
		design.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("View -> Design");
				else
					removeStatus( );
				displayDesign( );
			}
		});
		view.add(design);

		walkthrough = new JMenuItem("Walkthrough"); // "Walkthrough" Menu
		walkthrough.setMnemonic(KeyEvent.VK_W);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
		walkthrough.setAccelerator(keyStroke);
		walkthrough.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("View -> Walkthrough");
				else
					removeStatus( );
				displayWalkthrough( );
			}
		});
		view.add(walkthrough);

		view.addSeparator( );

		statusBarOption = new JCheckBoxMenuItem("Status Bar"); // "Status Bar"
		// Menu
		statusBarOption.setState(true);
		statusBarOption.setMnemonic(KeyEvent.VK_S);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
		statusBarOption.setAccelerator(keyStroke);
		statusBarOption.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				statusBarDisplay = statusBarOption.getState( );
				if (statusBarDisplay)
					setStatus("View -> Status Bar : Status Bar enabled");
				else
					removeStatus( );
			}
		});
		view.add(statusBarOption);

		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);

		topics = new JMenuItem("User Guide"); // "User Guide" Menu
		topics.setMnemonic(KeyEvent.VK_G);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
		topics.setAccelerator(keyStroke);
		topics.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Help -> User Guide");
				else
					removeStatus( );
				String userDir = System.getProperty("user.dir");
				File helpFile = new File(userDir + "/v3/help/V3.chm");
				try
				{
					Runtime.getRuntime( ).exec(
							"rundll32 SHELL32.DLL,ShellExec_RunDLL "
									+ helpFile.getAbsolutePath( ));
				}
				catch (IOException e1)
				{
					e1.printStackTrace( );
				}
			}
		});
		help.add(topics);

		help.addSeparator( );

		about = new JMenuItem("About V3"); // "About" Menu
		about.setMnemonic(KeyEvent.VK_A);
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0);
		about.setAccelerator(keyStroke);
		about.addActionListener(new ActionListener( )
		{
			public void actionPerformed(ActionEvent e)
			{
				if (statusBarDisplay)
					setStatus("Help -> About V3");
				else
					removeStatus( );
				showAboutDialog( );
			}
		});
		help.add(about);

		mbar.add(file);
		mbar.add(edit);
		mbar.add(view);
		mbar.add(help);

		setJMenuBar(mbar);

		setPreferredSize(new Dimension(800, 600));
		pack( );

		fileName = "untitled";
		selectedFile = null;
		setTitle( );

		setToolbar( );

		addComponentListener(new ComponentAdapter( )
		{
			public void componentResized(ComponentEvent e)
			{
				mainPanel.sizeChanged( );
			}
		});
	}

	public void displayDesign( )
	{
		if (mainFrame != null)
		{
			if (mainFrame.isVisible( ))
			{
				setMainFrameLocation(false);
			}
		}
	}

	protected void setNewProject( )
	{
		int result = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to select new project?", "New",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.YES_OPTION)
		{
			selectedFile = null;
			fileName = "untitled";
			setTitle( );
			mainPanel.setNewProject( );
		}
	}

	private void setToolbar( )
	{
		toolbar = new ToolbarPanel(this);

		JToolBar tBar = toolbar.getToolBar( );
		contentPane.add(tBar, BorderLayout.NORTH);
	}

	private void showAboutDialog( )
	{
		new AboutV3Dialog(this, true);
	}

	// Displaying the Status Bar

	private void setStatus(String msg)
	{
		statusBar.setMessage(msg);
		statusBar.showStatus( );
	}

	// Hiding the Status Bar

	private void removeStatus( )
	{
		statusBar.hideStatus( );
		statusBar.setMessage("V3");
	}

	// Displaying the Save and Open Dialog Box

	protected void saveOpenDialog(boolean type)
	{
		String userDir = System.getProperty("user.dir");
		JFileChooser chooser = new JFileChooser(new File(userDir));
		chooser.addChoosableFileFilter(new NameFilter( ));
		if (type)
		{
			chooser.showSaveDialog(null);
			File saveFile = chooser.getSelectedFile( );
			if (saveFile == null)
				return;
			mainPanel.saveVRMLFile(saveFile);
			setFileSaved(true);
		}
		else
		{
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				selectedFile = chooser.getSelectedFile( );
				fileName = selectedFile.getName( );
				setTitle( );
				mainPanel.openExistingProject(selectedFile);
				setFileSaved(true);
			}
		}
	}

	// The filter for specifying the filter for the Open and Save Dialog Box

	class NameFilter extends FileFilter
	{
		public boolean accept(File fileObj)
		{
			String extension = "";
			String filePath = fileObj.getPath( );
			int extensionPos = filePath.lastIndexOf('.');
			if (extensionPos > 0)
				extension = filePath.substring(extensionPos + 1).toLowerCase( );
			if (extension != "")
				return extension.equals("wrl");
			else
				return fileObj.isDirectory( );
		}

		public String getDescription( )
		{
			return "VRML File (*.wrl)";
		}
	}

	public void displayWalkthrough( )
	{
		if (mainFrame != null)
		{
			setMainFrameLocation(true);
			return;
		}
		if (!isFileSaved)
		{
			int res = JOptionPane.showConfirmDialog(this,
					"Do you want to save ?", "Save Confirm Dialog",
					JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.NO_OPTION)
				return;
			if(res==JOptionPane.CLOSED_OPTION)
				return;
			saveOpenDialog(true);
		}
		if (selectedFile == null)
		{
			JOptionPane.showMessageDialog(this, "No Scene Loaded", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		mainFrame = new MainFrame(new WalkthroughApplet(this), 1000, 550);
		mainFrame.addKeyListener(new KeyAdapter( )
		{
			public void keyPressed(KeyEvent e)
			{
				int keyCode = e.getKeyCode( );
				switch (keyCode)
				{
				case KeyEvent.VK_ESCAPE:
					setMainFrameVisible(false);
					break;
				case KeyEvent.VK_F5:
					setMainFrameLocation(false);
				default:
				}
			}
		});
	}

	private void setTitle( )
	{
		setTitle("V3 - " + fileName);
	}

	public File getSelectedFile( )
	{
		return selectedFile;
	}

	public void setSelectedFile(File file)
	{
		selectedFile = file;
	}

	public boolean isFileSaved( )
	{
		return isFileSaved;
	}

	public void setFileSaved(boolean b)
	{
		isFileSaved = b;
	}

	public Frame getMainFrame( )
	{
		return mainFrame;
	}

	public void setMainFrameVisible(boolean b)
	{
		mainFrame.setVisible(false);
		mainFrame = null;
	}

	public void setMainFrameLocation(boolean b)
	{
		if (mainFrame != null)
		{
			if (b)
				mainFrame.toFront( );
			else
				mainFrame.toBack( );
		}
	}

	public StatusBar getStatusBar( )
	{
		return statusBar;
	}

}
